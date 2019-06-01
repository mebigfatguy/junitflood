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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassInfoCollectingVisitor extends ClassVisitor {

    private final ClassDetails details;

    public ClassInfoCollectingVisitor(ClassDetails info) {
        super(Opcodes.ASM4);
        details = info;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {

        details.addInterfaces(interfaces);
        details.addSuperclass(superName);
        details.setFinal((access & Opcodes.ACC_FINAL) != 0);
        details.setInterface((access & Opcodes.ACC_INTERFACE) != 0);
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
        details.addDefaultConstructor();
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
        if ("<init>".equals(name)) {
            details.addConstructor(access, desc);
        } else if (!"<clinit>".equals(name)) {
            details.addMethod(access, name, desc);
        }
        return null;
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
    }

    @Override
    public void visitSource(String source, String debug) {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
