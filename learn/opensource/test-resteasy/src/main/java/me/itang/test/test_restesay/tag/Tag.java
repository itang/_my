package me.itang.test.test_restesay.tag;

import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Tag extends Node {

	private String name;
	private String text;
	private Map<String, String> attributes = Maps.newHashMap();

	private List<Node> children = Lists.newArrayList();

	public Tag(String name) {
		this.name = name;
	}

	public Tag child(Node tag) {
		this.children.add(tag);
		return this;
	}

	public Tag child(String content) {
		this.children.add(new Text(content));
		return this;
	}

	public Tag name(String name) {
		this.name = name;
		return this;
	}

	public Tag text(String text) {
		this.text = text;
		return this;
	}

	public Tag attr(String name, String value) {
		attributes.put(name, value);
		return this;
	}

	@Override
	public String toString() {
		return str();
	}

	public String str() {
		StringBuilder sb = new StringBuilder();
		sb.append("<").append(name);

		final List<String> as = attributesString();
		if (!as.isEmpty()) {
			sb.append(" ").append(Joiner.on(" ").join(as).toString());
		}

		if (children.isEmpty() && text == null) {
			sb.append("/>");
		} else {
			sb.append(">");
			sb.append("\n");
			// text
			if (text != null && !text.isEmpty()) {
				sb.append(text);
			}
			// children
			sb.append(Joiner.on("\n").join(children));

			sb.append("\n");
			sb.append("</").append(name).append(">");
		}

		return sb.toString();
	}

	private List<String> attributesString() {
		List<String> result = Lists.newArrayList();
		for (Map.Entry<String, String> it : attributes.entrySet()) {
			result.add(it.getKey() + "=\"" + it.getValue() + "\"");
		}
		return result;
	}
}
