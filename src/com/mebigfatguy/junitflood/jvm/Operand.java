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

public class Operand {
	private int register = -1;
	private String field = null;
	private String staticSignature = null;
	private String dynamicSignature = null;
	private Object constant = null;
	private boolean isNull = false;

	public Operand() {
	}

	public Operand(int reg, String signature) {
		register = reg;
		staticSignature = signature;
	}

	public Operand(String fld, String signature) {
		field = fld;
		staticSignature = signature;
	}

	public int getRegister() {
		return register;
	}

	public String getField() {
		return field;
	}

	public String getSignature() {
		if (dynamicSignature != null) {
			return dynamicSignature;
		}

		return staticSignature;
	}

	public void setStaticSignature(String signature) {
		staticSignature = signature;
	}

	public String getStaticSignature() {
		return staticSignature;
	}

	public void setDynamicSignature(String signature) {
		dynamicSignature = signature;
	}

	public String getDynamicSignature() {
		return dynamicSignature;
	}

	public Object getConstant() {
		return constant;
	}

	public void setConstant(Object cnst) {
		constant = cnst;
	}

	public boolean isNull() {
		return isNull;
	}

	public void setNull(boolean isNil) {
		isNull = isNil;
	}

	@Override
	public String toString() {
		String info = getSignature();
		if (register >= 0) {
			info += ":R" + register;
		} else if (field != null) {
			info += ":F(" + field + ")";
		}

		if (constant != null) {
			info += " " + constant;
		}

		return info;
	}
}
