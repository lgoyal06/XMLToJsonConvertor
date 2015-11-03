package com.test.junit;

import java.io.File;

import com.xml.object.builder.api.Node;
import com.xml.object.builder.api.XMLToNodeObjectConvertorImpl;

public class NodeTest {

	public static void main(String... s) throws Exception {
		XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
				new File(
						"C:\\Users\\lalit goyal\\workspace\\xmlToObjectUtils\\src\\test\\resources\\SampleInputXML1.xml"));
		Node rootNode = nodeObject.convertToNodeObject();
		System.out.println(rootNode.getMapStructure().toString());
		// Assert.assertEquals(rootNode.getMapStructure().toString(),"");
	}
}