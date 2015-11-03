package com.test.junit;

import java.io.File;

import org.junit.Test;

import com.xml.object.builder.api.Node;
import com.xml.object.builder.api.XMLToNodeObjectConvertorImpl;

public class NodeToMapStructureUnitTest {

	@Test
	public void testGivenXMLWithAttributesNodesEceptForListNodesExpectFinalMapContainsAtributeInKeyValuePair() {
		XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
				new File(
						"C:\\Users\\lalit goyal\\workspace\\xmlToObjectUtils\\src\\test\\resources\\SampleInputXML.xml"));
		Node rootNode = nodeObject.convertToNodeObject();
		try {
			System.out.println(rootNode.getMapStructure().toString());
			// TODO
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
