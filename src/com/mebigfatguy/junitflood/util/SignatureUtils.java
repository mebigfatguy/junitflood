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
package com.mebigfatguy.junitflood.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignatureUtils {

	public static final Pattern ARGS_PATTERN = Pattern.compile("\\[*(L[^;]+;|I|J|D|F|B|C|S|Z)");
	private static final Map<String, Class<?>> primitiveTypes = new HashMap<String, Class<?>>();
	static {
		primitiveTypes.put("I", int.class);
		primitiveTypes.put("J", long.class);
		primitiveTypes.put("F", float.class);
		primitiveTypes.put("D", double.class);
		primitiveTypes.put("B", byte.class);
		primitiveTypes.put("C", char.class);
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
			cls = Array.newInstance(cls, new int[arrayCnt]).getClass();
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

	public static String getReturnSignature(String signature) {
		int rParenPos = signature.indexOf(')');
		return signature.substring(rParenPos + 1);
	}

	public static Class<?>[] convertMethodParameterSignaturesToClassArray(ClassLoader loader, String signature) throws ClassNotFoundException {

		String[] parmSigs = splitMethodParameterSignatures(signature);
		List<Class<?>> clses = new ArrayList<Class<?>>();
		for (String parmSig : parmSigs) {
			clses.add(signatureTypeToClass(loader, parmSig));
		}

		return clses.toArray(new Class<?>[clses.size()]);
	}

	public static SortedMap<Integer, String> getParameterRegisters(boolean isStatic, String signature) {
		String[] parmSigs = splitMethodParameterSignatures(signature);
		SortedMap<Integer, String> parmRegs = new TreeMap<Integer, String>();

		int reg = isStatic ? 0 : 1;
		for (String parmSig : parmSigs) {
			parmRegs.put(Integer.valueOf(reg), parmSig);

			reg += ("J".equals(parmSig) || "D".equals(parmSig)) ? 2 : 1;
		}

		return parmRegs;
	}

	public static Object[] createDefaultArgsForSignature(String signature) {
		int rParenPos = signature.indexOf(')');
		String args = signature.substring(1, rParenPos);
		List<Object> parms = new ArrayList<Object>();
		Matcher m = SignatureUtils.ARGS_PATTERN.matcher(args);

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
			} else if ("C".equals(typeSig)) {
				parms.add(Character.valueOf(' '));
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

	public static boolean isParm(String desc, boolean isStatic, int reg) {
		SortedMap<Integer, String> regs = getParameterRegisters(isStatic, desc);
		return (regs.containsKey(Integer.valueOf(reg)));
	}
}
