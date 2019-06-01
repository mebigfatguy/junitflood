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
package com.mebigfatguy.junitflood.jvm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mebigfatguy.junitflood.util.SignatureUtils;

public class OperandStack {

    private static final Logger logger = LoggerFactory.getLogger(OperandStack.class);

    private final List<Operand> stack = new ArrayList<Operand>();
    private final Map<Integer, Operand> registers = new HashMap<Integer, Operand>();
    private final Map<String, Operand> fields = new HashMap<String, Operand>();
    private final Map<Label, String> catchHandlers = new HashMap<Label, String>();

    public OperandStack() {
    }

    public void addParameter(Integer reg, String signature) {
        Operand op = new Operand(reg.intValue(), signature);
        registers.put(reg, op);
    }

    public Operand getOperand(int stackPosition) {
        if (stack.size() > stackPosition) {
            return stack.get(stackPosition);
        } else {
            logger.error("Request for stack position: {} doesn't exist for stack: {}", stackPosition, stack);
            return new Operand();
        }
    }

    public void performFieldInsn(int opcode, String owner, String name, String desc) {
        switch (opcode) {
        case Opcodes.GETSTATIC:
        case Opcodes.GETFIELD: {
            Operand op = fields.get(owner + ":" + name);
            if (op == null) {
                op = new Operand(owner + ":" + name, desc);
            }
            push(op);
        }
            break;

        case Opcodes.PUTSTATIC:
        case Opcodes.PUTFIELD:
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                fields.put(owner + ":" + name, op);
            }
            break;
        }
    }

    public void performIincInsn(int variable, int increment) {
        // nop -- document +x ?
    }

    public void performInsn(int opcode) {
        switch (opcode) {
        case Opcodes.NOP:
            break;

        case Opcodes.ACONST_NULL: {
            Operand op = new Operand();
            op.setNull(true);
            push(op);
        }
            break;

        case Opcodes.ICONST_M1: {
            Operand op = new Operand();
            op.setConstant(Integer.valueOf(-1));
            push(op);
        }
            break;

        case Opcodes.ICONST_0: {
            Operand op = new Operand();
            op.setConstant(Integer.valueOf(0));
            push(op);
        }
            break;

        case Opcodes.ICONST_1: {
            Operand op = new Operand();
            op.setConstant(Integer.valueOf(1));
            push(op);
        }
            break;

        case Opcodes.ICONST_2: {
            Operand op = new Operand();
            op.setConstant(Integer.valueOf(2));
            push(op);
        }
            break;

        case Opcodes.ICONST_3: {
            Operand op = new Operand();
            op.setConstant(Integer.valueOf(3));
            push(op);
        }
            break;

        case Opcodes.ICONST_4: {
            Operand op = new Operand();
            op.setConstant(Integer.valueOf(4));
            push(op);
        }
            break;

        case Opcodes.ICONST_5: {
            Operand op = new Operand();
            op.setConstant(Integer.valueOf(5));
            push(op);
        }
            break;

        case Opcodes.LCONST_0: {
            Operand op = new Operand();
            op.setConstant(Long.valueOf(0));
            push(op);
        }
            break;

        case Opcodes.LCONST_1: {
            Operand op = new Operand();
            op.setConstant(Long.valueOf(1));
            push(op);
        }
            break;

        case Opcodes.FCONST_0: {
            Operand op = new Operand();
            op.setConstant(Float.valueOf(0));
            push(op);
        }
            break;

        case Opcodes.FCONST_1: {
            Operand op = new Operand();
            op.setConstant(Float.valueOf(1));
            push(op);
        }
            break;

        case Opcodes.FCONST_2: {
            Operand op = new Operand();
            op.setConstant(Float.valueOf(2));
            push(op);
        }
            break;

        case Opcodes.DCONST_0: {
            Operand op = new Operand();
            op.setConstant(Double.valueOf(0));
            push(op);
        }
            break;

        case Opcodes.DCONST_1: {
            Operand op = new Operand();
            op.setConstant(Double.valueOf(1));
            push(op);
        }
            break;

        case Opcodes.IALOAD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LALOAD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.FALOAD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("F");
            push(op);
        }
            break;

        case Opcodes.DALOAD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("D");
            push(op);
        }
            break;

        case Opcodes.AALOAD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("Ljava/lang/Object;");
            push(op);
        }
            break;

        case Opcodes.BALOAD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("B");
            push(op);
        }
            break;

        case Opcodes.CALOAD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("C");
            push(op);
        }
            break;

        case Opcodes.SALOAD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("S");
            push(op);
        }
            break;

        case Opcodes.IASTORE:
        case Opcodes.LASTORE:
        case Opcodes.FASTORE:
        case Opcodes.DASTORE:
        case Opcodes.AASTORE:
        case Opcodes.BASTORE:
        case Opcodes.CASTORE:
        case Opcodes.SASTORE:
            if (stack.size() < 2) {
                stack.clear();
            } else {
                Operand value = stack.remove(stack.size() - 1);
                Operand reg = stack.remove(stack.size() - 1);
                registers.put(Integer.valueOf(reg.getRegister()), value);
            }
            break;

        case Opcodes.POP:
            pop();
            break;

        case Opcodes.POP2:
            pop2();
            break;

        case Opcodes.DUP:
            if (!stack.isEmpty()) {
                Operand op = stack.get(stack.size() - 1);
                push(op);
            }
            break;

        case Opcodes.DUP_X1:
            if (stack.size() >= 2) {
                Operand op = stack.get(stack.size() - 1);
                stack.add(stack.size() - 2, op);
            }
            break;

        case Opcodes.DUP_X2:
            if (stack.size() >= 2) {
                Operand op = stack.get(stack.size() - 2);
                String sig = op.getSignature();
                op = stack.get(stack.size() - 1);
                if ("J".equals(sig) || "D".equals(sig)) {
                    stack.add(stack.size() - 2, op);
                } else if (stack.size() >= 3) {
                    stack.add(stack.size() - 3, op);
                }
            }
            break;

        case Opcodes.DUP2:
            if (stack.size() >= 2) {
                stack.add(stack.get(stack.size() - 2));
                stack.add(stack.get(stack.size() - 2));
            }
            break;

        case Opcodes.DUP2_X1:
            if (stack.size() >= 1) {
                Operand op = stack.get(stack.size() - 1);
                String sig = op.getSignature();
                if ("J".equals(sig) || "D".equals(sig)) {
                    if (stack.size() >= 3) {
                        stack.add(stack.size() - 3, op);
                        op = stack.get(stack.size() - 2);
                        stack.add(stack.size() - 4, op);
                    }
                } else {
                    if (stack.size() >= 2) {
                        stack.add(stack.size() - 2, op);
                    }
                }
            }
            break;

        case Opcodes.DUP2_X2:
            if (stack.size() >= 1) {
                Operand op = stack.get(stack.size() - 1);
                String sig = op.getSignature();
                if ("J".equals(sig) || "D".equals(sig)) {
                    if (stack.size() >= 2) {
                        op = stack.get(stack.size() - 2);
                        sig = op.getSignature();
                        if ("J".equals(sig) || "D".equals(sig)) {
                            op = stack.get(stack.size() - 1);
                            stack.add(stack.size() - 2, op);
                        } else {
                            if (stack.size() >= 3) {
                                op = stack.get(stack.size() - 1);
                                stack.add(stack.size() - 3, op);
                            }
                        }
                    }
                } else {
                    if (stack.size() >= 3) {
                        op = stack.get(stack.size() - 3);
                        sig = op.getSignature();
                        if ("J".equals(sig) || "D".equals(sig)) {
                            op = stack.get(stack.size() - 2);
                            stack.add(stack.size() - 3, op);
                            op = stack.get(stack.size() - 1);
                            stack.add(stack.size() - 3, op);
                        } else {
                            if (stack.size() >= 4) {
                                op = stack.get(stack.size() - 2);
                                stack.add(stack.size() - 4, op);
                                op = stack.get(stack.size() - 1);
                                stack.add(stack.size() - 4, op);
                            }
                        }
                    }
                }
            }
            break;

        case Opcodes.SWAP:
            if (stack.size() >= 2) {
                Operand op = stack.remove(stack.size() - 1);
                stack.add(stack.size() - 1, op);
            }
            break;

        case Opcodes.IADD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LADD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.FADD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("F");
            push(op);
        }
            break;

        case Opcodes.DADD: {
            pop2();
            Operand op = new Operand();
            op.setSignature("D");
            push(op);
        }
            break;

        case Opcodes.ISUB: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LSUB: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.FSUB: {
            pop2();
            Operand op = new Operand();
            op.setSignature("F");
            push(op);
        }
            break;

        case Opcodes.DSUB: {
            pop2();
            Operand op = new Operand();
            op.setSignature("D");
            push(op);
        }
            break;

        case Opcodes.IMUL: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LMUL: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.FMUL: {
            pop2();
            Operand op = new Operand();
            op.setSignature("F");
            push(op);
        }
            break;

        case Opcodes.DMUL: {
            pop2();
            Operand op = new Operand();
            op.setSignature("D");
            push(op);
        }
            break;

        case Opcodes.IDIV: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LDIV: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.FDIV: {
            pop2();
            Operand op = new Operand();
            op.setSignature("F");
            push(op);
        }
            break;

        case Opcodes.DDIV: {
            pop2();
            Operand op = new Operand();
            op.setSignature("D");
            push(op);
        }
            break;

        case Opcodes.IREM: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LREM: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.FREM: {
            pop2();
            Operand op = new Operand();
            op.setSignature("F");
            push(op);
        }
            break;

        case Opcodes.DREM: {
            pop2();
            Operand op = new Operand();
            op.setSignature("D");
            push(op);
        }
            break;

        case Opcodes.INEG: {
            pop();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LNEG: {
            pop();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.FNEG: {
            pop();
            Operand op = new Operand();
            op.setSignature("F");
            push(op);
        }
            break;

        case Opcodes.DNEG: {
            pop();
            Operand op = new Operand();
            op.setSignature("D");
            push(op);
        }
            break;

        case Opcodes.ISHL: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LSHL: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.ISHR: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LSHR: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.IUSHR: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LUSHR: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.IAND: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LAND: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.IOR: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LOR: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.IXOR: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.LXOR: {
            pop2();
            Operand op = new Operand();
            op.setSignature("J");
            push(op);
        }
            break;

        case Opcodes.I2L: {
            Operand lop = new Operand();
            lop.setSignature("J");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    lop.setConstant(Long.valueOf(((Integer) o).longValue()));
                }
            }
            push(lop);
        }
            break;

        case Opcodes.I2F: {
            Operand fop = new Operand();
            fop.setSignature("F");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    fop.setConstant(Float.valueOf(((Integer) o).floatValue()));
                }
            }
            push(fop);
        }
            break;

        case Opcodes.I2D: {
            Operand dop = new Operand();
            dop.setSignature("D");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    dop.setConstant(Double.valueOf(((Integer) o).doubleValue()));
                }
            }
            push(dop);
        }
            break;

        case Opcodes.L2I: {
            Operand iop = new Operand();
            iop.setSignature("I");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    iop.setConstant(Integer.valueOf(((Long) o).intValue()));
                }
            }
            push(iop);
        }
            break;

        case Opcodes.L2F: {
            Operand fop = new Operand();
            fop.setSignature("F");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    fop.setConstant(Float.valueOf(((Long) o).floatValue()));
                }
            }
            push(fop);
        }
            break;

        case Opcodes.L2D: {
            Operand dop = new Operand();
            dop.setSignature("D");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    dop.setConstant(Double.valueOf(((Long) o).doubleValue()));
                }
            }
            push(dop);
        }
            break;

        case Opcodes.F2I: {
            Operand iop = new Operand();
            iop.setSignature("I");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    iop.setConstant(Integer.valueOf(((Float) o).intValue()));
                }
            }
            push(iop);
        }
            break;

        case Opcodes.F2L: {
            Operand lop = new Operand();
            lop.setSignature("J");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    lop.setConstant(Long.valueOf(((Float) o).longValue()));
                }
            }
            push(lop);
        }
            break;

        case Opcodes.F2D: {
            Operand dop = new Operand();
            dop.setSignature("D");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    dop.setConstant(Double.valueOf(((Float) o).doubleValue()));
                }
            }
            push(dop);
        }
            break;

        case Opcodes.D2I: {
            Operand iop = new Operand();
            iop.setSignature("I");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    iop.setConstant(Integer.valueOf(((Double) o).intValue()));
                }
            }
            push(iop);
        }
            break;

        case Opcodes.D2L: {
            Operand lop = new Operand();
            lop.setSignature("J");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    lop.setConstant(Long.valueOf(((Double) o).longValue()));
                }
            }
            push(lop);
        }
            break;

        case Opcodes.D2F: {
            Operand fop = new Operand();
            fop.setSignature("F");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    fop.setConstant(Float.valueOf(((Double) o).floatValue()));
                }
            }
            push(fop);
        }
            break;

        case Opcodes.I2B: {
            Operand bop = new Operand();
            bop.setSignature("B");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    bop.setConstant(Byte.valueOf(((Integer) o).byteValue()));
                }
            }
            push(bop);
        }
            break;

        case Opcodes.I2C: {
            Operand cop = new Operand();
            cop.setSignature("C");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    cop.setConstant(Character.valueOf((char) ((Integer) o).intValue()));
                }
            }
            push(cop);
        }
            break;

        case Opcodes.I2S: {
            Operand sop = new Operand();
            sop.setSignature("S");
            if (!stack.isEmpty()) {
                Operand op = stack.remove(stack.size() - 1);
                Object o = op.getConstant();
                if (o != null) {
                    sop.setConstant(Short.valueOf((short) ((Integer) o).intValue()));
                }
            }
            push(sop);
        }
            break;

        case Opcodes.LCMP:
        case Opcodes.FCMPL:
        case Opcodes.FCMPG:
        case Opcodes.DCMPL:
        case Opcodes.DCMPG: {
            pop2();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.IRETURN:
        case Opcodes.LRETURN:
        case Opcodes.FRETURN:
        case Opcodes.DRETURN:
        case Opcodes.ARETURN:
            pop();
            break;

        case Opcodes.RETURN:
            // nop
            break;

        case Opcodes.ARRAYLENGTH: {
            pop();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;

        case Opcodes.ATHROW:
        case Opcodes.MONITORENTER:
        case Opcodes.MONITOREXIT:
            pop();
            break;
        }
    }

    public void performIntInsn(int opcode, int operand) {
        switch (opcode) {
        case Opcodes.BIPUSH: {
            Operand op = new Operand();
            op.setSignature("B");
            op.setConstant(Byte.valueOf((byte) operand));
            push(op);
        }
            break;

        case Opcodes.SIPUSH: {
            Operand op = new Operand();
            op.setSignature("S");
            op.setConstant(Short.valueOf((short) operand));
            push(op);
        }
            break;

        case Opcodes.NEWARRAY: {
            Operand op = new Operand();
            op.setSignature("[Ljava/lang/Object;");
            push(op);
        }
            break;
        }
    }

    public void performJumpInsn(int opcode, Label label) {
        switch (opcode) {
        case Opcodes.IFEQ:
        case Opcodes.IFNE:
        case Opcodes.IFLT:
        case Opcodes.IFGE:
        case Opcodes.IFGT:
        case Opcodes.IFLE:
        case Opcodes.IFNULL:
        case Opcodes.IFNONNULL:
            pop();
            break;

        case Opcodes.IF_ICMPEQ:
        case Opcodes.IF_ICMPNE:
        case Opcodes.IF_ICMPLT:
        case Opcodes.IF_ICMPGE:
        case Opcodes.IF_ICMPGT:
        case Opcodes.IF_ICMPLE:
        case Opcodes.IF_ACMPEQ:
        case Opcodes.IF_ACMPNE:
            pop2();
            break;

        case Opcodes.GOTO:
            // nop
            break;

        case Opcodes.JSR:
            // nop -- a fudge
            break;
        }
    }

    public void performLabel(Label label) {
        String sig = catchHandlers.remove(label);
        if (sig != null) {
            Operand op = new Operand();
            op.setSignature(sig);
            push(op);
        }
    }

    public void performLdcInsn(Object cst) {
        Operand op = new Operand();
        Class<?> cls = cst.getClass();
        if (Integer.class.equals(cls)) {
            op.setSignature("I");
        } else if (String.class.equals(cls)) {
            op.setSignature("Ljava/lang/String;");
        } else if (Long.class.equals(cls)) {
            op.setSignature("J");
        } else if (Float.class.equals(cls)) {
            op.setSignature("F");
        } else if (Double.class.equals(cls)) {
            op.setSignature("D");
        }

        op.setConstant(cst);
        push(op);
    }

    public void performLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        pop();
    }

    public void performMethodInsn(int opcode, String owner, String name, String desc) {
        switch (opcode) {
        case Opcodes.INVOKEVIRTUAL:
        case Opcodes.INVOKESPECIAL:
        case Opcodes.INVOKESTATIC:
        case Opcodes.INVOKEINTERFACE:
            String[] parmSigs = SignatureUtils.splitMethodParameterSignatures(desc);
            int numParms = parmSigs.length;
            while (numParms-- > 0) {
                pop();
            }
            if (opcode != Opcodes.INVOKESTATIC) {
                pop();
            }

            String returnSig = SignatureUtils.getReturnSignature(desc);
            if (!"V".equals(returnSig)) {
                Operand op = new Operand();
                op.setSignature(returnSig);
                push(op);
            }
            break;

        case Opcodes.INVOKEDYNAMIC:
            // no idea
            break;
        }
    }

    public void performMultiANewArrayInsn(String desc, int dims) {
        if (stack.size() < dims) {
            stack.clear();
        } else {
            while (dims-- > 0) {
                pop();
            }
        }
        Operand op = new Operand();
        op.setSignature(desc);
        push(op);
    }

    public void performTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
        pop();
    }

    public void performTryCatchBlock(Label start, Label end, Label handler, String type) {
        catchHandlers.put(handler, type);
    }

    public void performTypeInsn(int opcode, String type) {
        switch (opcode) {
        case Opcodes.NEW: {
            Operand op = new Operand();
            op.setSignature(type);
            push(op);
        }
            break;

        case Opcodes.ANEWARRAY: {
            pop();
            Operand op = new Operand();
            op.setSignature(type);
            push(op);
        }
            break;

        case Opcodes.CHECKCAST:
            // nop
            break;

        case Opcodes.INSTANCEOF: {
            pop();
            Operand op = new Operand();
            op.setSignature("I");
            push(op);
        }
            break;
        }
    }

    public void performVarInsn(int opcode, int variable) {
        switch (opcode) {
        case Opcodes.ILOAD:
        case Opcodes.LLOAD:
        case Opcodes.FLOAD:
        case Opcodes.DLOAD:
        case Opcodes.ALOAD:
            stack.add(registers.get(Integer.valueOf(variable)));
            break;

        case Opcodes.ISTORE:
        case Opcodes.LSTORE:
        case Opcodes.FSTORE:
        case Opcodes.DSTORE:
        case Opcodes.ASTORE:
            registers.put(Integer.valueOf(variable), stack.remove(stack.size() - 1));
            break;

        case Opcodes.RET:
            // nop - a fudge
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

    private void push(Operand op) {
        stack.add(op);
    }

    @Override
    public String toString() {
        return String.format("Stack: %s%nRegisters: %s%nFields: %s", stack.toString(), registers.toString(), fields.toString());
    }
}
