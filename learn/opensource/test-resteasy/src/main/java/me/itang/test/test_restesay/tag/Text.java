package me.itang.test.test_restesay.tag;

public class Text extends Node {
	private String content;

	public Text(String content) {
		this.content = content;
	}

	public String str() {
		return this.content;
	}
}