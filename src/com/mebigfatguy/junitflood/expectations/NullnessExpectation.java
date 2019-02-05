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
package com.mebigfatguy.junitflood.expectations;

public class NullnessExpectation implements Expectation {

	public enum NullState {UNKNOWN, ISNULL, ISNOTNULL, ANYSTATE};

	private NullState state = NullState.UNKNOWN;

	public NullnessExpectation(NullState nullState) {
		state = nullState;
	}

	public NullState getState() {
		return state;
	}

	@Override
	public int hashCode() {
		return 0;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof NullnessExpectation;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ":" + state.toString();
	}
}
