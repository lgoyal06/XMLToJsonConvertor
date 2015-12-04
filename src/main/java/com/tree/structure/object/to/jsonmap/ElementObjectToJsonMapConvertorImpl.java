package com.tree.structure.object.to.jsonmap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import nu.xom.Element;
import nu.xom.Elements;

public class ElementObjectToJsonMapConvertorImpl implements
		TreeStructureLinkedObjectToJsonMapConvertor<Element> {

	@Override
	public LinkedHashMap<String, Object> getMapStructure(Element element)
			throws Exception {
		Object finalObj = buildMap(element);
		if (finalObj instanceof LinkedHashMap<?, ?>) {
			LinkedHashMap<String, Object> resultantMap = new LinkedHashMap<String, Object>();
			resultantMap.put(element.getLocalName(), finalObj);
			return resultantMap;
		} else if (finalObj instanceof String) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put(element.getLocalName(), setValue(element));
			return map;
		} else {
			throw new Exception(
					"XML Structure is either invalid or not supported by this Utility.");
		}
	}

	@SuppressWarnings("unchecked")
	private Object buildMap(Element element) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		Elements childNodeList = element.getChildElements();
		int childCount = childNodeList.size();
		if (childCount > 0) {
			if ((childCount == 1
					&& childNodeList.get(0).getAttribute("dataType") != null && ("Array"
						.equalsIgnoreCase(childNodeList.get(0)
								.getAttribute("dataType").getValue())))
					|| (childCount > 1 && childNodeList.get(0).getLocalName()
							.equals(childNodeList.get(1).getLocalName()))) {
				List<Object> list = new ArrayList<Object>();
				for (int count = 0; count < childCount; ++count) {
					list.add(buildMap(childNodeList.get(count)));
				}
				LinkedHashMap<String, Object> mapContainingListElements = new LinkedHashMap<String, Object>();
				mapContainingListElements.put(childNodeList.get(0)
						.getLocalName(), list);
				return mapContainingListElements;
			}
			for (int count = 0; count < childCount; ++count) {
				Element node = childNodeList.get(count);
				if (node.getAttributeCount() > 0
						&& (node.getAttributeCount() == 1 && !node
								.getAttribute(0).getLocalName()
								.equalsIgnoreCase("dataType"))) {
					LinkedHashMap<String, Object> mapWithAttributeFields = new LinkedHashMap<String, Object>();
					if (node.getChildElements().size() == 0) {
						mapWithAttributeFields.put("value", buildMap(node));
					} else {
						mapWithAttributeFields
								.putAll((Map<? extends String, ? extends Object>) buildMap(node));
					}
					for (int i = 0; i < node.getAttributeCount(); ++i) {
						if (!node.getAttribute(i).getLocalName()
								.equalsIgnoreCase("dataType")) {
							mapWithAttributeFields.put(node.getAttribute(i)
									.getLocalName(), node.getAttribute(i)
									.getValue());
						}
					}
					map.put(node.getLocalName(), mapWithAttributeFields);
				} else {
					map.put(node.getLocalName(), buildMap(node));
				}
			}
			return map;
		} else {
			return setValue(element);
		}
	}

	private Object setValue(Element element) {
		if ("Integer".equalsIgnoreCase(element.getAttributeValue("dataType"))) {
			if (element.getValue() != null
					&& element.getValue().trim().equalsIgnoreCase("")) {
				return null;
			}
			return element.getValue() != null ? Integer.valueOf(element
					.getValue()) : "";
		}

		if ("Boolean".equalsIgnoreCase(element.getAttributeValue("dataType"))) {
			return element.getValue() != null ? Boolean.valueOf(element
					.getValue()) : "";
		}

		return element.getValue() != null ? element.getValue().replaceAll(
				"(\\r|\\n|\\t)+", "") : "";
	}
}
