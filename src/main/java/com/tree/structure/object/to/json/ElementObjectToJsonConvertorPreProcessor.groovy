package com.tree.structure.object.to.json

import nu.xom.Attribute
import nu.xom.Document
import nu.xom.Element
import nu.xom.Nodes

/**
 * @author lalit goyal
 * 
 * Class to perform the Pre processing tasks on the Element object like setting 
 * 
 * 1.Datatype
 * 2.Adding up the Default attribute
 * 3.Adding up default child
 *
 */
class ElementObjectToJsonConvertorPreProcessor {

	private  Map<String,HashMap<String,String>> addDefaultChildMap=new TreeMap<String,HashMap<String,String>>();
	private  Map<String,HashMap<String,String>> ensureAttributeXpaths=new TreeMap<String,HashMap<String,String>>();
	private  Map<String,HashMap<String,String>> dataTypeXpaths=new TreeMap<String,HashMap<String,String>>();


	public Element executePreProcessor(Document doc){

		addDefaultChildMap.put("//Client/InsuredNames/InsuredName", ["tagName":"Selected", "tagValue":"false"])

		ensureAttributeXpaths.put("//Client/Contacts/Contact/Adresses/Address/Type", [ "attributeName":"Code", "attributeValue":"null"])
		ensureAttributeXpaths.put("//Client/ServiceTeam/Member/Status", [ "attributeName":"Code", "attributeValue":"null"])

		dataTypeXpaths.put("//Client/ServiceTeam/Member/PersonId",[ "attributeName":"dataType", "attributeValue":"Integer"])
		dataTypeXpaths.put("//Client/CRDId",[ "attributeName":"dataType", "attributeValue":"Integer"])

		dataTypeXpaths.put("//Client/InsuredNames/InsuredName/Selected",[ "attributeName":"dataType", "attributeValue":"Boolean"])

		dataTypeXpaths.put("//Client/Contacts/Contact",[ "attributeName":"dataType", "attributeValue":"Array"])
		dataTypeXpaths.put("//Client/Contacts/Contact/Adresses/Address",[ "attributeName":"dataType", "attributeValue":"Array"])
		dataTypeXpaths.put("//Client/Emails/Email",[ "attributeName":"dataType", "attributeValue":"Array"])
		dataTypeXpaths.put("//Client/Contacts/Contact",[ "attributeName":"dataType", "attributeValue":"Array"])

		addDefaultChildMap.each{ key, value ->
			Nodes childNodes = doc.query(key)
			appendChildToElement(childNodes, value)
		}

		ensureAttributeXpaths.each{ key, value ->
			Nodes childNodes = doc.query(key)
			addAttributeToElement(childNodes, value)
		}

		dataTypeXpaths.each{ key, value ->
			Nodes childNodes = doc.query(key)
			addAttributeToElement(childNodes, value)
		}

		return doc.getRootElement()
	}


	private static appendChildToElement(Nodes childNodes,HashMap<String,String> childTag) {
		for(int i=0; i<childNodes.size(); ++i){
			Element e = new Element(childTag.get("tagName"));
			e.appendChild(childTag.get("tagValue"))
			Element parentChild = childNodes.get(i)
			parentChild.appendChild(e);
		}
	}

	private static addAttributeToElement(Nodes childNodes,HashMap<String,String> map) {
		for(int i=0; i<childNodes.size(); ++i){
			Attribute attr = new Attribute(map.get("attributeName"), map.get("attributeValue"));
			Element parentChild = childNodes.get(i)
			parentChild.addAttribute(attr)
		}
	}
}
