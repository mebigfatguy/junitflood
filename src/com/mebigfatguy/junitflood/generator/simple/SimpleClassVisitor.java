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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mebigfatguy.junitflood.Configuration;
import com.mebigfatguy.junitflood.util.Closer;


public class SimpleClassVisitor implements ClassVisitor {

	private static final  Logger logger = LoggerFactory.getLogger(SimpleClassVisitor.class);

	private final Configuration configuration;
	private PrintWriter writer;
	private List<String> methodBodies;
	private List<String> ctorSignatures;


	public SimpleClassVisitor(Configuration config) {
		configuration = config;
		writer = null;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		try {
			File testFile = new File(configuration.getOutputDirectory(), name + "Test.java");
			if (!testFile.exists()) {
				testFile.getParentFile().mkdirs();
				writer = new PrintWriter(new BufferedWriter(new FileWriter(testFile)));
				int slashPos = name.lastIndexOf("/");
				if (slashPos >= 0) {
					String packageName = name.substring(0, slashPos).replaceAll("/", ".");
					writer.println("package " + packageName + ";");
				}
				ctorSignatures = new ArrayList<String>();
				methodBodies = new ArrayList<String>();
			} else {
				logger.warn("Class " + name + " was skipped as it already has a unit test: " + testFile);
			}
		} catch (IOException ioe) {
			writer = null;
		}
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	@Override
	public void visitAttribute(Attribute attr) {
	}

	@Override
	public void visitEnd() {
		if (writer != null) {
			Closer.closeQuietly(writer);
		}
	}

	@Override
	public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
		return null;
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		if (writer != null) {
			if ("<init>".equals(name)) {
				ctorSignatures.add(desc);
			} else if (!"<clinit>".equals(name)) {
				return new SimpleMethodVisitor(configuration, methodBodies, ctorSignatures);
			}
		}

		return null;
	}

	@Override
	public void visitOuterClass(String owner, String name, String desc) {
	}

	@Override
	public void visitSource(String source, String debug) {
	}
}
