package com.example.test.webservice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.cyx.lib.ShaPreOpe;
import com.cyx.lib.ContextUtil;
import com.example.test.GloVar;
import com.example.test.model.*;

public class WebService {

	// 命名空间
	// private static final String NAME_SPACE = "http://localhost/PimsSrv.wsdl";
	private static final String NAME_SPACE = "urn:PimsSrv";
	// 方法名
	private static final String METHOD_ECHO = "Echo";
	private static final String METHOD_COMAPI = "ComApi";
	// 命令名
	private static final String CMD_USER_LOGIN = "UL"; // 用户登录
	private static final String CMD_QUERY_PRODUCT = "QP"; // 查询产品
	// 根节点
	private static final String NODE_ROOT = "nhroot";
	// 命令节点
	private static final String NODE_CMD = "cmd";
	// 数据节点
	private static final String NODE_DATA = "data";
	// 数据属性-行数
	private static final String ATTR_DATAROW = "datarow";
	// 错误代码
	private static final String NODE_ERRCODE = "errcode";
	// 错误消息
	private static final String NODE_ERRMSG = "errmsg";

	// 获取WebService Url
	private static String getServerUrl() {
		ShaPreOpe shaPreOpe = new ShaPreOpe(ContextUtil.getInstance());
		final String ipPort = shaPreOpe.read("ip", "") + ":"
				+ shaPreOpe.read("port", "");
		String ServerUrl = "http://" + ipPort + "/PimsSrv";
		return ServerUrl;
	}

	// 用户登录
	public static void UserLogin(String userName, String password,
			UserInfo userInfo, WsErr err) {

		String in = "";
		String out = "";

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		// 创建xml
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			Document document = builder.newDocument();
			// 建立根节点
			Element rootNode = document.createElement(NODE_ROOT);
			// 添加到document
			document.appendChild(rootNode);
			// 建立命令节点
			Element cmdNode = document.createElement(NODE_CMD);
			cmdNode.setTextContent(CMD_USER_LOGIN);
			// 添加到document
			rootNode.appendChild(cmdNode);
			// 建立数据节点
			Element dataNode = document.createElement(NODE_DATA);
			// 添加到document
			rootNode.appendChild(dataNode);
			// // 设置数据节点属性
			// dataNode.setAttribute(ATTR_DATAROW, "0");
			// 其它数据
			Element userNameNode = document.createElement("username");
			userNameNode.setTextContent(userName);
			dataNode.appendChild(userNameNode);
			Element passwordNode = document.createElement("password");
			passwordNode.setTextContent(password);
			dataNode.appendChild(passwordNode);

			// 设置输出结果
			DOMSource domSource = new DOMSource(document);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			// 开始把Document映射到result
			transformer.transform(domSource, result);

			in = writer.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			err.errCode = "1";
			err.errMsg = "创建xml异常：" + e.getMessage();
			return;
		}

		out = comApi(in);
		if (null == out) {
			err.errCode = WsErr.ERR_CODE_CNT;
			err.errMsg = WsErr.ERR_MSG_CNT;
			return;
		}

