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
package com.mebigfatguy.junitflood.generator.simple;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import com.mebigfatguy.junitflood.Configuration;
import com.mebigfatguy.junitflood.classpath.ClassPathItem;
import com.mebigfatguy.junitflood.classpath.ClassPathIterator;
import com.mebigfatguy.junitflood.generator.GeneratorException;
import com.mebigfatguy.junitflood.generator.JUnitGenerator;

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
                ClassNode node = parseClass(item);

                if (node.outerClass == null) {
                    generateUnitTest(node);
                }
            }
        } catch (IOException ioe) {
            throw new GeneratorException("Failed generating unit tests", ioe);
        }
    }

    private ClassNode parseClass(ClassPathItem item) throws IOException {
        try (InputStream is = item.getInputStream()) {
            ClassReader cr = new ClassReader(is);
            ClassNode node = new ClassNode();
            cr.accept(node, ClassReader.SKIP_CODE);
            return node;
        }
    }

    private void generateUnitTest(ClassNode classNode) throws IOException {
        String className = classNode.name;
        int lastSlashPos = className.lastIndexOf('/');
        String packageName = (lastSlashPos >= 0) ? className.substring(0, lastSlashPos) : "";
        className = (lastSlashPos > 0) ? className.substring(lastSlashPos + 1) : className;

        File f = new File(configuration.getOutputDirectory(), packageName);
        f.mkdirs();

        f = new File(f, className + "Test.java");
        if (!f.exists()) {
            try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(f.toPath(), StandardCharsets.UTF_8))) {

                pw.println("package " + packageName.replaceAll("/", ".") + ";");
                pw.println();
                pw.println("public class " + className + "Test {");

                for (MethodNode node : (List<MethodNode>) classNode.methods) {

                    if (isTestable(node)) {
                        pw.println("\t@Test");
                        pw.println("\tpublic void test" + StringUtils.capitalize(node.name) + "{} {");
                        pw.println("\t}");
                    }
                }

                pw.println("}");
            }
        }
    }

    private boolean isTestable(MethodNode node) {

        if ((node.access & Opcodes.ACC_PUBLIC) == 0) {
            return false;
        }

        if (node.name.equals("<clinit>") || (node.name.equals("<init>"))) {
            return false;
        }

        return true;
    }
}
