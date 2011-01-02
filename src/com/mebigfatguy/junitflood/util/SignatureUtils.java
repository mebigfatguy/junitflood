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
package com.mebigfatguy.junitflood.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignatureUtils {

	public static final Pattern ARGS_PATTERN = Pattern.compile("\\[*(L[^;]+;|I|J|D|F|B|S|Z)");
	private static final Map<String, Class<?>> primitiveTypes = new HashMap<String, Class<?>>();
	static {
		primitiveTypes.put("I", int.class);
		primitiveTypes.put("J", long.class);
		primitiveTypes.put("F", float.class);
		primitiveTypes.put("D", double.class);
		primitiveTypes.put("B", byte.class);
		primitiveTypes.put("S", short.class);
		primitiveTypes.put("Z", boolean.class);
	}

	private SignatureUtils() {
	}

	public static Class<?> signatureTypeToClass(ClassLoader loader, String signatureType) throws ClassNotFoundException {
		String signature = signatureType;

		int arrayCnt = 0;
		while (signature.charAt(arrayCnt) == '[') {
			arrayCnt++;
		}

		if (arrayCnt > 0) {
			signature = signature.substring(arrayCnt);
		}

		Class<?> cls = primitiveTypes.get(signature);
		if (cls == null) {
			if (signature.charAt(0) == 'L') {
				signature = signature.substring(1, signature.length() - 1);
				cls = loader.loadClass(signature.replaceAll("/", "."));
			} else {
				throw new IllegalArgumentException("Invalid signature: " + signatureType);
			}
		}

		if (arrayCnt > 0) {

		}

		return cls;
	}

	public static String[] splitMethodParameterSignatures(String signature) {
		int rParenPos = signature.indexOf(')');
		String args = signature.substring(1, rParenPos);

		List<String> parmSigs = new ArrayList<String>();
		Matcher m = ARGS_PATTERN.matcher(args);
		while (m.find()) {
			parmSigs.add(m.group(1));
		}

		return parmSigs.toArray(new String[parmSigs.size()]);
	}

	public static Class<?>[] convertMethodParameterSignaturesToClassArray(ClassLoader loader, String signature) throws ClassNotFoundException {

		String[] parmSigs = splitMethodParameterSignatures(signature);
		List<Class<?>> clses = new ArrayList<Class<?>>();
		for (String parmSig : parmSigs) {
			clses.add(signatureTypeToClass(loader, parmSig));
		}

		return clses.toArray(new Class<?>[clses.size()]);
	}
}
