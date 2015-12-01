package com.xml.object.builder.api;

import groovy.json.JsonBuilder;
import nu.xom.Element;


public class LinkedTreeStructureObjectToJsonConvertorImpl implements
		LinkedTreeStructureObjectToJsonConvertor {

	@Override
	public String getJsonStructure(Node node) throws Exception {
		return new JsonBuilder(
				new LinkedTreeStructureObjectToJsonMapConvertorImpl()
						.getMapStructure(node)).toString();
	}

	public String getJsonStructure(Element node) throws Exception {

		return new JsonBuilder(
				new LinkedTreeStructureObjectToJsonMapConvertorImpl()
						.getMapStructure(node)).toString();
	}

}
