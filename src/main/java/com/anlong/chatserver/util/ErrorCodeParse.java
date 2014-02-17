package com.anlong.chatserver.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ErrorCodeParse {
	private static ErrorCodeParse errorXmlObject = null;
	private static Map<Integer, String> errorCodeMap = new HashMap<Integer, String>();
	private static final String ERRORCODE_CONFIG_PATH = "WEB-INF/classes/config/errorCodeConfig.xml";
	private ErrorCodeParse() {
		readErrorXML();
	}

	// public static ErrorCodeParse getInstance() {
	// if (null == errorXmlObject) {
	// errorXmlObject = new ErrorCodeParse();
	// }
	// return errorXmlObject;
	// }

	/**
	 * 读取错误配置文件
	 */
	private void readErrorXML() {
		DocumentBuilderFactory domfac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dombuilder;
		try {
			dombuilder = domfac.newDocumentBuilder();
			InputStream is = new FileInputStream(new Utils().getWebRoot() + ERRORCODE_CONFIG_PATH);
			Document doc = dombuilder.parse(is);
			Element root = doc.getDocumentElement();
			NodeList errorCodeList = root.getChildNodes();
			if (errorCodeList != null) {
				for (int i = 0; i < errorCodeList.getLength(); i++) {
					Node errorCode = errorCodeList.item(i);
					if (errorCode.getNodeType() == Node.ELEMENT_NODE) {
						String key = errorCode.getAttributes().getNamedItem("errorCode").getNodeValue();
						String value = errorCode.getAttributes().getNamedItem("strErrMsg").getNodeValue();
						// System.out.println(key + "  " + value);
						try {
							errorCodeMap.put(Integer.parseInt(key), value);
						} catch (Exception e) {
						}

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取错误信息
	 * 
	 * @param errorCode
	 * @return
	 */
	public static String getErrorMessage(int errorCode) {
		if (null == errorXmlObject) {
			errorXmlObject = new ErrorCodeParse();
		}
		if (errorCodeMap.containsKey(errorCode)) {
			return errorCodeMap.get(errorCode);
		} else {
			return errorCodeMap.get(1);
		}
	}

	public static void main(String[] args) throws Exception {
		// XML文件
		// System.out.println(ErrorCodeParse.getErrorMessage(111));
		// System.out.println(ErrorCodeParse.getErrorMessage(112));
		// System.out.println(ErrorCodeParse.getErrorMessage(113));
	}
}
