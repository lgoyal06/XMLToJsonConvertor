package com.xml.object.builder.api;

import groovy.json.JsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import nu.xom.Element;
import nu.xom.Elements;

import com.node.object.traverser.NodeTraverser;
import com.xml.node.object.Iterator.Iterators;

/**
 * @author lalit goyal
 * 
 */

public class Node implements NodeTraverser<Node> {

	private List<Node> child = new ArrayList<>();
	private String tagName;
	private String val;
	private LinkedHashMap<String, String> attribute = new LinkedHashMap<>();

	protected void setAttribute(LinkedHashMap<String, String> attribute) {
		this.attribute = attribute;
	}

	protected LinkedHashMap<String, String> getAttribute() {
		return attribute;
	}

	protected List<Node> getChild() {
		return child;
	}

	protected void setChild(List<Node> child) {
		this.child = child;
	}

	protected String getTagName() {
		return tagName;
	}

	protected void setTagName(String tagName) {
		this.tagName = tagName;
	}

	protected String getVal() {
		return val;
	}

	protected void setVal(String val) {
		this.val = val;
	}

	@Override
	public String getValue(String xmlElement) {
		Stack<Node> stack = new Stack<Node>();
		Node obj = this;
		stack.push(obj);
		while (stack.top > 0) {
			Node element = stack.pop();
			if (xmlElement.equalsIgnoreCase(element.getTagName())) {
				return element.getVal();
			}
			if (element.getChild() == null) {
				continue;
			} else {
				for (int child = element.getChild().size() - 1; child >= 0; --child)
					stack.push(element.getChild().get(child));
			}
		}
		return null;
	}

	@Override
	public LinkedHashMap<String, String> getAttributes(String xmlElement) {
		Stack<Node> stack = new Stack<Node>();
		Node obj = this;
		stack.push(obj);
		while (stack.top > 0) {
			Node element = stack.pop();
			if (xmlElement.equalsIgnoreCase(element.getTagName())) {
				return element.getAttribute();
			}
			if (element.getChild() == null) {
				continue;
			} else {
				for (int child = element.getChild().size() - 1; child >= 0; --child)
					stack.push(element.getChild().get(child));
			}
		}
		return new LinkedHashMap<>();
	}

	@Override
	public String getAttributeValue(String xmlElement, String attributeName) {
		return getAttributes(xmlElement).get(attributeName);
	}

	@Override
	public List<Node> getChildNodes(String xmlElement) {
		// Priority 6 TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterators<Node> breathFirst() {
		// Priority 4 TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterators<Node> depthFirst() {
		// Priority 3 TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<?, ?>> getListElement(String xmlElement) {
		// Priority 2 TODO Auto-generated method stub
		return null;
	}

	public class NodeIterator implements Iterators<Node> {

		@Override
		public boolean hasNextElement() {
			// Priority 5.1 TODO Auto-generated method stub
			return false;
		}

		@Override
		public Node nextElement() {
			// Priority 5.2 TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * @return the Map Structure of the Node object This structure is really
	 * 
	 *         Easy to be traversed in Freemarker as freemarker takes the input
	 *         data in Map structure
	 * 
	 *         Easy to be converted into Json
	 * 
	 *         Attributes is now supported by the Map Structure for all nodes
	 *         except for list type nodes
	 * 
	 *         Assumption : Only Leaf Nodes can have attribute fields
	 * @throws Exception
	 */
	public LinkedHashMap<String, Object> getMapStructure() throws Exception {
		Node element = this;
		Object finalObj = buildMap(element);
		if (finalObj instanceof LinkedHashMap<?, ?>) {
			LinkedHashMap<String, Object> resultantMap = new LinkedHashMap<>();
			resultantMap.put(this.getTagName(), finalObj);
			return resultantMap;
		} else if (finalObj instanceof String) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put(element.getTagName(), element.getVal());
			return map;
		} else {
			throw new Exception(
					"XML Structure is either invalid or not supported by this Utility. For more info please read the Help Page");
		}
	}

