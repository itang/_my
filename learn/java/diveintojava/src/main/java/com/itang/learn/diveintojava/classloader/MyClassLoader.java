package com.itang.learn.diveintojava.classloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyClassLoader extends ClassLoader {

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		if ("com.itang.learn.diveintojava.classloader.Hello".equals(name)) {
			String path = "target/classes/" + name.replaceAll("\\.", "/");
			try {
				BufferedInputStream in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				byte[] buffer = new byte[in.available()];
				in.read(buffer);
				in.close();
				return this.defineClass(name, buffer, 0, buffer.length);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return super.findClass(name);
	}

}
