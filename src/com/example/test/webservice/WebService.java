package com.example.test.webservice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cyx.lib.ShaPreOpe;
import com.cyx.lib.ContextUtil;

public class WebService {

	// 命名空间
//	private static final String NAME_SPACE = "http://localhost/PimsSrv.wsdl";
	private static final String NAME_SPACE = "urn:PimsSrv";
	// 方法名
	private static final String METHOD_ECHOA = "EchoA";
	private static final String METHOD_ECHOW = "EchoW";
	private static final String METHOD_INTERFACE = "Interface";
	// 服务器Url前缀、后缀
	private static final String SRV_URL_PRE = "http://";
	private static final String SRV_URL_SUF = "/PimsSrv";
	
	
	public static String echoa(String in, String out) {
		
		final String ServerUrl = getServerUrl();

		// 创建HttpTransportSE传说对象 传入webservice服务器地址
		final HttpTransportSE httpSE = new HttpTransportSE(ServerUrl);
		httpSE.debug = true;

		// 创建soapObject对象并传入命名空间和方法名
		SoapObject soapObject = new SoapObject(NAME_SPACE, METHOD_ECHOA);

		soapObject.addProperty("in", in);

		// 创建SoapSerializationEnvelope对象并传入SOAP协议的版本号
		final SoapSerializationEnvelope soapserial = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapserial.bodyOut = soapObject;
		// 设置与.NET提供的Web service保持有良好的兼容性
		soapserial.dotNet = true;
		// 使用Callable与Future来创建启动线程
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						String detail = new String();
						// 调用HttpTransportSE对象的call方法来调用 webserice
						httpSE.call(NAME_SPACE + METHOD_ECHOA, soapserial);
						if (soapserial.getResponse() != null) {
							// 获取服务器响应返回的SOAP消息
							SoapObject result = (SoapObject) soapserial.bodyIn;
							detail = result.getPropertyAsString(0).toString();
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
	
	public static String echow(String in, String out) {
		
		final String ServerUrl = getServerUrl();

		// 创建HttpTransportSE传说对象 传入webservice服务器地址
		final HttpTransportSE httpSE = new HttpTransportSE(ServerUrl);
		httpSE.debug = true;

		// 创建soapObject对象并传入命名空间和方法名
		SoapObject soapObject = new SoapObject(NAME_SPACE, METHOD_ECHOW);

		soapObject.addProperty("in", in);

		// 创建SoapSerializationEnvelope对象并传入SOAP协议的版本号
		final SoapSerializationEnvelope soapserial = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapserial.bodyOut = soapObject;
		// 设置与.NET提供的Web service保持有良好的兼容性
		soapserial.dotNet = true;
		// 使用Callable与Future来创建启动线程
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						String detail = new String();
						// 调用HttpTransportSE对象的call方法来调用 webservice
						httpSE.call(NAME_SPACE + METHOD_ECHOW, soapserial);
						if (soapserial.getResponse() != null) {
							// 获取服务器响应返回的SOAP消息
							SoapObject result = (SoapObject) soapserial.bodyIn;
							detail = result.getPropertyAsString(0).toString();
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
	
	// 获取WebService Url
	private static String getServerUrl() {
		ShaPreOpe shaPreOpe = new ShaPreOpe(ContextUtil.getInstance());
		final String ipPort = shaPreOpe.read("ip", "") + ":" + shaPreOpe.read("port", "");
		String ServerUrl = SRV_URL_PRE + ipPort + SRV_URL_SUF;
		return ServerUrl;
	}
	
}
