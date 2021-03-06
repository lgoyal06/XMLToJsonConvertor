package com.test.junit;

import org.junit.Ignore;

import com.xml.to.tree.structure.object.builder.Node;
import com.xml.to.tree.structure.object.builder.XMLToNodeObjectConvertorImpl;

public class XMLToNodeObjectConversionUnitTests {

	static String sampleXML = "<xml><a  c='s' as='asasaS'   s=sd><b>sdfsd</b></a></xml>";

	@Ignore
	public void test() {

		XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
				sampleXML);

		Node rootNode = nodeObject.convertToNodeObject();
		System.out.println(rootNode.getValue("b"));
		System.out.println(rootNode.getAttributes("a"));
		System.out.println(rootNode.getAttributeValue("a", "c"));

	}

}
