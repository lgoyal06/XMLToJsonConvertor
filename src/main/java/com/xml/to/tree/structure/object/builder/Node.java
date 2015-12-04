package com.xml.to.tree.structure.object.builder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

	public LinkedHashMap<String, String> getAttribute() {
		return attribute;
	}

	public List<Node> getChild() {
		return child;
	}

	protected void setChild(List<Node> child) {
		this.child = child;
	}

	public String getTagName() {
		return tagName;
	}

	protected void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public String getVal() {
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
}
