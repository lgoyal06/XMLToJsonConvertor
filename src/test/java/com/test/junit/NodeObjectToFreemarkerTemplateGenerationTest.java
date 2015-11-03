package com.test.junit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Ignore;

import com.xml.object.builder.api.Node;
import com.xml.object.builder.api.XMLToNodeObjectConvertorImpl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class NodeObjectToFreemarkerTemplateGenerationTest {

	@Ignore
	public void test() {

		Configuration cfg = new Configuration();
		try {

			Template template = cfg.getTemplate("helloworld.ftl");

			// Build the data-model
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("message", "Hello World!");

			XMLToNodeObjectConvertorImpl nodeObject = new XMLToNodeObjectConvertorImpl(
					new File(
							"C:/Users/lalit goyal/workspace/datastructureLearning/src/main/java/com/lalit/xmlToJavaNodeObject/converter/SampleInputXML.xml"));

			Node rootNode = nodeObject.convertToNodeObject();
			HashMap<String, Object> hashMap1 = rootNode.getMapStructure();

			// Map<String, Object> address1 = new HashMap<>();
			// address1.put("Address1", "Address1valius");
			// address1.put("Address2", "Address2value");
			//
			// // leaf node always as map
			// Map<String, Object> address2 = new HashMap<>();
			// address2.put("Address1", "Address1_1valius");
			// address2.put("Address2", "Address2_2value");
			//
			// List<Object> addressList = new ArrayList<>();
			// addressList.add(address1);
			// addressList.add(address2);
			//
			// // if
			// List<Object> contacts = new ArrayList<>();
			// contacts.add("ContactValue");
			// contacts.add("ContactValue2");
			//
			// // root map can have two type of value - string or it is list
			//
			// Map<String, Object> map = new HashMap<>();
			// // leaf node as map
			// map.put("employeeName", "lalit");
			// map.put("addressList", addressList);
			// map.put("contacts", contacts);
			//
			data.put("rootNode", hashMap1);

			// Console output
			Writer out = new OutputStreamWriter(System.out);
			template.process(data, out);
			out.flush();

			// File output
			Writer file = new FileWriter(new File("F:\\FTL_helloworld.txt"));
			template.process(data, file);
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
