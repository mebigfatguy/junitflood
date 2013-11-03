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
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mebigfatguy.junitflood.Configuration;


public class SimpleClassVisitor extends ClassVisitor {

	private static final  Logger logger = LoggerFactory.getLogger(SimpleClassVisitor.class);

	private final Configuration configuration;
	private String className = null;
	private File testFile = null;
	private List<String> methodBodies = null;

	public SimpleClassVisitor(Configuration config) {
	    super(Opcodes.ASM4);
		configuration = config;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		className = name;
		testFile = new File(configuration.getOutputDirectory(), name + "Test.java");
		if (!testFile.exists()) {
			methodBodies = new ArrayList<String>();
		} else {
			logger.warn("Class {} was skipped as it already has a unit test: {}", name, testFile);
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
		if (methodBodies.size() > 0) {
			testFile.getParentFile().mkdirs();
			try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(testFile)))) {
				int slashPos = className.lastIndexOf('/');
				if (slashPos >= 0) {
					String packageName = className.substring(0, slashPos).replaceAll("/", ".");

					writer.println("package " + packageName + ";");
				}

				writer.println();
				writer.println("import org.junit.Test;");
				writer.println();
				String simpleClassName;
				if (slashPos >= 0) {
					simpleClassName = className.substring(slashPos+1);
				} else {
					simpleClassName = className;
				}

				writer.println("public class " + simpleClassName + "Test {");
				writer.println();

				for (String methodBody : methodBodies) {
					writer.println("\t@Test");
					writer.println(methodBody);
					writer.println();
				}
				writer.println("}");
			} catch (IOException ioe) {
				logger.error("Failed opening file to create test file: {}", testFile, ioe);
			}
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
		if (testFile != null) {
			if ((access != Opcodes.ACC_PRIVATE) && (access != Opcodes.ACC_SYNTHETIC) && !"<clinit>".equals(name) && !"<init>".equals(name)) {
				return new SimpleMethodVisitor(configuration, className, name, desc, (access == Opcodes.ACC_STATIC), methodBodies);
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
