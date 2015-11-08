package com.test.junit;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.xml.object.builder.api.Node;
import com.xml.object.builder.api.XMLToNodeObjectConvertorImpl;

public class XMLToJsonStructureUnitTest {

	@Test
	public void testGivenXMLWithListStructureWhenUtilityRunExpectJsonWithListStructureAsResult() {
		try {
			String expecedJson = "{\"xml\":{\"Adresses\":{\"Address\":[{\"AddressInfo\":{\"State\":{\"Value\":\"VICTORIA\", \"StateCode\":\"VIC\"}, \"Address1\":\"D-1/126A\"}, \"Type\":\"Postal\"}, {\"AddressInfo\":{\"State\":{\"Value\":\"VICTORIA\", \"StateCode\":\"VIC\"}, \"Address1\":\"D-1/126A\"}, \"Type\":\"Postal\"}]}}}";
			XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
					new File(
							"C:\\Users\\lalit goyal\\workspace\\xmlToJsonConverter\\src\\test\\resources\\XMLListStructure.xml"));
			Node rootNode = nodeObject.convertToNodeObject();
			Assert.assertEquals(expecedJson, rootNode.getJsonStructure()
					.toString());
		} catch (Exception ex) {

		}
	}
}
