package com.xml.object.builder.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	private HashMap<String, String> attribute = new HashMap<>();

	protected void setAttribute(HashMap<String, String> attribute) {
		this.attribute = attribute;
	}

	protected HashMap<String, String> getAttribute() {
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
			Node tmpNode = stack.pop();
			if (xmlElement.equalsIgnoreCase(tmpNode.getTagName())) {
				return tmpNode.getVal();
			}
			if (tmpNode.getChild() == null) {
				continue;
			} else {
				for (int child = tmpNode.getChild().size() - 1; child >= 0; --child)
					stack.push(tmpNode.getChild().get(child));
			}
		}
		return null;
	}

	@Override
	public HashMap<String, String> getAttributes(String xmlElement) {
		Stack<Node> stack = new Stack<Node>();
		Node obj = this;
		stack.push(obj);
		while (stack.top > 0) {
			Node tmpNode = stack.pop();
			if (xmlElement.equalsIgnoreCase(tmpNode.getTagName())) {
				return tmpNode.getAttribute();
			}
			if (tmpNode.getChild() == null) {
				continue;
			} else {
				for (int child = tmpNode.getChild().size() - 1; child >= 0; --child)
					stack.push(tmpNode.getChild().get(child));
			}
		}
		return new HashMap<>();
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
	public HashMap<String, Object> getMapStructure() throws Exception {
		Node tmpNode = this;
		Object finalObj = buildMap(tmpNode);
		if (finalObj instanceof HashMap<?, ?>) {
			HashMap<String, Object> resultantMap = new HashMap<>();
			resultantMap.put(this.getTagName(), finalObj);
			return resultantMap;
		} else if (finalObj instanceof String) {
			HashMap<String, Object> map = new HashMap<>();
			map.put(tmpNode.getTagName(), tmpNode.getVal());
			return map;
		} else {
			throw new Exception(
					"XML Structure is either invalid or not supported by this Utility. For more info please read the Help Page");
		}
	}

	private Object buildMap(Node tmpNode) {
		if (tmpNode.getChild() != null) {
			List<Node> childNodeList = tmpNode.getChild();
			if (childNodeList.size() == 1) {
				HashMap<String, Object> map = new HashMap<>();
				if (childNodeList.get(0).getAttribute().size() > 0) {
					HashMap<String, Object> map1 = new HashMap<>();
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
					HashMap<String, Object> map1 = new HashMap<>();
					map1.put(childNodeList.get(0).getTagName(), list);
					return map1;
				} else {
					HashMap<String, Object> map = new HashMap<>();
					for (Node node : childNodeList) {
						if (node.getAttribute().size() > 0) {
							HashMap<String, Object> map1 = new HashMap<>();
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
		return tmpNode.getVal() != null ? tmpNode.getVal().trim() : "";
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
		Node tmpNode = this;
		Object finalObj = buildJson(tmpNode);
		if (finalObj instanceof HashMap<?, ?>) {
			HashMap<String, Object> resultantMap = new HashMap<>();
			resultantMap.put("\"" + this.getTagName() + "\"", finalObj);
			return resultantMap.toString().replaceAll("=", ":");
		} else if (finalObj instanceof String) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("\"" + tmpNode.getTagName() + "\"", tmpNode.getVal());
			return map.toString();
		} else {
			throw new Exception(
					"XML Structure is either invalid or not supported by this Utility. For more info please read the Help Page");
		}
	}

	private Object buildJson(Node tmpNode) {
		if (tmpNode.getChild() != null) {
			List<Node> childNodeList = tmpNode.getChild();
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
		return "\"" + (tmpNode.getVal() != null ? tmpNode.getVal().trim() : "")
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

}
