package com.tree.structure.object.to.jsonmap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.xml.to.tree.structure.object.builder.api.Node;

public class NodeObjectToJsonMapConvertorImpl implements
		TreeStructureLinkedObjectToJsonMapConvertor<Node> {

	@Override
	/**
	 * @return the Map Structure of the Node object This structure is really
	 * 
	 *         Easy to be traversed in Freemarker as freemarker takes the input
	 *         data in Map structure
	 * 
	 *         Easy to be converted into Json
	 * 
	 *         Attributes is now supported by the Map Structure for all nodes
	 *         except for list type nodes
	 * 
	 *         Assumption : Only Leaf Nodes can have attribute fields
	 * @throws Exception
	 */
	public LinkedHashMap<String, Object> getMapStructure(Node node)
			throws Exception {
		Node element = node;
		Object finalObj = buildMap(element);
		if (finalObj instanceof LinkedHashMap<?, ?>) {
			LinkedHashMap<String, Object> resultantMap = new LinkedHashMap<>();
			resultantMap.put(element.getTagName(), finalObj);
			return resultantMap;
		} else if (finalObj instanceof String) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put(element.getTagName(), element.getVal());
			return map;
		} else {
			throw new Exception(
					"XML Structure is either invalid or not supported by this Utility. For more info please read the Help Page");
		}
	}

	private Object buildMap(Node element) {
		if (element.getChild() != null) {
			List<Node> childNodeList = element.getChild();
			if (childNodeList.size() == 1) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				if (childNodeList.get(0).getAttribute().size() > 0) {
					LinkedHashMap<String, Object> map1 = new LinkedHashMap<>();
					map1.putAll(childNodeList.get(0).getAttribute());
					map1.put("Value", buildMap(childNodeList.get(0)));
					map.put(childNodeList.get(0).getTagName(), map1);
				} else {
					map.put(childNodeList.get(0).getTagName(),
							buildMap(childNodeList.get(0)));
				}
				return map;
			} else if (childNodeList.size() > 1) {
				if (childNodeList.get(0).getTagName()
						.equals(childNodeList.get(1).getTagName())) {
					List<Object> list = new ArrayList<>();
					for (Node node : childNodeList) {
						list.add(buildMap(node));
					}
					LinkedHashMap<String, Object> map1 = new LinkedHashMap<>();
					map1.put(childNodeList.get(0).getTagName(), list);
					return map1;
				} else {
					LinkedHashMap<String, Object> map = new LinkedHashMap<>();
					for (Node node : childNodeList) {
						if (node.getAttribute().size() > 0) {
							LinkedHashMap<String, Object> map1 = new LinkedHashMap<>();
							map1.putAll(node.getAttribute());
							map1.put("Value", buildMap(node));
							map.put(node.getTagName(), map1);
						} else
							map.put(node.getTagName(), buildMap(node));
					}
					return map;
				}
			}
		}
		return element.getVal() != null ? element.getVal().trim() : "";
	}

}
