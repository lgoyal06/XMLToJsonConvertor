package com.node.object.traverser;

import java.util.List;
import java.util.Map;

import com.xml.node.object.Iterator.Iterators;

/**
 * @author lalit goyal
 * 
 * @param <T>
 */
public interface NodeTraverser<T> {

	/*
	 * Retrieve the value of an element i.e leaf node
	 */
	String getValue(String element);

	/*
	 * Retrieve All Attributes of given element as Map
	 */
	Map<String, String> getAttributes(String element);

	/*
	 * Retrieve specific value of an Attribute of given element
	 */
	String getAttributeValue(String element, String attributeName);

	/*
	 * Retrieve Child And Sibling nodes of given XML element
	 */
	List<T> getChildNodes(String xmlElement);

	/*
	 * Iterate over all nodes in breath first manner
	 */
	Iterators<T> breathFirst();

	/*
	 * Iterate over all nodes in depth first manner
	 */
	Iterators<T> depthFirst();

	/*
	 * Return the List for element in list format
	 */
	List<Map<?, ?>> getListElement(String element);

}