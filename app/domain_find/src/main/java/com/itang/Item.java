package com.itang;

public class Item {
	public String sentence;
	public String word;
	public String pinyin;

	public Item(String sentence, String word, String pinyin) {
		this.sentence = sentence;
		this.word = word;
		this.pinyin = pinyin;
	}

	public String toString() {
		return word + ":" + pinyin.replaceAll(" ", "") + " [" + sentence + "]";
	}
}