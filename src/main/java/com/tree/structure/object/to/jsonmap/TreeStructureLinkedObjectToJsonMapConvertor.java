package com.tree.structure.object.to.jsonmap;

import java.util.LinkedHashMap;

public interface TreeStructureLinkedObjectToJsonMapConvertor<T> {

	/*
	 * Return the result in LinkedHashMap Structure and Uses the nu.xom.Element
	 * Linked Tree Structure Object
	 */

	LinkedHashMap<String, Object> getMapStructure(T node) throws Exception;

}