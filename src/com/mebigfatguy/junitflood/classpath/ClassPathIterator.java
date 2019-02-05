/*
 * junitflood - An automatic junit test generator
 * Copyright 2011-2019 MeBigFatGuy.com
 * Copyright 2011-2019 Dave Brosius
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

import java.io.File;
import java.util.Iterator;
import java.util.Set;

public class ClassPathIterator implements Iterator<ClassPathItem> {

	private final Iterator<File> pathIterator;
	private File currentFile;
	private Iterator<ClassPathItem> subIterator;

	public ClassPathIterator(Set<File> cp) {
		pathIterator = cp.iterator();
		currentFile = null;
		subIterator = null;
	}

	@Override
	public boolean hasNext() {
		while (currentFile == null) {
			if (subIterator == null) {
				if (!pathIterator.hasNext()) {
					return false;
				}

				currentFile = pathIterator.next();

				if (currentFile.isDirectory()) {
					subIterator = new DirectoryIterator(currentFile);
				} else {
					subIterator = new JarIterator(currentFile);
				}
			}

			currentFile = null;

			if (subIterator.hasNext()) {
				return true;
			}

			subIterator = null;

		}

		return false;
	}

	@Override
	public ClassPathItem next() {
		return subIterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove not supported");
	}
}