		// 解析xml
		try {
			// 得到DocumentBuilder对象
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// 得到代表整个xml的Document对象
			InputStream stream = new ByteArrayInputStream(out.getBytes("UTF-8"));
			Document document = builder.parse(stream);
			// 得到根节点
			Element root = document.getDocumentElement();
			// 获取根节点中的所有的节点
			NodeList errCodeList = root.getElementsByTagName(NODE_ERRCODE);
			if (0 != errCodeList.getLength()) {
				Node node = (Element)errCodeList.item(0);
				err.errCode = node.getTextContent();
			}
			NodeList errMsgList = root.getElementsByTagName(NODE_ERRMSG);
			if (0 != errMsgList.getLength()) {
				Node node = errMsgList.item(0);
				err.errMsg = "远程:" + node.getTextContent();
			}
			NodeList dataList = root.getElementsByTagName(NODE_DATA);
			if (0 != dataList.getLength()) {
                Node node = dataList.item(0);
                NodeList childList = node.getChildNodes();
                for (int i=0; i<childList.getLength(); ++i) {
                	Node childNode =  childList.item(i);
                	if (Node.ELEMENT_NODE == childNode.getNodeType()) {
                		if ("username".equals(childNode.getNodeName())) {
							GloVar.curUser.userName = childNode.getFirstChild()
									.getNodeValue();
						} else if ("realname".equals(childNode.getNodeName())) {
							GloVar.curUser.realName = childNode.getFirstChild()
									.getNodeValue();
						}
					}
                }
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.toString());
			err.errCode = "1";
			err.errMsg = "解析xml异常：" + e.getMessage();
			return;
		}

	}

	// 查询产品
	public static void queryProduct(String proNum, ProInfo proInfo, WsErr err) {

		String in = "";
		String out = "";

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		// 创建xml
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			Document document = builder.newDocument();
			// 建立根节点
			Element rootNode = document.createElement(NODE_ROOT);
			// 添加到document
			document.appendChild(rootNode);
			// 建立命令节点
			Element cmdNode = document.createElement(NODE_CMD);
			cmdNode.setTextContent(CMD_QUERY_PRODUCT);
			// 添加到document
			rootNode.appendChild(cmdNode);
			// 建立数据节点
			Element dataNode = document.createElement(NODE_DATA);
			// 添加到document
			rootNode.appendChild(dataNode);
			// 其它数据
			Element proNumNode = document.createElement("productnumber");
			proNumNode.setTextContent(proNum);
			dataNode.appendChild(proNumNode);

			// 设置输出结果
			DOMSource domSource = new DOMSource(document);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			// 开始把Document映射到result
			transformer.transform(domSource, result);

			in = writer.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			err.errCode = "1";
			err.errMsg = "创建xml异常：" + e.getMessage();
			return;
		}

		out = comApi(in);
		if (null == out) {
			err.errCode = WsErr.ERR_CODE_CNT;
			err.errMsg = WsErr.ERR_MSG_CNT;
			return;
		}

		// 解析xml
		try {
			// 得到DocumentBuilder对象
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// 得到代表整个xml的Document对象
			InputStream stream = new ByteArrayInputStream(out.getBytes("UTF-8"));
			Document document = builder.parse(stream);
			// 得到根节点
			Element root = document.getDocumentElement();
			// 获取根节点中的所有的节点
			NodeList errCodeList = root.getElementsByTagName(NODE_ERRCODE);
			if (0 != errCodeList.getLength()) {
				Node node = (Element)errCodeList.item(0);
				err.errCode = node.getTextContent();
			}
			NodeList errMsgList = root.getElementsByTagName(NODE_ERRMSG);
			if (0 != errMsgList.getLength()) {
				Node node = errMsgList.item(0);
				err.errMsg = "远程:" + node.getTextContent();
			}
			NodeList dataList = root.getElementsByTagName(NODE_DATA);
			if (0 != dataList.getLength()) {
                Node node = dataList.item(0);
                NodeList childList = node.getChildNodes();
                for (int i=0; i<childList.getLength(); ++i) {
                	Node childNode =  childList.item(i);
                	if (Node.ELEMENT_NODE == childNode.getNodeType()) {
                		if ("productnumber".equals(childNode.getNodeName())) {
							proInfo.proNum = childNode.getFirstChild()
									.getNodeValue();
						} else if ("productname".equals(childNode.getNodeName())) {
							proInfo.proName = childNode.getFirstChild()
									.getNodeValue();
						} else if ("productmodel".equals(childNode.getNodeName())) {
							proInfo.proModel = childNode.getFirstChild()
									.getNodeValue();
						}
					}
                }
			} else {
				// 没有数据
				proInfo = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.toString());
			err.errCode = "1";
			err.errMsg = "解析xml异常：" + e.getMessage();
			return;
		}

	}

	// 发货登记

	// 通用接口
	public static String comApi(String in) {

		final String ServerUrl = getServerUrl();

		// 创建soapObject对象并传入命名空间和方法名
		SoapObject request = new SoapObject(NAME_SPACE, METHOD_COMAPI);
		request.addProperty("in", in);

		// 创建HttpTransportSE传说对象 传入webservice服务器地址
		final HttpTransportSE httpSE = new HttpTransportSE(ServerUrl);
		httpSE.debug = true;

		// 创建SoapSerializationEnvelope对象并传入SOAP协议的版本号
		final SoapSerializationEnvelope soapSerial = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapSerial.bodyOut = request;
		// 设置与.NET提供的Web service保持有良好的兼容性
		soapSerial.dotNet = true;

		// 使用Callable与Future来创建启动线程
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						String detail = new String();
						// 调用HttpTransportSE对象的call方法来调用 webservice
						httpSE.call(NAME_SPACE + METHOD_COMAPI, soapSerial);
						if (soapSerial.getResponse() != null) {
							// 获取服务器响应返回的SOAP消息
							SoapObject response = (SoapObject) soapSerial.bodyIn;
							detail = response.getPropertyAsString(0).toString();
							return detail;
						} else {
							return null;
						}
					}
				});

		new Thread(future).start();
		try {
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 测试接口,发啥收啥
	public static String echo(String in) {

		final String ServerUrl = getServerUrl();

		// 创建soapObject对象并传入命名空间和方法名
		SoapObject request = new SoapObject(NAME_SPACE, METHOD_ECHO);
		request.addProperty("in", in);

		// 创建HttpTransportSE传说对象 传入webservice服务器地址
		final HttpTransportSE httpSE = new HttpTransportSE(ServerUrl);
		httpSE.debug = true;

		// 创建SoapSerializationEnvelope对象并传入SOAP协议的版本号
		final SoapSerializationEnvelope soapSerial = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapSerial.bodyOut = request;
		// 设置与.NET提供的Web service保持有良好的兼容性
		soapSerial.dotNet = true;

		// 使用Callable与Future来创建启动线程
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						String detail = new String();
						// 调用HttpTransportSE对象的call方法来调用 webservice
						httpSE.call(NAME_SPACE + METHOD_ECHO, soapSerial);
						if (soapSerial.getResponse() != null) {
							// 获取服务器响应返回的SOAP消息
							SoapObject response = (SoapObject) soapSerial.bodyIn;
							detail = response.getPropertyAsString(0).toString();
							return detail;
						} else {
							return null;
						}
					}
				});

		new Thread(future).start();
		try {
			return future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

}
