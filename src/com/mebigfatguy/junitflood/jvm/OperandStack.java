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
package com.mebigfatguy.junitflood.jvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;

public class OperandStack {

	private final List<Operand> stack = new ArrayList<Operand>();
	private final Map<Integer, Operand> registers = new HashMap<Integer, Operand>();
	private final Map<String, Operand> fields = new HashMap<String, Operand>();

	public OperandStack() {
	}

	public void performFieldInsn(int opcode, String owner, String name, String desc) {
		switch (opcode) {
			case Opcodes.GETSTATIC:
			case Opcodes.GETFIELD:
			break;

			case Opcodes.PUTSTATIC:
			case Opcodes.PUTFIELD:
				if (stack.size() > 0) {
					Operand val = stack.remove(stack.size() - 1);
					fields.put(owner + ":" + name, val);
				}
			break;
		}
	}

	public void performIincInsn(int var, int increment) {
		//nop -- document +x ?
	}

	public void performInsn(int opcode) {
	}

	public void performIntInsn(int opcode, int operand) {
		switch (opcode) {
			case Opcodes.BIPUSH:
			break;

			case Opcodes.SIPUSH:
			break;

			case Opcodes.NEWARRAY:
			break;
		}
	}

	public void performJumpInsn(int opcode, Label label) {
		switch (opcode) {
			case Opcodes.IFEQ:
			break;

			case Opcodes.IFNE:
			break;

			case Opcodes.IFLT:
			break;

			case Opcodes.IFGE:
			break;

			case Opcodes.IFGT:
			break;

			case Opcodes.IFLE:
			break;

			case Opcodes.IF_ICMPEQ:
			break;

			case Opcodes.IF_ICMPNE:
			break;

			case Opcodes.IF_ICMPLT:
			break;

			case Opcodes.IF_ICMPGE:
			break;

			case Opcodes.IF_ICMPGT:
			break;

			case Opcodes.IF_ICMPLE:
			break;

			case Opcodes.IF_ACMPEQ:
			break;

			case Opcodes.IF_ACMPNE:
			break;

			case Opcodes.GOTO:
			break;

			case Opcodes.JSR:
			break;

			case Opcodes.IFNULL:
			break;

			case Opcodes.IFNONNULL:
			break;
		}
	}

	public void performLcdInsn(Object cst) {
	}

	public void performLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
	}

	public void performMethodInsn(int opcode, String owner, String name, String desc) {
		switch (opcode) {
			case Opcodes.INVOKEVIRTUAL:
			break;

			case Opcodes.INVOKESPECIAL:
			break;

			case Opcodes.INVOKESTATIC:
			break;

			case Opcodes.INVOKEINTERFACE:
			break;

			case Opcodes.INVOKEDYNAMIC:
			break;
		}
	}

	public void performMultiANewArrayInsn(String desc, int dims) {
	}

	public void performTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
	}

	public void performTypeInsn(int opcode, String type) {
		switch (opcode) {
			case Opcodes.NEW:
			break;

			case Opcodes.ANEWARRAY:
			break;

			case Opcodes.CHECKCAST:
			break;

			case Opcodes.INSTANCEOF:
			break;
		}
	}

	public void performVarInsn(int opcode, int var) {
		switch (opcode) {
			case Opcodes.ILOAD:
			break;

			case Opcodes.LLOAD:
			break;

			case Opcodes.FLOAD:
			break;

			case Opcodes.DLOAD:
			break;

			case Opcodes.ALOAD:
			break;

			case Opcodes.ISTORE:
			break;

			case Opcodes.LSTORE:
			break;

			case Opcodes.FSTORE:
			break;

			case Opcodes.DSTORE:
			break;

			case Opcodes.ASTORE:
			break;

			case Opcodes.RET:
			break;
		}
	}
}
