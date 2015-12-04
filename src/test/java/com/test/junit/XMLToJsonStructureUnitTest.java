package com.test.junit;

import java.io.File;

import junit.framework.Assert;
import nu.xom.Builder;
import nu.xom.Document;

import org.junit.Test;

import com.xml.object.builder.api.LinkedTreeStructureObjectToJsonConvertorImpl;
import com.xml.object.builder.api.Node;
import com.xml.object.builder.api.XMLToMapConversionPreProcessor;
import com.xml.object.builder.api.XMLToNodeObjectConvertorImpl;

public class XMLToJsonStructureUnitTest {

	@Test
	public void testGivenXMLAsStringWhenUtilityRunViaLinkedNodeObjectExpectJsonWithListStructureAsResult() {
		try {
			String expecedJson = "{\"xml\":{\"Adresses\":{\"Address\":[{\"AddressInfo\":{\"Address1\":\"D-1/126A\",\"State\":{\"StateCode\":\"VIC\",\"Value\":\"VICTORIA\"}},\"Type\":\"Postal\"},{\"AddressInfo\":{\"Address1\":\"D-1/126A\",\"State\":{\"StateCode\":\"VIC\",\"Value\":\"VICTORIA\"}},\"Type\":\"Postal\"}]}}}";
			XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
					new File(
							"C:\\Users\\lalit goyal\\workspace\\xmlToJsonConverter\\src\\test\\resources\\XMLListStructureAsString.xml"));
			Node rootNode = nodeObject.convertToNodeObject();

			Assert.assertEquals(
					expecedJson.replaceAll("\"", "!").replaceAll("!", "\""),
					new LinkedTreeStructureObjectToJsonConvertorImpl()
							.getJsonStructure(rootNode));
		} catch (Exception ex) {
			Assert.assertTrue(false);
		}
	}
	
		@Test
	public void testGivenXMLAsStringWhenUtilityRunViaLinkedElementObjectExpectJsonWithListStructureAsResult() {
		try {
				Document doc = new Builder()
					.build(new File(
							"C:\\Users\\lalit goyal\\workspace\\xmlToJsonConverter\\src\\test\\resources\\SampleInputXML.xml"));
			String expectedResult = "{\"Client\":{\"CRDId\":2357265,\"YearEstablished\":\"\",\"Contacts\":{\"Contact\":[{\"Adresses\":{\"Address\":[{\"AddressInfo\":{\"Address1\":\"D-1/126A\",\"State\":{\"value\":\"VICTORIA\",\"StateCode\":\"VIC\"}},\"Type\":{\"value\":\"Postal\",\"Code\":\"null\"}},{\"AddressInfo\":{\"Address1\":\"D-1/126A\",\"State\":{\"value\":\"VICTORIA\",\"StateCode\":\"VIC\"}},\"Type\":{\"value\":\"Postal\",\"Code\":\"null\"}}]}}]},\"d\":\"sdsdsd\",\"InsuredNames\":{\"InsuredName\":[{\"id\":\"sdsdd\",\"Selected\":false},{\"Selected\":false}]},\"ServiceTeam\":{\"Member\":[{\"PersonId\":1212,\"Status\":{\"value\":\"A\",\"Code\":\"null\"}},{\"PersonId\":1212,\"Status\":{\"value\":\"A\",\"Code\":\"null\"}}]},\"Emails\":{\"Email\":[{\"EmailTypes\":\"dssds\"}]}}}";
			String actualJson = new LinkedTreeStructureObjectToJsonConvertorImpl()
					.getJsonStructure(new XMLToMapConversionPreProcessor()
							.executePreProcessor(doc));
			Assert.assertEquals(expectedResult.replaceAll("\"", "!")
					.replaceAll("!", "\""), actualJson);

		} catch (Exception ex) {
			Assert.assertTrue(false);
		}
	}

}
