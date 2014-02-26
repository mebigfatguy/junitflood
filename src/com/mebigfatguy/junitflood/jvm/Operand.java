/*
 * junitflood - An automatic junit test generator
 * Copyright 2011-2014 MeBigFatGuy.com
 * Copyright 2011-2014 Dave Brosius
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
	private String signature = null;
	private Object constant = null;
	private boolean isNull = false;
	private Object userObject = null;

	public Operand() {
	}

	public Operand(int reg, String sig) {
		register = reg;
		signature = sig;
	}

	public Operand(String fld, String sig) {
		field = fld;
		signature = sig;
	}

	public int getRegister() {
		return register;
	}

	public String getField() {
		return field;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String sig) {
		signature = sig;
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

	public Object getUserObject() {
		return userObject;
	}

	public void setUserObject(Object uo) {
		userObject = uo;
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
