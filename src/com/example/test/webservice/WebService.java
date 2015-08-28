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
import com.example.test.model.UserInfo;

public class WebService {

	// �����ռ�
	// private static final String NAME_SPACE = "http://localhost/PimsSrv.wsdl";
	private static final String NAME_SPACE = "urn:PimsSrv";
	// ������
	private static final String METHOD_ECHO = "Echo";
	private static final String METHOD_COMAPI = "ComApi";
	// ������
	private static final String CMD_USER_LOGIN = "UL"; // �û���¼
	private static final String CMD_QUERY_PRODUCT = "QP"; // ��ѯ��Ʒ
	// ���ڵ�
	private static final String NODE_ROOT = "nhroot";
	// ����ڵ�
	private static final String NODE_CMD = "cmd";
	// ���ݽڵ�
	private static final String NODE_DATA = "data";
	// ��������-����
	private static final String ATTR_DATAROW = "datarow";
	// �������
	private static final String NODE_ERRCODE = "errcode";
	// ������Ϣ
	private static final String NODE_ERRMSG = "errmsg";

	// ��ȡWebService Url
	private static String getServerUrl() {
		ShaPreOpe shaPreOpe = new ShaPreOpe(ContextUtil.getInstance());
		final String ipPort = shaPreOpe.read("ip", "") + ":"
				+ shaPreOpe.read("port", "");
		String ServerUrl = "http://" + ipPort + "/PimsSrv";
		return ServerUrl;
	}

	// �û���¼
	public static void UserLogin(String userName, String password,
			UserInfo userInfo, WsErr err) {

		String in = "";
		String out = "";

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		// ����xml
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();

			Document document = builder.newDocument();
			// �������ڵ�
			Element rootNode = document.createElement(NODE_ROOT);
			// ��ӵ�document
			document.appendChild(rootNode);
			// ��������ڵ�
			Element cmdNode = document.createElement(NODE_CMD);
			cmdNode.setTextContent(CMD_USER_LOGIN);
			// ��ӵ�document
			rootNode.appendChild(cmdNode);
			// �������ݽڵ�
			Element dataNode = document.createElement(NODE_DATA);
			// ��ӵ�document
			rootNode.appendChild(dataNode);
			// // �������ݽڵ�����
			// dataNode.setAttribute(ATTR_DATAROW, "0");
			// ��������
			Element userNameNode = document.createElement("username");
			userNameNode.setTextContent(userName);
			dataNode.appendChild(userNameNode);
			Element passwordNode = document.createElement("password");
			passwordNode.setTextContent(password);
			dataNode.appendChild(passwordNode);

			// ����������
			DOMSource domSource = new DOMSource(document);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			// ��ʼ��Documentӳ�䵽result
			transformer.transform(domSource, result);

			in = writer.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			err.errCode = "1";
			err.errMsg = "����xml�쳣��" + e.getMessage();
			return;
		}

		out = comApi(in);
		if (null == out) {
			err.errCode = WsErr.ERR_CODE_CNT;
			err.errMsg = WsErr.ERR_MSG_CNT;
			return;
		}

		// ����xml
		try {
			// �õ�DocumentBuilder����
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// �õ���������xml��Document����
			InputStream stream = new ByteArrayInputStream(out.getBytes("UTF-8"));
			Document document = builder.parse(stream);
			// �õ����ڵ�
			Element root = document.getDocumentElement();
			// ��ȡ���ڵ��е����еĽڵ�
			NodeList errCodeList = root.getElementsByTagName(NODE_ERRCODE);
			if (0 != errCodeList.getLength()) {
				Node node = (Element)errCodeList.item(0);
				err.errCode = node.getTextContent();
			}
			NodeList errMsgList = root.getElementsByTagName(NODE_ERRMSG);
			if (0 != errMsgList.getLength()) {
				Node node = errMsgList.item(0);
				err.errMsg = "Զ��:" + node.getTextContent();
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
			err.errMsg = "����xml�쳣��" + e.getMessage();
			return;
		}

	}

	// ��ѯ��Ʒ

	// �����Ǽ�

	// ͨ�ýӿ�
	public static String comApi(String in) {

		final String ServerUrl = getServerUrl();

		// ����soapObject���󲢴��������ռ�ͷ�����
		SoapObject request = new SoapObject(NAME_SPACE, METHOD_COMAPI);
		request.addProperty("in", in);

		// ����HttpTransportSE��˵���� ����webservice��������ַ
		final HttpTransportSE httpSE = new HttpTransportSE(ServerUrl);
		httpSE.debug = true;

		// ����SoapSerializationEnvelope���󲢴���SOAPЭ��İ汾��
		final SoapSerializationEnvelope soapSerial = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapSerial.bodyOut = request;
		// ������.NET�ṩ��Web service���������õļ�����
		soapSerial.dotNet = true;

		// ʹ��Callable��Future�����������߳�
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						String detail = new String();
						// ����HttpTransportSE�����call���������� webservice
						httpSE.call(NAME_SPACE + METHOD_COMAPI, soapSerial);
						if (soapSerial.getResponse() != null) {
							// ��ȡ��������Ӧ���ص�SOAP��Ϣ
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

	// ���Խӿ�,��ɶ��ɶ
	public static String echo(String in) {

		final String ServerUrl = getServerUrl();

		// ����soapObject���󲢴��������ռ�ͷ�����
		SoapObject request = new SoapObject(NAME_SPACE, METHOD_ECHO);
		request.addProperty("in", in);

		// ����HttpTransportSE��˵���� ����webservice��������ַ
		final HttpTransportSE httpSE = new HttpTransportSE(ServerUrl);
		httpSE.debug = true;

		// ����SoapSerializationEnvelope���󲢴���SOAPЭ��İ汾��
		final SoapSerializationEnvelope soapSerial = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapSerial.bodyOut = request;
		// ������.NET�ṩ��Web service���������õļ�����
		soapSerial.dotNet = true;

		// ʹ��Callable��Future�����������߳�
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						String detail = new String();
						// ����HttpTransportSE�����call���������� webservice
						httpSE.call(NAME_SPACE + METHOD_ECHO, soapSerial);
						if (soapSerial.getResponse() != null) {
							// ��ȡ��������Ӧ���ص�SOAP��Ϣ
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
