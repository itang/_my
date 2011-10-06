package me.itang.test.test_restesay.tag;

public abstract class Node {
	public abstract String str();

	@Override
	public String toString() {
		return str();
	}
}
