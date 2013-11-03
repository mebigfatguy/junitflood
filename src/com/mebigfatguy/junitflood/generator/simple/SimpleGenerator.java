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
package com.mebigfatguy.junitflood.generator.simple;

import java.io.IOException;
import java.io.InputStream;

import org.objectweb.asm.ClassReader;

import com.mebigfatguy.junitflood.Configuration;
import com.mebigfatguy.junitflood.classpath.ClassPathItem;
import com.mebigfatguy.junitflood.classpath.ClassPathIterator;
import com.mebigfatguy.junitflood.generator.GeneratorException;
import com.mebigfatguy.junitflood.generator.JUnitGenerator;
import com.mebigfatguy.junitflood.util.Closer;

public class SimpleGenerator implements JUnitGenerator {

	Configuration configuration;

	@Override
	public void setConfiguration(Configuration conf) {
		configuration = conf;
	}

	@Override
	public void generate() throws GeneratorException {
		try {
			ClassPathIterator iterator = new ClassPathIterator(configuration.getScanClassPath());
			while (iterator.hasNext()) {
				ClassPathItem item = iterator.next();
				try (InputStream is = item.getInputStream()) {
					ClassReader cr = new ClassReader(is);
					SimpleClassVisitor scv = new SimpleClassVisitor(configuration);
					cr.accept(scv, ClassReader.SKIP_DEBUG);
				}
			}
		} catch (IOException ioe) {
			throw new GeneratorException("Failed generating unit tests", ioe);
		}
	}
}
