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
package com.mebigfatguy.junitflood.classpath;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mebigfatguy.junitflood.util.Closer;

public class ClassDetails {
	private static final  Logger logger = LoggerFactory.getLogger(ClassDetails.class);

	private final ClassLoader classLoader;
	private final String className;
	private boolean isFinal;
	private boolean isInterface;
	private final Map<LookupType, Map<String, Access>> accessInfo;

	public ClassDetails(ClassLoader loader, String clsName) {
		classLoader = loader;
		className = clsName;
		accessInfo = loadClassDetails(clsName);
	}

	public boolean isOverridable() {
		return !isFinal || isInterface;
	}

	public Set<String> getConstructors(String fromClass) {
		Map<String, Access> ctors = accessInfo.get(LookupType.CONSTRUCTOR);
		if (ctors != null) {
			return Collections.<String>unmodifiableSet(ctors.keySet()); //for now
		} else {
			return Collections.<String>emptySet();
		}
	}

	private Map<LookupType, Map<String, Access>> loadClassDetails(String clsName) {

		Map<LookupType, Map<String, Access>> details = new EnumMap<LookupType, Map<String, Access>>(LookupType.class);
		InputStream is = null;
		try {
			is = classLoader.getResourceAsStream(clsName + ".class");
			ClassReader cr = new ClassReader(is);
			ClassInfoCollectingVisitor cicv = new ClassInfoCollectingVisitor(this);
			cr.accept(cicv, ClassReader.SKIP_DEBUG|ClassReader.SKIP_CODE);
		} catch (IOException ioe) {
			logger.error("Failed parsing class " + clsName, ioe);
		} finally {
			Closer.closeQuietly(is);
		}
		return details;
	}

	void addInterfaces(String[] interfaces) {
		Map<String, Access> clsInterfaces = accessInfo.get(LookupType.INTERFACE);
		if (clsInterfaces == null) {
			clsInterfaces = new HashMap<String, Access>();
			accessInfo.put(LookupType.INTERFACE, clsInterfaces);
		}

		for (String inf : interfaces) {
			clsInterfaces.put(inf, Access.PUBLIC);
		}
	}

	void addSuperclass(String superName) {
		Map<String, Access> clsSupers = accessInfo.get(LookupType.SUPERCLASS);
		if (clsSupers == null) {
			clsSupers = new HashMap<String, Access>();
			accessInfo.put(LookupType.SUPERCLASS, clsSupers);
		}
		clsSupers.put(superName, Access.PUBLIC);
	}

	void setFinal(boolean clsFinal) {
		isFinal = clsFinal;
	}

	void setInterface(boolean clsIsInterface) {
		isInterface = clsIsInterface;
	}

	void addConstructor(int access, String desc) {
		Map<String, Access> clsConstructors = accessInfo.get(LookupType.CONSTRUCTOR);
		if (clsConstructors == null) {
			clsConstructors = new HashMap<String, Access>();
			accessInfo.put(LookupType.CONSTRUCTOR, clsConstructors);
		}

		Access clsAccess = convertAccess(access);
		clsConstructors.put(desc, clsAccess);
	}

	void addDefaultConstructor() {
		Map<String, Access> clsConstructors = accessInfo.get(LookupType.CONSTRUCTOR);
		if (clsConstructors == null) {
			clsConstructors = new HashMap<String, Access>();
			accessInfo.put(LookupType.CONSTRUCTOR, clsConstructors);
			clsConstructors.put("()V", Access.PUBLIC);
		}
	}

	void addMethod(int access, String name, String desc) {
		Map<String, Access> clsMethods = accessInfo.get(LookupType.METHOD);
		if (clsMethods == null) {
			clsMethods = new HashMap<String, Access>();
			accessInfo.put(LookupType.METHOD, clsMethods);
		}

		Access clsAccess = convertAccess(access);
		clsMethods.put(name + desc, clsAccess);
	}

	private Access convertAccess(int access) {
		switch (access) {
			case Opcodes.ACC_PRIVATE:
				return Access.PRIVATE;
			case Opcodes.ACC_PROTECTED:
				return Access.PROTECTED;
			case Opcodes.ACC_PUBLIC:
				return Access.PUBLIC;
		    default:
		    	return Access.PACKAGE;
		}
	}
}
