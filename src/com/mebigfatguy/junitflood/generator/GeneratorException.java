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
package com.mebigfatguy.junitflood.generator;

public class GeneratorException extends Exception {

	private static final long serialVersionUID = -5552612957102654870L;
	
	private String srcClass;

	public GeneratorException(String message) {
		super(message);
	}
	
	public GeneratorException(String message, Throwable initCause) {
		super(message, initCause);
	}
	
	public void setParsedClass(String className) {
		srcClass = className;
	}
	
	public String getParsedClass() {
		return srcClass;
	}
}