	private Object buildMap(Node element) {
		if (element.getChild() != null) {
			List<Node> childNodeList = element.getChild();
			if (childNodeList.size() == 1) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				if (childNodeList.get(0).getAttribute().size() > 0) {
					LinkedHashMap<String, Object> map1 = new LinkedHashMap<>();
					map1.putAll(childNodeList.get(0).getAttribute());
					map1.put("Value", buildMap(childNodeList.get(0)));
					map.put(childNodeList.get(0).getTagName(), map1);
				} else {
					map.put(childNodeList.get(0).getTagName(),
							buildMap(childNodeList.get(0)));
				}
				return map;
			} else if (childNodeList.size() > 1) {
				if (childNodeList.get(0).getTagName()
						.equals(childNodeList.get(1).getTagName())) {
					List<Object> list = new ArrayList<>();
					for (Node node : childNodeList) {
						list.add(buildMap(node));
					}
					LinkedHashMap<String, Object> map1 = new LinkedHashMap<>();
					map1.put(childNodeList.get(0).getTagName(), list);
					return map1;
				} else {
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					for (Node node : childNodeList) {
						if (node.getAttribute().size() > 0) {
							LinkedHashMap<String, Object> map1 = new LinkedHashMap<>();
							map1.putAll(node.getAttribute());
							map1.put("Value", buildMap(node));
							map.put(node.getTagName(), map1);
						} else
							map.put(node.getTagName(), buildMap(node));
					}
					return map;
				}
			}
		}
		return element.getVal() != null ? element.getVal().trim() : "";
	}

	private class Stack<T> {
		private List<T> node = new ArrayList<>();

		int top = 0;

		public T pop() {
			if (top > 0) {
				return node.remove(--top);
			} else {
				throw null;
			}
		}

		public void push(T node) {
			this.node.add(node);
			++top;
		}
	}

