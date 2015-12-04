package com.tree.structure.object.to.json;

import groovy.json.JsonBuilder;
import nu.xom.Element;

import com.tree.structure.object.to.jsonmap.ElementObjectToJsonMapConvertorImpl;

public class ElementObjectToJsonConvertorImpl implements
		TreeStructureLinkedObjectToJsonConvertor<Element> {

	@Override
	public String getJsonStructure(Element node) throws Exception {
		return new JsonBuilder(
				new ElementObjectToJsonMapConvertorImpl().getMapStructure(node))
				.toString();
	}

}
