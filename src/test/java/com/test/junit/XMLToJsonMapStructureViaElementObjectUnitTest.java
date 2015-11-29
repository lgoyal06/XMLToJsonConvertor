package com.test.junit;

import java.io.File;

import junit.framework.Assert;
import nu.xom.Builder;
import nu.xom.Document;

import org.junit.Test;

import com.xml.object.builder.api.Node;
import com.xml.object.builder.api.XMLToMapConversionPreProcessor;

public class XMLToJsonMapStructureViaElementObjectUnitTest {

	@Test
	public void testGivenXMLWhenConversionUtilityRunExpectCorrectMapStructure() {
		try {
			Node rootNode = new Node();
			Document doc = new Builder()
					.build(new File(
							"C:\\Users\\lalit goyal\\workspace\\xmlToJsonConverter\\src\\test\\resources\\SampleInputXML.xml"));
			String expectedResult = "{Client={CRDId=2357265, YearEstablished=, Contacts={Contact=[{Adresses={Address=[{AddressInfo={Address1=D-1/126A, State={value=VICTORIA, StateCode=VIC}}, Type={value=Postal, Code=null}}, {AddressInfo={Address1=D-1/126A, State={value=VICTORIA, StateCode=VIC}}, Type={value=Postal, Code=null}}]}}]}, d=sdsdsd, InsuredNames={InsuredName=[{id=sdsdd, Selected=false}, {Selected=false}]}, ServiceTeam={Member=[{PersonId=1212, Status={value=A, Code=null}}, {PersonId=1212, Status={value=A, Code=null}}]}, Emails={Email=[{EmailTypes=dssds}]}}}";

			Assert.assertEquals(
					expectedResult,
					rootNode.getMapStructure(
							new XMLToMapConversionPreProcessor()
									.executePreProcessor(doc)).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
