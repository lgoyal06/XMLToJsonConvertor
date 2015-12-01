package com.xml.object.builder.api;

import java.util.LinkedHashMap;

import nu.xom.Element;


public interface LinkedTreeStructureObjectToJsonMapConvertor {

	/*
	 * Return the result in LinkedHashMap Structure and Uses the Node Linked
	 * Tree Structure Object
	 */
	LinkedHashMap<String, Object> getMapStructure(Node node) throws Exception;

	/*
	 * Return the result in LinkedHashMap Structure and Uses the nu.xom.Element
	 * Linked Tree Structure Object
	 */

	LinkedHashMap<String, Object> getMapStructure(Element node)
			throws Exception;

}