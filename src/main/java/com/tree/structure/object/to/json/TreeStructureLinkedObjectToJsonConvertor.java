package com.tree.structure.object.to.json;

public interface TreeStructureLinkedObjectToJsonConvertor<T> {

	/*
	 * Return Object in JSON Structure
	 */
	String getJsonStructure(T node) throws Exception;

}