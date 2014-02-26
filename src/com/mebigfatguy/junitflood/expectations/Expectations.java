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
package com.mebigfatguy.junitflood.expectations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Expectations {

	public Map<String, Set<Expectation>> expectations = new HashMap<String, Set<Expectation>>();

	public void addExpectation(int register, Expectation expectation) {
		String reg = String.valueOf(register);
		addExpectation(reg, expectation);
	}

	public void addExpectation(String field, Expectation expectation) {
		Set<Expectation> fieldExpectations = expectations.get(field);
		if (fieldExpectations == null) {
			fieldExpectations = new HashSet<Expectation>();
			expectations.put(field, fieldExpectations);
		}

		fieldExpectations.add(expectation);
	}
}
