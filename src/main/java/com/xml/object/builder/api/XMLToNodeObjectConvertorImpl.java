package com.xml.object.builder.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * @author lalit goyal
 * 
 *         TODO 1.Handle <a/> tags
 * 
 *         2.Handle list elements
 */

public final class XMLToNodeObjectConvertorImpl implements
		XMLToNodeObjectConvertor {

	private String inputXML;
	private int counter = 0;
	private Node nodeObject = new Node();
	private StackToStoreTags stackToStoreTags = new StackToStoreTags();

	@Override
	public Node convertToNodeObject() {
		return buildLinkeNodeObject(nodeObject, stackToStoreTags, inputXML);
	}

	private Node buildLinkeNodeObject(Node parentNode, StackToStoreTags stack,
			String xml) {

		while (stack.top > 0 || counter == 0) {
			int indexTagStart = xml.indexOf("<", counter);
			int indexTagEnd = xml.indexOf(">", counter);
			int secondIndexTagStart, secondIndexTagEnd;
			counter = indexTagEnd + 1;

			/**
			 * Code to check for </> closing tag.
			 */
			if (xml.charAt(indexTagStart + 1) == '/'
					&& (stack.getTopElement().equalsIgnoreCase(xml.substring(
							indexTagStart + 2, indexTagEnd)))) {
				stack.popTag();
				return parentNode;
			}

			/**
			 * Code for <> non closing tag.
			 */
			else {
				Node newNode = new Node();
				parentNode.getChild().add(newNode);

				String[] splitter = addAttributesToElement(xml, indexTagStart,
						indexTagEnd, newNode);

				stack.pushTag(splitter[0]);
				secondIndexTagStart = xml.indexOf("<", counter);
				secondIndexTagEnd = xml.indexOf(">", counter);
				newNode.setTagName(stack.getTopElement());
				/**
				 * Code for leaf node
				 **/
				if (stack.top > 0
						&& xml.substring(secondIndexTagStart + 2,
								secondIndexTagEnd)
								.equals(stack.getTopElement())) {
					newNode.setVal(xml.substring(indexTagEnd + 1,
							secondIndexTagStart));
					newNode.setChild(null);
					stack.popTag();
					counter = secondIndexTagEnd + 1;
				}
				/**
				 * Child Node exists *
				 **/
				else {
					buildLinkeNodeObject(newNode, stack, xml);
				}
			}
		}
		return parentNode.getChild().get(0);
	}

	private String[] addAttributesToElement(String xml, int indexTagStart,
			int indexTagEnd, Node newNode) {
		String[] attributesArray = xml
				.substring(indexTagStart + 1, indexTagEnd).replaceAll(" ", ",")
				.split(",");
		for (int i = 1; i < attributesArray.length; ++i) {
			if (attributesArray[i].trim().equals(""))
				continue;
			String[] attributekeyValueSplitter = attributesArray[i].split("=");
			newNode.getAttribute().put(attributekeyValueSplitter[0].trim(),
					attributekeyValueSplitter[1].trim());
		}
		return attributesArray;
	}

	/**
	 * 
	 * Constructor that takes XML data as String
	 * **/
	public XMLToNodeObjectConvertorImpl(String xml) {
		this.inputXML = xml;
	}

	/**
	 * Constructor that read XML data from a File
	 * 
	 * */
	public XMLToNodeObjectConvertorImpl(File xmlFile) {
		try {
			inputXML = FileUtils.readFileToString(xmlFile, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class StackToStoreTags {

		private List<String> tagNameList = new ArrayList<>();
		private int top = 0;

		public String popTag() {
			return tagNameList.remove(--top);
		}

		public void pushTag(String tagName) {
			tagNameList.add(top, tagName);
			++top;
		}

		public String getTopElement() {
			int temp = top;
			return tagNameList.get(--temp);
		}
	}
}