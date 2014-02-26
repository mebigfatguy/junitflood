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
package com.mebigfatguy.junitflood.factory;

import com.mebigfatguy.junitflood.Configuration;
import com.mebigfatguy.junitflood.generator.JUnitGenerator;
import com.mebigfatguy.junitflood.generator.simple.SimpleGenerator;

public class JUnitFloodFactory {

	public static JUnitGenerator getJUnitGenerator(Configuration configuration) {
		//Based on some configuration option, create a certain kind of generator,
		//for now use the simple one

		SimpleGenerator gen = new SimpleGenerator();
		gen.setConfiguration(configuration);

		return gen;
	}
}