	@Override
	public String getJsonStructure() throws Exception {
		Node element = this;
		Object finalObj = buildJson(element);
		if (finalObj instanceof HashMap<?, ?>) {
			HashMap<String, Object> resultantMap = new HashMap<>();
			resultantMap.put("\"" + this.getTagName() + "\"", finalObj);
			return resultantMap.toString().replaceAll("=", ":");
		} else if (finalObj instanceof String) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("\"" + element.getTagName() + "\"", element.getVal());
			return map.toString();
		} else {
			throw new Exception(
					"XML Structure is either invalid or not supported by this Utility. For more info please read the Help Page");
		}
	}

	private Object buildJson(Node element) {
		if (element.getChild() != null) {
			List<Node> childNodeList = element.getChild();
			if (childNodeList.size() == 1) {
				HashMap<String, Object> map = new HashMap<>();
				if (childNodeList.get(0).getAttribute().size() > 0) {
					HashMap<String, Object> map1 = new HashMap<>();
					addAttributesToMap(map1, childNodeList.get(0)
							.getAttribute());
					map1.put("\"Value\"", buildJson(childNodeList.get(0)));
					map.put("\"" + childNodeList.get(0).getTagName() + "\"",
							map1);
				} else {
					map.put("\"" + childNodeList.get(0).getTagName() + "\"",
							buildJson(childNodeList.get(0)));
				}
				return map;
			} else if (childNodeList.size() > 1) {
				if (childNodeList.get(0).getTagName()
						.equals(childNodeList.get(1).getTagName())) {
					List<Object> list = new ArrayList<>();
					for (Node node : childNodeList) {
						list.add(buildJson(node));
					}
					HashMap<String, Object> map1 = new HashMap<>();
					map1.put("\"" + childNodeList.get(0).getTagName() + "\"",
							list);
					return map1;
				} else {
					HashMap<String, Object> map = new HashMap<>();
					for (Node node : childNodeList) {
						if (node.getAttribute().size() > 0) {
							HashMap<String, Object> map1 = new HashMap<>();
							addAttributesToMap(map1, node.getAttribute());
							map1.put("\"Value\"", buildJson(node));
							map.put("\"" + node.getTagName() + "\"", map1);
						} else {
							map.put("\"" + node.getTagName() + "\"",
									buildJson(node));
						}
					}
					return map;
				}
			}
		}
		return "\"" + (element.getVal() != null ? element.getVal().trim() : "")
				+ "\"";
	}

	private HashMap<String, Object> addAttributesToMap(
			HashMap<String, Object> mainMap, HashMap<String, String> map) {

		Set<Entry<String, String>> entrySet = map.entrySet();
		for (Entry<String, String> entry : entrySet) {
			mainMap.put("\"" + entry.getKey() + "\"", "\"" + entry.getValue()
					+ "\"");
		}
		return mainMap;
	}

	public LinkedHashMap<String, Object> getMapStructure(Element element)
			throws Exception {
		Object finalObj = buildMap(element);
		if (finalObj instanceof LinkedHashMap<?, ?>) {
			LinkedHashMap<String, Object> resultantMap = new LinkedHashMap<String, Object>();
			resultantMap.put(element.getLocalName(), finalObj);
			return resultantMap;
		} else if (finalObj instanceof String) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put(element.getLocalName(), setValue(element));
			return map;
		} else {
			throw new Exception(
					"XML Structure is either invalid or not supported by this Utility.");
		}
	}

	@SuppressWarnings("unchecked")
	private Object buildMap(Element element) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		Elements childNodeList = element.getChildElements();
		int childCount = childNodeList.size();
		if (childCount > 0) {
			if ((childCount == 1
					&& childNodeList.get(0).getAttribute("dataType") != null && ("Array"
						.equalsIgnoreCase(childNodeList.get(0)
								.getAttribute("dataType").getValue())))
					|| (childCount > 1 && childNodeList.get(0).getLocalName()
							.equals(childNodeList.get(1).getLocalName()))) {
				List<Object> list = new ArrayList<Object>();
				for (int count = 0; count < childCount; ++count) {
					list.add(buildMap(childNodeList.get(count)));
				}
				LinkedHashMap<String, Object> mapContainingListElements = new LinkedHashMap<String, Object>();
				mapContainingListElements.put(childNodeList.get(0)
						.getLocalName(), list);
				return mapContainingListElements;
			}
			for (int count = 0; count < childCount; ++count) {
				Element node = childNodeList.get(count);
				if (node.getAttributeCount() > 0
						&& (node.getAttributeCount() == 1 && !node
								.getAttribute(0).getLocalName()
								.equalsIgnoreCase("dataType"))) {
					LinkedHashMap<String, Object> mapWithAttributeFields = new LinkedHashMap<String, Object>();
					if (node.getChildElements().size() == 0) {
						mapWithAttributeFields.put("value", buildMap(node));
					} else {
						mapWithAttributeFields
								.putAll((Map<? extends String, ? extends Object>) buildMap(node));
					}
					for (int i = 0; i < node.getAttributeCount(); ++i) {
						if (!node.getAttribute(i).getLocalName()
								.equalsIgnoreCase("dataType")) {
							mapWithAttributeFields.put(node.getAttribute(i)
									.getLocalName(), node.getAttribute(i)
									.getValue());
						}
					}
					map.put(node.getLocalName(), mapWithAttributeFields);
				} else {
					map.put(node.getLocalName(), buildMap(node));
				}
			}
			return map;
		} else {
			return setValue(element);
		}
	}

	private Object setValue(Element element) {
		if ("Integer".equalsIgnoreCase(element.getAttributeValue("dataType"))) {
			if (element.getValue() != null
					&& element.getValue().trim().equalsIgnoreCase("")) {
				return null;
			}
			return element.getValue() != null ? Integer.valueOf(element
					.getValue().replaceAll("(\\r|\\n|\\t)+", "")) : "";
		}

		if ("Boolean".equalsIgnoreCase(element.getAttributeValue("dataType"))) {
			return element.getValue() != null ? Boolean.valueOf(element
					.getValue().replaceAll("(\\r|\\n|\\t)+", "")) : "";
		}

		return element.getValue() != null ? element.getValue().replaceAll(
				"(\\r|\\n|\\t)+", "") : "";
	}

	@Override
	public String getJsonStructure(Element root) throws Exception {

		return new JsonBuilder(getMapStructure(root)).toPrettyString();
	}

}
