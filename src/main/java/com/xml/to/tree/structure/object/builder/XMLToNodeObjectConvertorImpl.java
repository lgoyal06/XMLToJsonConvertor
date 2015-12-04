package com.xml.to.tree.structure.object.builder;

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
			 * 
			 * Code to remove top element from the Stack in case closing of XML
			 * Non Leaf Element is found
			 * 
			 */

			if (xml.substring(indexTagStart, indexTagEnd + 1).contains("</")
					&& (stack.getTopElement().equalsIgnoreCase(xml.substring(
							indexTagStart + 2, indexTagEnd)))) {
				stack.popTag();
				return parentNode;
			}

			else {
				/**
				 * Creating the New Node and adding it as child of Parent Node
				 */
				Node newNode = new Node();
				parentNode.getChild().add(newNode);
				/**
				 * 
				 * Code to add Self closing Tags i.e. <a/> as Node Object
				 * 
				 */
				if (xml.substring(indexTagStart, indexTagEnd + 1)
						.contains("/>")) {
					newNode.setVal(null);
					newNode.setChild(null);
					newNode.setTagName(xml.substring(indexTagStart + 1,
							indexTagEnd - 1).trim());
					continue;
				}
				/**
				 * TODO Write generic Code That handles adding attributes to all
				 * nodes for all XML Elements i.e. Leaf , composite and self
				 * closing elements
				 * 
				 * 
				 * Code to Populate Attribute for given XML Element
				 * 
				 * Move up
				 * 
				 * */
				addAttributesToNode(xml, indexTagStart, indexTagEnd, newNode);
				/**
				 * Code to add Tag name of non self closing XML elements to Node
				 * Object
				 */
				stack.pushTag(xml.substring(indexTagStart + 1, indexTagEnd)
						.replaceAll(" ", ",").split(",")[0]);
				secondIndexTagStart = xml.indexOf("<", counter);
				secondIndexTagEnd = xml.indexOf(">", counter);
				newNode.setTagName(stack.getTopElement());
				/**
				 * Code to set value for leaf element to Node Object
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
				 * Recursive call to get Value of Composite Elements i.e. Non
				 * Leaf Node
				 **/
				else {
					buildLinkeNodeObject(newNode, stack, xml);
				}
			}
		}
		return parentNode.getChild().get(0);
	}

	private void addAttributesToNode(String xml, int indexTagStart,
			int indexTagEnd, Node newNode) {

		// TODO : Write split logic based upon some pattern matching that
		// should handle all type of values of
		// attribute i.e.
		// 1.'',
		// 2."",
		// 3.' ',
		// 4." ",
		// 5.
		String[] attributesArray = xml
				.substring(indexTagStart + 1, indexTagEnd).replaceAll(" ", ",")
				.split(",");
		for (int i = 1; i < attributesArray.length; ++i) {
			if (attributesArray[i].trim().equals("")) {
				continue;
			}
			String[] attributekeyValueSplitter = attributesArray[i].split("=");
			newNode.getAttribute()
					.put(attributekeyValueSplitter[0].trim(),
							attributekeyValueSplitter[1] != null ? attributekeyValueSplitter[1]
									.trim().replaceAll("\"", "")
									.replaceAll("\'", "")
									: "");
		}
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

		public List<String> tagNameList = new ArrayList<>();
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