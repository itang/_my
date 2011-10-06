package com.itang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.wltea.analyzer.IKSegmentation;
import org.wltea.analyzer.Lexeme;

interface Filter {
	public boolean select(String word);
}

public class Words {
	private final Filter filter;

	public Words(Filter filter) {
		this.filter = filter;
	}

	public Words() {
		this(new Filter() {
			@Override
			public boolean select(String word) {
				return word.length() >= 2 && word.length() <= 3;
			}
		});
	}

	public List<Item> whats() {
		List<Item> result = new ArrayList<Item>();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					Words.class.getResourceAsStream("/data"), "UTF-8"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] sentences = line.trim().replaceAll("。", "")
						.replaceAll("，", "").split("\\s");
				for (String sentence : sentences) {
					for (String word : this.getWords(sentence)) {
						result.add(new Item(sentence, word, Pingyin4j
								.hanziToPinyin(word).replaceAll(" ", "").replaceAll(":", "")));
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List<String> getWords(String sentence) {
		List<String> result = new ArrayList<String>();
		IKSegmentation segment = new IKSegmentation(new StringReader(sentence));
		try {
			for (Lexeme lexeme = segment.next(); lexeme != null; lexeme = segment
					.next()) {
				String word = lexeme.getLexemeText();
				if (filter.select(word)) {
					result.add(word);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// private static String fill(String str, int max) {
	// if (str.length() < max) {
	// StringBuilder sb = new StringBuilder(str);
	// sb.append(repeatChar('一', max - str.length()));
	// return sb.toString();
	// } else {
	// return str.substring(0, max);
	// }
	// }
	//
	// private static String repeatChar(char c, int times) {
	// StringBuilder sb = new StringBuilder();
	// for (int i = 0; i < times; i++)
	// sb.append(c);
	// return sb.toString();
	// }
}
