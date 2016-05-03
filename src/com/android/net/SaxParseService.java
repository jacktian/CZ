package com.android.net;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.android.common.HomeNews;

public class SaxParseService extends DefaultHandler {
	private List<HomeNews> books = null;
	private HomeNews book = null;
	private String preTag = null;// 作用是记录解析时的上一个节点名称

	public List<HomeNews> getBooks(InputStream xmlStream) throws Exception {

		SAXParserFactory factory = SAXParserFactory.newInstance();

		SAXParser parser = factory.newSAXParser();

		SaxParseService handler = new SaxParseService();

		parser.parse(xmlStream, handler);

		return handler.getBooks();

	}

	public List<HomeNews> getBooks() {

		return books;

	}

	@Override
	public void startDocument() throws SAXException {

		books = new ArrayList<HomeNews>();

	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if ("P_News".equals(qName)) {
			book = new HomeNews();
			book.setId(Integer.parseInt(attributes.getValue(0)));

		}
		preTag = qName;// 将正在解析的节点名称赋给 preTag
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if ("P_News".equals(qName)) {
			books.add(book);
			book = null;

		}

		preTag = null;
		/**
		 * 当解析结束时置为空。这里很重要，例如，当图中画 3 的位置结束后，会调用这个方法
		 * 
		 * ，如果这里不把preTag 置为 null ，根据 startElement(....) 方法， preTag 的值还是 book
		 * ，当文档顺序读到图
		 * 
		 * 中标记4 的位置时，会执行 characters(char[] ch, int start, int length) 这个方法，而
		 * characters(....) 方
		 * 
		 * 法判断preTag!=null ，会执行 if 判断的代码，这样就会把空值赋值给 book ，这不是我们想要的。
		 */

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (preTag != null) {
			String content = new String(ch, start, length);
			if ("description".equals(preTag)) {
				book.setDescription(content);
			} else if ("imagefile".equals(preTag)) {
				book.setImagefile(content);
			}
		}

	}
}
