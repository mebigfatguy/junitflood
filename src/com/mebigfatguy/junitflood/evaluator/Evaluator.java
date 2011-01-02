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
package com.mebigfatguy.junitflood.evaluator;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mebigfatguy.junitflood.Configuration;
import com.mebigfatguy.junitflood.security.SaneSecurityManager;
import com.mebigfatguy.junitflood.security.SecurityManagerFactory;
import com.mebigfatguy.junitflood.util.SignatureUtils;

public class Evaluator {
	private static final Pattern ARGS_PATTERN = Pattern.compile("\\[*(L[^;]+;|I|L|J|D)");
	private final Configuration configuration;
	private final ClassLoader classLoader;

	public Evaluator(Configuration config) {
		configuration = config;
		classLoader = configuration.getRepository().createClassLoader();

	}

	public boolean attemptExecution(String clsName, String methodName, String signature) {
		try {
			Set<String> constructorSignatures = configuration.getRepository().getConstructors(clsName, clsName);
			SecurityManagerFactory.setSecurityManager(new SaneSecurityManager());

			Class<?> cls = classLoader.loadClass(clsName.replaceAll("/", "."));

			for (String constructorSignature : constructorSignatures) {
				try {
					Constructor<?> cons = cls.getDeclaredConstructor(SignatureUtils.convertMethodParameterSignaturesToClassArray(classLoader, constructorSignature));
					Object o = cons.newInstance(createArgsForSignature(constructorSignature));

				} catch (Exception e) {
				}
			}

			return true;
		} catch (Exception e) {
			return false;
		} finally {
			SecurityManagerFactory.setSecurityManager(null);
		}
	}

	private Object[] createArgsForSignature(String signature) {
		int rParenPos = signature.indexOf(')');
		String args = signature.substring(1, rParenPos);
		List<Object> parms = new ArrayList<Object>();
		Matcher m = ARGS_PATTERN.matcher(args);

		while (m.find()) {
			String typeSig = m.group(1);
			if ("I".equals(typeSig)) {
				parms.add(Integer.valueOf(0));
			} else if ("J".equals(typeSig)) {
				parms.add(Long.valueOf(0));
			} else if ("F".equals(typeSig)) {
				parms.add(Float.valueOf(0));
			} else if ("D".equals(typeSig)) {
				parms.add(Double.valueOf(0));
			} else if ("B".equals(typeSig)) {
				parms.add(Byte.valueOf("0"));
			} else if ("S".equals(typeSig)) {
				parms.add(Short.valueOf("0"));
			} else if ("Z".equals(typeSig)) {
				parms.add(Boolean.FALSE);
			} else {
				parms.add(null);
			}
		}

		return parms.toArray(new Object[parms.size()]);
	}
}
