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
		switch (opcode) {
			case Opcodes.NOP:
			break;

			case Opcodes.ACONST_NULL:
			break;

			case Opcodes.ICONST_M1:
			break;

			case Opcodes.ICONST_0:
			break;

			case Opcodes.ICONST_1:
			break;

			case Opcodes.ICONST_2:
			break;

			case Opcodes.ICONST_3:
			break;

			case Opcodes.ICONST_4:
			break;

			case Opcodes.ICONST_5:
			break;

			case Opcodes.LCONST_0:
			break;

			case Opcodes.LCONST_1:
			break;

			case Opcodes.FCONST_0:
			break;

			case Opcodes.FCONST_1:
			break;

			case Opcodes.FCONST_2:
			break;

			case Opcodes.DCONST_0:
			break;

			case Opcodes.DCONST_1:
			break;

			case Opcodes.IALOAD:
			break;

			case Opcodes.LALOAD:
			break;

			case Opcodes.FALOAD:
			break;

			case Opcodes.DALOAD:
			break;

			case Opcodes.AALOAD:
			break;

			case Opcodes.BALOAD:
			break;

			case Opcodes.CALOAD:
			break;

			case Opcodes.SALOAD:
			break;

			case Opcodes.IASTORE:
			break;

			case Opcodes.LASTORE:
			break;

			case Opcodes.FASTORE:
			break;

			case Opcodes.DASTORE:
			break;

			case Opcodes.AASTORE:
			break;

			case Opcodes.BASTORE:
			break;

			case Opcodes.CASTORE:
			break;

			case Opcodes.SASTORE:
			break;

			case Opcodes.POP:
			break;

			case Opcodes.POP2:
			break;

			case Opcodes.DUP:
			break;

			case Opcodes.DUP_X1:
			break;

			case Opcodes.DUP_X2:
			break;

			case Opcodes.DUP2:
			break;

			case Opcodes.DUP2_X1:
			break;

			case Opcodes.DUP2_X2:
			break;

			case Opcodes.SWAP:
			break;

			case Opcodes.IADD:
			break;

			case Opcodes.LADD:
			break;

			case Opcodes.FADD:
			break;

			case Opcodes.DADD:
			break;

			case Opcodes.ISUB:
			break;

			case Opcodes.LSUB:
			break;

			case Opcodes.FSUB:
			break;

			case Opcodes.DSUB:
			break;

			case Opcodes.IMUL:
			break;

			case Opcodes.LMUL:
			break;

			case Opcodes.FMUL:
			break;

			case Opcodes.DMUL:
			break;

			case Opcodes.IDIV:
			break;

			case Opcodes.LDIV:
			break;

			case Opcodes.FDIV:
			break;

			case Opcodes.DDIV:
			break;

			case Opcodes.IREM:
			break;

			case Opcodes.LREM:
			break;

			case Opcodes.FREM:
			break;

			case Opcodes.DREM:
			break;

			case Opcodes.INEG:
			break;

			case Opcodes.LNEG:
			break;

			case Opcodes.FNEG:
			break;

			case Opcodes.DNEG:
			break;

			case Opcodes.ISHL:
			break;

			case Opcodes.LSHL:
			break;

			case Opcodes.ISHR:
			break;

			case Opcodes.LSHR:
			break;

			case Opcodes.IUSHR:
			break;

			case Opcodes.LUSHR:
			break;

			case Opcodes.IAND:
			break;

			case Opcodes.LAND:
			break;

			case Opcodes.IOR:
			break;

			case Opcodes.LOR:
			break;

			case Opcodes.IXOR:
			break;

			case Opcodes.LXOR:
			break;

			case Opcodes.I2L:
			break;

			case Opcodes.I2F:
			break;

			case Opcodes.I2D:
			break;

			case Opcodes.L2I:
			break;

			case Opcodes.L2F:
			break;

			case Opcodes.L2D:
			break;

			case Opcodes.F2I:
			break;

			case Opcodes.F2L:
			break;

			case Opcodes.F2D:
			break;

			case Opcodes.D2I:
			break;

			case Opcodes.D2L:
			break;

			case Opcodes.D2F:
			break;

			case Opcodes.I2B:
			break;

			case Opcodes.I2C:
			break;

			case Opcodes.I2S:
			break;

			case Opcodes.LCMP:
			break;

			case Opcodes.FCMPL:
			break;

			case Opcodes.FCMPG:
			break;

			case Opcodes.DCMPL:
			break;

			case Opcodes.DCMPG:
			break;

			case Opcodes.IRETURN:
			break;

			case Opcodes.LRETURN:
			break;

			case Opcodes.FRETURN:
			break;

			case Opcodes.DRETURN:
			break;

			case Opcodes.ARETURN:
			break;

			case Opcodes.RETURN:
			break;

			case Opcodes.ARRAYLENGTH:
			break;

			case Opcodes.ATHROW:
			break;

			case Opcodes.MONITORENTER:
			break;

			case Opcodes.MONITOREXIT:
			break;
		}
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
