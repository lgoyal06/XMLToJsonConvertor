package com.tree.structure.object.to.json;

import com.tree.structure.object.to.jsonmap.NodeObjectToJsonMapConvertorImpl;
import com.xml.to.tree.structure.object.builder.Node;

import groovy.json.JsonBuilder;

public class NodeObjectToJsonConvertorImpl implements
		TreeStructureLinkedObjectToJsonConvertor<Node> {

	@Override
	public String getJsonStructure(Node node) throws Exception {
		return new JsonBuilder(
				new NodeObjectToJsonMapConvertorImpl().getMapStructure(node))
				.toString();
	}

}
