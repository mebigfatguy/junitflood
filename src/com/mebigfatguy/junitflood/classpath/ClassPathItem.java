/*
 * junitflood - An automatic junit test generator
 * Copyright 2011 MeBigFatGuy.com
 * Copyright 2011 Dave Brosius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package com.mebigfatguy.junitflood.classpath;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import com.mebigfatguy.junitflood.streams.LengthLimitedInputStream;

public class ClassPathItem {

	private final boolean isJar;
	private JarInputStream jarInputStream;
	private JarEntry jarEntry;
	private File directory;
	private File clsName;

	public ClassPathItem(JarInputStream jis, JarEntry entry) {
		jarInputStream = jis;
		jarEntry = entry;
		isJar = true;
	}

	public ClassPathItem(File dir, File cls) {
		directory = dir;
		clsName = cls;
		isJar = false;
	}

	public String getName() {
		if (isJar) {
			return jarEntry.getName();
		} else {
			return clsName.getPath().substring(directory.getPath().length());
		}
	}

	public InputStream getInputStream() throws IOException {
		if (isJar) {
			return new LengthLimitedInputStream(jarInputStream, jarEntry.getSize());
		} else {
			return new BufferedInputStream(new FileInputStream(clsName));
		}
	}
}
