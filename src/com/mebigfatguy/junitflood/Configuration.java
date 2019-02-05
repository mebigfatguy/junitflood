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
package com.mebigfatguy.junitflood;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.Set;

import com.mebigfatguy.junitflood.classpath.ClassLookup;

public class Configuration {
	private Set<File> scanClassPath;
	private File outputDirectory;
	private Set<File> auxClassPath;
	private File rulesFile;
	private ClassLookup lookup;

	public Set<File> getScanClassPath() {
		return scanClassPath;
	}

	public void setScanClassPath(Set<File> scanClassPath) {
		this.scanClassPath = Collections.<File>unmodifiableSet(scanClassPath);
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	public Set<File> getAuxClassPath() {
		return auxClassPath;
	}

	public void setAuxClassPath(Set<File> auxClassPath) {
		this.auxClassPath = Collections.<File>unmodifiableSet(auxClassPath);
	}

	public File getRulesFile() {
		return rulesFile;
	}

	public void setRulesFile(File ruleFile) {
		rulesFile = ruleFile;
	}

	public void initializeLookup() throws MalformedURLException {
		lookup = new ClassLookup(this);
	}

	public ClassLookup getRepository() {
		return lookup;
	}


}
