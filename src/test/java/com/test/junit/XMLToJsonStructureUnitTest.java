package com.test.junit;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;

import com.xml.object.builder.api.Node;
import com.xml.object.builder.api.XMLToNodeObjectConvertorImpl;

public class XMLToJsonStructureUnitTest {

	@Test
	public void testGivenXMLWithListStructureAsStringWhenUtilityRunExpectJsonWithListStructureAsResult() {
		try {
			String expecedJson = "{\"xml\":{\"Adresses\":{\"Address\":[{\"AddressInfo\":{\"State\":{\"Value\":\"VICTORIA\", \"StateCode\":\"VIC\"}, \"Address1\":\"D-1/126A\"}, \"Type\":\"Postal\"}, {\"AddressInfo\":{\"State\":{\"Value\":\"VICTORIA\", \"StateCode\":\"VIC\"}, \"Address1\":\"D-1/126A\"}, \"Type\":\"Postal\"}]}}}";
			XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
					new File(
							"C:\\Users\\lalit goyal\\workspace\\xmlToJsonConverter\\src\\test\\resources\\XMLListStructureAsString.xml"));
			Node rootNode = nodeObject.convertToNodeObject();
			Assert.assertEquals(expecedJson, rootNode.getJsonStructure()
					.toString());
		} catch (Exception ex) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testGivenXMLWithListStructureFormattedWhenUtilityRunExpectJsonWithListStructureAsResult() {
		try {
			String expecedJson = "{\"xml\":{\"Adresses\":{\"Address\":[{\"AddressInfo\":{\"State\":{\"Value\":\"VICTORIA\", \"StateCode\":\"VIC\"}, \"Address1\":\"D-1/126A\"}, \"Type\":\"Postal\"}, {\"AddressInfo\":{\"State\":{\"Value\":\"VICTORIA\", \"StateCode\":\"VIC\"}, \"Address1\":\"D-1/126A\"}, \"Type\":\"Postal\"}]}, \"Emails\":{\"Email\":[\"lgoyal06@gmail.com\", \"lalit.goyal@aon.com\"]}}}";
			XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
					new File(
							"C:\\Users\\lalit goyal\\workspace\\xmlToJsonConverter\\src\\test\\resources\\XMLListStructureFormatted.xml"));
			Node rootNode = nodeObject.convertToNodeObject();
			System.out.println(rootNode.getJsonStructure().toString());
			Assert.assertEquals(expecedJson, rootNode.getJsonStructure()
					.toString());
		} catch (Exception ex) {
			Assert.assertTrue(false);
		}
	}

	@Test
	public void testGivenXMLWithSelfClosingElementTagWithOutAttributesWhenUtilityRunExpectCorrectJsonAsResult() {
		try {
			String expecedJson = "{\"xml\":{\"c\":\"\", \"a\":\"Hello\", \"b\":\"\", \"m\":\"\"}}";
			XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
					new File(
							"C:\\Users\\lalit goyal\\workspace\\xmlToJsonConverter\\src\\test\\resources\\XMLListStructureInFormatWithSelfClosingTag.xml"));
			Node rootNode = nodeObject.convertToNodeObject();
			Assert.assertEquals(expecedJson, rootNode.getJsonStructure()
					.toString());

		} catch (Exception ex) {
			Assert.assertTrue(false);
		}
	}

}