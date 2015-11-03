package com.test.junit;

import java.io.File;

import junit.framework.Assert;

import org.junit.Ignore;

import com.xml.object.builder.api.Node;
import com.xml.object.builder.api.XMLToNodeObjectConvertorImpl;

public class NodeToMapStructureUnitTest {

	@Ignore
	public void testGivenXMLWithAttributesNodesEceptForListNodesExpectFinalMapContainsAtributeInKeyValuePair() {

		XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
				new File(
						"C:\\Users\\lalit goyal\\workspace\\xmlToObjectUtils\\src\\test\\resources\\SampleInputXML.xml"));
		Node rootNode = nodeObject.convertToNodeObject();
		try {
			Assert.assertEquals(
					rootNode.getMapStructure(),
					"{xml={Adresses={id=\"11\", Value=[{Type=Postal, AddressInfo={Value={State={Value=VICTORIA, StateCode=\"VIC\"}, Address1=D-1/126A}, type=\"street\"}}, {Type=Postal, AddressInfo={Value={State={Value=VICTORIA, StateCode=\"VIC\"}, Address1=D-1/126A}, type=\"postal\"}}]}}}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
