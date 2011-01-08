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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import com.mebigfatguy.junitflood.Configuration;
import com.mebigfatguy.junitflood.classpath.ClassLookup;
import com.mebigfatguy.junitflood.evaluator.Evaluator;
import com.mebigfatguy.junitflood.expectations.Expectation;
import com.mebigfatguy.junitflood.expectations.NullnessExpectation;
import com.mebigfatguy.junitflood.generator.Statement;
import com.mebigfatguy.junitflood.generator.StatementList;
import com.mebigfatguy.junitflood.jvm.OperandStack;
import com.mebigfatguy.junitflood.util.SignatureUtils;

public class SimpleMethodVisitor implements MethodVisitor {

	private final Configuration configuration;
	private final String className;
	private final String methodName;
	private final String methodDesc;
	private final List<String> methodBodies;
	private final Set<String> ctors;
	private final Map<String, Set<Expectation>> expectations;
	private final OperandStack opStack;
	private final StringWriter stringWriter;
	private final PrintWriter writer;

	public SimpleMethodVisitor(Configuration config, String clsName, String mName, String desc, boolean isStatic, List<String> bodies) {
		configuration = config;
		className = clsName;
		methodName = mName;
		methodDesc = desc;
		methodBodies = bodies;
		stringWriter = new StringWriter();
		writer = new PrintWriter(stringWriter);
		ClassLookup lookup = config.getRepository();
		ctors = lookup.getConstructors(clsName, clsName);
		expectations = new HashMap<String, Set<Expectation>>();
		opStack = new OperandStack();
		SortedMap<Integer, String> parmSigs = SignatureUtils.getParameterRegisters(isStatic, desc);
		for (Map.Entry<Integer, String> entry : parmSigs.entrySet()) {
			opStack.addParameter(entry.getKey(), entry.getValue());
		}
		if (!isStatic) {
			opStack.addParameter(Integer.valueOf(0), desc);
		}

		buildInitialParameterExpectations(desc, isStatic);
	}

	@Override
	public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
		return null;
	}

	@Override
	public AnnotationVisitor visitAnnotationDefault() {
		return null;
	}

	@Override
	public void visitAttribute(Attribute attr) {
	}

	@Override
	public void visitCode() {
	}

	@Override
	public void visitEnd() {
		Evaluator evaluator = new Evaluator(configuration);
		StatementList statementList = evaluator.attemptExecution(className, methodName, methodDesc);

		if (statementList != null) {
			writer.println("\tpublic void test" + upperFirst(methodName) + "() throws Exception {");

			for (Statement statement : statementList) {
				writer.println("\t\t" + statement.toString());
			}

			writer.println("\t}");
			writer.flush();
			methodBodies.add(stringWriter.toString());
		}
	}

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		opStack.performFieldInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		opStack.performIincInsn(var, increment);
	}

	@Override
	public void visitInsn(int opcode) {
		opStack.performInsn(opcode);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		opStack.performIntInsn(opcode, operand);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		opStack.performJumpInsn(opcode, label);
	}

	@Override
	public void visitLabel(Label label) {
		opStack.performLabel(label);
	}

	@Override
	public void visitLdcInsn(Object cst) {
		opStack.performLdcInsn(cst);
	}

	@Override
	public void visitLineNumber(int line, Label start1) {
	}

	@Override
	public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		opStack.performLookupSwitchInsn(dflt, keys, labels);
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		opStack.performMethodInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitMultiANewArrayInsn(String desc, int dims) {
		opStack.performMultiANewArrayInsn(desc, dims);
	}

	@Override
	public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
		return null;
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
		opStack.performTableSwitchInsn(min, max, dflt, labels);
	}

	@Override
	public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
		opStack.performTryCatchBlock(start, end, handler, type);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		opStack.performTypeInsn(opcode, type);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		opStack.performVarInsn(opcode, var);
	}

	private String upperFirst(String name) {
		return Character.toUpperCase(name.charAt(0)) + name.substring(1);
	}

	private void buildInitialParameterExpectations(String desc, boolean isStatic) {
		if (!desc.startsWith("()")) {
			int parmNum = isStatic ? 0 : 1;
			String[] parmSigs = SignatureUtils.splitMethodParameterSignatures(desc);
			for (String parmSig : parmSigs) {
				Set<Expectation> parmExpectations = new HashSet<Expectation>();
				parmExpectations.add(new NullnessExpectation());
				expectations.put(String.valueOf(parmNum), parmExpectations);
				parmNum += (("D".equals(parmSig) || "J".equals(parmSig))) ? 2 : 1;
			}
		}
	}
}
