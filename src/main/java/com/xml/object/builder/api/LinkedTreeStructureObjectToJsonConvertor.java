package com.xml.object.builder.api;

import nu.xom.Element;


public interface LinkedTreeStructureObjectToJsonConvertor {

	/*
	 * Return Object in JSON Structure
	 */
	String getJsonStructure(Node node) throws Exception;

	/*
	 * Return Object in JSON Structure
	 */
	String getJsonStructure(Element node) throws Exception;

}