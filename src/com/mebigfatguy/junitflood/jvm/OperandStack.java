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
				if (!stack.isEmpty()) {
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

			case Opcodes.ACONST_NULL: {
				Operand op = new Operand();
				op.setNull(true);
				stack.add(op);
			}
			break;

			case Opcodes.ICONST_M1: {
				Operand op = new Operand();
				op.setConstant(Integer.valueOf(-1));
				stack.add(op);
			}
			break;

			case Opcodes.ICONST_0: {
				Operand op = new Operand();
				op.setConstant(Integer.valueOf(0));
				stack.add(op);
			}
			break;

			case Opcodes.ICONST_1: {
				Operand op = new Operand();
				op.setConstant(Integer.valueOf(1));
				stack.add(op);
			}
			break;

			case Opcodes.ICONST_2: {
				Operand op = new Operand();
				op.setConstant(Integer.valueOf(2));
				stack.add(op);
			}
			break;

			case Opcodes.ICONST_3: {
				Operand op = new Operand();
				op.setConstant(Integer.valueOf(3));
				stack.add(op);
			}
			break;

			case Opcodes.ICONST_4: {
				Operand op = new Operand();
				op.setConstant(Integer.valueOf(4));
				stack.add(op);
			}
			break;

			case Opcodes.ICONST_5: {
				Operand op = new Operand();
				op.setConstant(Integer.valueOf(5));
				stack.add(op);
			}
			break;

			case Opcodes.LCONST_0: {
				Operand op = new Operand();
				op.setConstant(Long.valueOf(0));
				stack.add(op);
			}
			break;

			case Opcodes.LCONST_1: {
				Operand op = new Operand();
				op.setConstant(Long.valueOf(1));
				stack.add(op);
			}
			break;

			case Opcodes.FCONST_0: {
				Operand op = new Operand();
				op.setConstant(Float.valueOf(0));
				stack.add(op);
			}
			break;

			case Opcodes.FCONST_1: {
				Operand op = new Operand();
				op.setConstant(Float.valueOf(1));
				stack.add(op);
			}
			break;

			case Opcodes.FCONST_2: {
				Operand op = new Operand();
				op.setConstant(Float.valueOf(2));
				stack.add(op);
			}
			break;

			case Opcodes.DCONST_0: {
				Operand op = new Operand();
				op.setConstant(Double.valueOf(0));
				stack.add(op);
			}
			break;

			case Opcodes.DCONST_1: {
				Operand op = new Operand();
				op.setConstant(Double.valueOf(1));
				stack.add(op);
			}
			break;

			case Opcodes.IALOAD: {
				pop2();
				Operand op = new Operand();
				op.setStaticSignature("I");
				stack.add(op);
			}
			break;

			case Opcodes.LALOAD: {
				pop2();
				Operand op = new Operand();
				op.setStaticSignature("J");
				stack.add(op);
			}
			break;

			case Opcodes.FALOAD: {
				pop2();
				Operand op = new Operand();
				op.setStaticSignature("F");
				stack.add(op);
			}
			break;

			case Opcodes.DALOAD: {
				pop2();
				Operand op = new Operand();
				op.setStaticSignature("D");
				stack.add(op);
			}
			break;

			case Opcodes.AALOAD: {
				pop2();
				Operand op = new Operand();
				op.setStaticSignature("Ljava/lang/Object;");
				stack.add(op);
			}
			break;

			case Opcodes.BALOAD: {
				pop2();
				Operand op = new Operand();
				op.setStaticSignature("B");
				stack.add(op);
			}
			break;

			case Opcodes.CALOAD: {
				pop2();
				Operand op = new Operand();
				op.setStaticSignature("C");
				stack.add(op);
			}
			break;

			case Opcodes.SALOAD:
			 {
					pop2();
					Operand op = new Operand();
					op.setStaticSignature("S");
					stack.add(op);
				}
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
				pop();
			break;

			case Opcodes.POP2:
				pop2();
			break;

			case Opcodes.DUP:
				if (!stack.isEmpty()) {
					Operand operand = stack.get(stack.size() - 1);
					stack.add(operand);
				}
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
				if (stack.size() >= 2) {
					Operand op = stack.remove(stack.size() - 1);
					stack.add(stack.size() - 1, op);
				}
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

			case Opcodes.I2L: {
				Operand lop = new Operand();
				lop.setStaticSignature("J");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						lop.setConstant(Long.valueOf((Integer) o));
					}
				}
				stack.add(lop);
			}
			break;

			case Opcodes.I2F: {
				Operand fop = new Operand();
				fop.setStaticSignature("F");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						fop.setConstant(Float.valueOf((Integer) o));
					}
				}
				stack.add(fop);
			}
			break;

			case Opcodes.I2D: {
				Operand dop = new Operand();
				dop.setStaticSignature("D");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						dop.setConstant(Double.valueOf((Integer) o));
					}
				}
				stack.add(dop);
			}
			break;

			case Opcodes.L2I: {
				Operand iop = new Operand();
				iop.setStaticSignature("I");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						iop.setConstant(((Long) o).intValue());
					}
				}
				stack.add(iop);
			}
			break;

			case Opcodes.L2F: {
				Operand fop = new Operand();
				fop.setStaticSignature("F");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						fop.setConstant(((Long) o).floatValue());
					}
				}
				stack.add(fop);
			}
			break;

			case Opcodes.L2D: {
				Operand dop = new Operand();
				dop.setStaticSignature("D");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						dop.setConstant(((Long) o).doubleValue());
					}
				}
				stack.add(dop);
			}
			break;

			case Opcodes.F2I: {
				Operand iop = new Operand();
				iop.setStaticSignature("I");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						iop.setConstant(((Float) o).intValue());
					}
				}
				stack.add(iop);
			}
			break;

			case Opcodes.F2L: {
				Operand lop = new Operand();
				lop.setStaticSignature("J");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						lop.setConstant(((Float) o).longValue());
					}
				}
				stack.add(lop);
			}
			break;

			case Opcodes.F2D: {
				Operand dop = new Operand();
				dop.setStaticSignature("D");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						dop.setConstant(((Float) o).doubleValue());
					}
				}
				stack.add(dop);
			}
			break;

			case Opcodes.D2I: {
				Operand iop = new Operand();
				iop.setStaticSignature("I");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						iop.setConstant(((Double) o).intValue());
					}
				}
				stack.add(iop);
			}
			break;

			case Opcodes.D2L: {
				Operand lop = new Operand();
				lop.setStaticSignature("J");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						lop.setConstant(((Double) o).longValue());
					}
				}
				stack.add(lop);
			}
			break;

			case Opcodes.D2F: {
				Operand fop = new Operand();
				fop.setStaticSignature("F");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						fop.setConstant(((Double) o).floatValue());
					}
				}
				stack.add(fop);
			}
			break;

			case Opcodes.I2B: {
				Operand bop = new Operand();
				bop.setStaticSignature("B");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						bop.setConstant(((Integer) o).byteValue());
					}
				}
				stack.add(bop);
			}
			break;

			case Opcodes.I2C: {
				Operand cop = new Operand();
				cop.setStaticSignature("C");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						cop.setConstant(Character.valueOf((char)((Integer) o).intValue()));
					}
				}
				stack.add(cop);
			}
			break;

			case Opcodes.I2S: {
				Operand sop = new Operand();
				sop.setStaticSignature("S");
				if (!stack.isEmpty()) {
					Operand op = stack.remove(stack.size() - 1);
					Object o = op.getConstant();
					if (o != null) {
						sop.setConstant(Short.valueOf((short)((Integer) o).intValue()));
					}
				}
				stack.add(sop);
			}
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
				pop();
			break;

			case Opcodes.LRETURN:
				pop();
			break;

			case Opcodes.FRETURN:
				pop();
			break;

			case Opcodes.DRETURN:
				pop();
			break;

			case Opcodes.ARETURN:
				pop();
			break;

			case Opcodes.RETURN:
			break;

			case Opcodes.ARRAYLENGTH: {
				pop();
				Operand op = new Operand();
				op.setStaticSignature("I");
				stack.add(op);
			}
			break;

			case Opcodes.ATHROW:
				pop();
			break;

			case Opcodes.MONITORENTER:
				pop();
			break;

			case Opcodes.MONITOREXIT:
				pop();
			break;
		}
	}

	public void performIntInsn(int opcode, int operand) {
		switch (opcode) {
			case Opcodes.BIPUSH: {
				Operand op = new Operand();
				op.setStaticSignature("B");
				op.setConstant(operand);
				stack.add(op);
			}
			break;

			case Opcodes.SIPUSH: {
				Operand op = new Operand();
				op.setStaticSignature("S");
				op.setConstant(operand);
				stack.add(op);
			}
			break;

			case Opcodes.NEWARRAY: {
				Operand op = new Operand();
				op.setStaticSignature("[Ljava/lang/Object;");
				stack.add(op);
			}
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

			case Opcodes.IFNULL: {
				pop();
				Operand op = new Operand();
				op.setStaticSignature("Z");
				stack.add(op);
			}
			break;

			case Opcodes.IFNONNULL: {
				pop();
				Operand op = new Operand();
				op.setStaticSignature("Z");
				stack.add(op);
			}
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

	private void pop() {
		if (!stack.isEmpty()) {
			stack.remove(stack.size() - 1);
		}
	}
	private void pop2() {
		pop();
		pop();
	}
}
