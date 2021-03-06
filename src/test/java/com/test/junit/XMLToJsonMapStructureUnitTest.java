package com.test.junit;

import java.io.File;

import junit.framework.Assert;
import nu.xom.Builder;
import nu.xom.Document;

import org.junit.Test;

import com.tree.structure.object.to.jsonmap.ElementObjectToJsonMapConvertorImpl;
import com.tree.structure.object.to.jsonmap.ElementObjectToJsonMapConvertorPreProcessor;
import com.tree.structure.object.to.jsonmap.NodeObjectToJsonMapConvertorImpl;
import com.xml.to.tree.structure.object.builder.Node;
import com.xml.to.tree.structure.object.builder.XMLToNodeObjectConvertorImpl;

public class XMLToJsonMapStructureUnitTest {

	@Test
	public void testGivenXMLWhenConversionUtilityRunUsingElementObjectExpectCorrectMapStructure() {
		try {
			Document doc = new Builder()
					.build(new File(
							"C:\\Users\\lalit goyal\\workspace\\xmlToJsonConvertorUtils\\src\\test\\resources\\SampleInputXML.xml"));
			String expectedResult = "{Client={Id=2357265, YearEstablished=, Contacts={Contact=[{Adresses={Address=[{AddressInfo={Address1=D-1/126A, State={value=VICTORIA, StateCode=VIC}}, Type={value=Postal, Code=null}}, {AddressInfo={Address1=D-1/126A, State={value=VICTORIA, StateCode=VIC}}, Type={value=Postal, Code=null}}]}}]}, d=sdsdsd, InsuredNames={InsuredName=[{id=sdsdd, Selected=false}, {Selected=false}]}, ServiceTeam={Member=[{PersonId=1212, Status={value=A, Code=null}}, {PersonId=1212, Status={value=A, Code=null}}]}, Emails={Email=[{EmailTypes=dssds}]}}}";
			String actualJson = new ElementObjectToJsonMapConvertorImpl()
					.getMapStructure(
							new ElementObjectToJsonMapConvertorPreProcessor()
									.executePreProcessor(doc)).toString();
			Assert.assertEquals(expectedResult, actualJson);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGivenXMLWhenConversionUtilityRunUisnfNodeObjectExpectCorrectMapStructure() {
		try {

			String expecedJson = "{xml={Adresses={Address=[{AddressInfo={Address1=D-1/126A, State={StateCode=VIC, Value=VICTORIA}}, Type=Postal}, {AddressInfo={Address1=D-1/126A, State={StateCode=VIC, Value=VICTORIA}}, Type=Postal}]}}}";
			XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
					new File(
							"C:\\Users\\lalit goyal\\workspace\\xmlToJsonConvertorUtils\\src\\test\\resources\\XMLListStructureAsString.xml"));
			Node rootNode = nodeObject.convertToNodeObject();
			Assert.assertEquals(
					expecedJson,
					new NodeObjectToJsonMapConvertorImpl().getMapStructure(
							rootNode).toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
