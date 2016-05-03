package com.android.net;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public abstract class BaseHandler extends DefaultHandler {
	// 解析时导入的是xml字符串
	public boolean parse(String xmlString) {
		return false;
	};

	// 解析时导入的是xml流
	public boolean parse(InputStream xmlInputStream) {
		return false;
	};

	// 解析时导入的是xml的File类型数据
	public boolean parse(File file) {
		return false;
	};

	public void parserXml(BaseHandler baseHandler, String xmlString) throws Exception {
		if (xmlString == null || xmlString.length() == 0)
			return;
		// 注册baseHandler事件并获取XMLReader实例
		XMLReader xmlReader = xmlReader(baseHandler);
		// 将字符串转换成StringReader对象
		StringReader read = new StringReader(xmlString);
		// 将字StringReader对象转换成InputSource对象
		InputSource source = new InputSource(read);
		// 将一个xml字符串变成一个java可以处理的InputSource对象载入xmlReader的parse方法中，解析正式开始
		xmlReader.parse(source);
		read.close();
	}

	// 把自己写的baseHandler注册到XMLReader中并返回XMLReader对象
	private XMLReader xmlReader(BaseHandler baseHandler) {
		// 获得解析工厂的实例
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		XMLReader xmlReader = null;
		try {
			// 获得工厂解析器
			parser = factory.newSAXParser();
			// 从SAXPsrser中得到一个XMLReader实例
			xmlReader = parser.getXMLReader();
			// 把自己写的baseHandler注册到XMLReader中(注册处理XML文档解析事件ContentHandler)
			xmlReader.setContentHandler(baseHandler);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlReader;
	}

	public void parserXml(BaseHandler baseHandler, InputStream xmlInputStream) throws Exception {
		if (xmlInputStream == null)
			return;
		// 获得解析工厂的实例
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 获得工厂解析器
		SAXParser parser = factory.newSAXParser();
		// 将一个xml流载入parse方法中，解析正式开始
		parser.parse(xmlInputStream, baseHandler);
	}

	public void parserXml(BaseHandler baseHandler, File file) throws Exception {
		if (file == null)
			return;
		// 获得解析工厂的实例
		SAXParserFactory factory = SAXParserFactory.newInstance();
		// 获得工厂解析器
		SAXParser parser = factory.newSAXParser();
		// 将一个xml文件对象File载入parse方法中，解析正式开始
		parser.parse(file, baseHandler);
	}

	/*
	 * 读取标签里的值,ch用来存放某行的xml的字符数据,包括标签,初始大小是2048, 每解释到新的字符会把它添加到char[]里。 *
	 * 注意,这个char字符会自己管理存储的字符, 并不是每一行就会刷新一次char,start,length是由xml的元素数据确定的,
	 * 这里一个正标签，反标签都会被执行一次characters，所以在反标签时不用获得其中的值
	 * 这里获取的值是标签之间的值如:<item>*********</item>表示星号部分的值
	 */
	public abstract void characters(char[] ch, int start, int length) throws SAXException;

	// 用户处理文档解析结束事件
	public void endDocument() throws SAXException {
	};

	// 用于处理元素结束事件
	public abstract void endElement(String uri, String localName, String qName) throws SAXException;

	// 用于处理文档解析开始事件
	public void startDocument() throws SAXException {
	};

	/*
	 * 用于处理元素开始事件, 在解释到一个开始元素时会调用此方法.但是当元素有重复时可以自己写算法来区分 这些重复的元素.qName是什么?
	 * <name:page ll=""></name:page>这样写就会抛出SAXException错误 通常情况下qName等于localName
	 */
	public abstract void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException;
}
