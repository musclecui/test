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

	// �����ռ�
//	private static final String NAME_SPACE = "http://localhost/PimsSrv.wsdl";
	private static final String NAME_SPACE = "urn:PimsSrv";
	// ������
	private static final String METHOD_ECHOA = "EchoA";
	private static final String METHOD_ECHOW = "EchoW";
	private static final String METHOD_INTERFACE = "Interface";
	// ������Urlǰ׺����׺
	private static final String SRV_URL_PRE = "http://";
	private static final String SRV_URL_SUF = "/PimsSrv";
	
	
	public static String echoa(String in, String out) {
		
		final String ServerUrl = getServerUrl();

		// ����HttpTransportSE��˵���� ����webservice��������ַ
		final HttpTransportSE httpSE = new HttpTransportSE(ServerUrl);
		httpSE.debug = true;

		// ����soapObject���󲢴��������ռ�ͷ�����
		SoapObject soapObject = new SoapObject(NAME_SPACE, METHOD_ECHOA);

		soapObject.addProperty("in", in);

		// ����SoapSerializationEnvelope���󲢴���SOAPЭ��İ汾��
		final SoapSerializationEnvelope soapserial = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapserial.bodyOut = soapObject;
		// ������.NET�ṩ��Web service���������õļ�����
		soapserial.dotNet = true;
		// ʹ��Callable��Future�����������߳�
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						String detail = new String();
						// ����HttpTransportSE�����call���������� webserice
						httpSE.call(NAME_SPACE + METHOD_ECHOA, soapserial);
						if (soapserial.getResponse() != null) {
							// ��ȡ��������Ӧ���ص�SOAP��Ϣ
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

		// ����HttpTransportSE��˵���� ����webservice��������ַ
		final HttpTransportSE httpSE = new HttpTransportSE(ServerUrl);
		httpSE.debug = true;

		// ����soapObject���󲢴��������ռ�ͷ�����
		SoapObject soapObject = new SoapObject(NAME_SPACE, METHOD_ECHOW);

		soapObject.addProperty("in", in);

		// ����SoapSerializationEnvelope���󲢴���SOAPЭ��İ汾��
		final SoapSerializationEnvelope soapserial = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		soapserial.bodyOut = soapObject;
		// ������.NET�ṩ��Web service���������õļ�����
		soapserial.dotNet = true;
		// ʹ��Callable��Future�����������߳�
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						String detail = new String();
						// ����HttpTransportSE�����call���������� webservice
						httpSE.call(NAME_SPACE + METHOD_ECHOW, soapserial);
						if (soapserial.getResponse() != null) {
							// ��ȡ��������Ӧ���ص�SOAP��Ϣ
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
	
	// ��ȡWebService Url
	private static String getServerUrl() {
		ShaPreOpe shaPreOpe = new ShaPreOpe(ContextUtil.getInstance());
		final String ipPort = shaPreOpe.read("ip", "") + ":" + shaPreOpe.read("port", "");
		String ServerUrl = SRV_URL_PRE + ipPort + SRV_URL_SUF;
		return ServerUrl;
	}
	
}
