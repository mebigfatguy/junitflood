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
package com.mebigfatguy.junitflood;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mebigfatguy.junitflood.factory.JUnitFloodFactory;
import com.mebigfatguy.junitflood.generator.GeneratorException;
import com.mebigfatguy.junitflood.generator.JUnitGenerator;

public class JUnitFlood {

	private static final  Logger logger = LoggerFactory.getLogger(JUnitFlood.class);

	public static void main(String[] args) {
		try {

			Options options = createOptions();

			CommandLineParser parser = new GnuParser();
			CommandLine cmd = parser.parse( options, args);

			Configuration configuration = getConfiguration(cmd);

			JUnitGenerator generator = JUnitFloodFactory.getJUnitGenerator(configuration);
			generator.generate();


		} catch (MissingOptionException moe) {
			logger.error("Failed parsing command line", moe);
		} catch (ConfigurationException ce) {
			logger.error("Failed parsing user configuration", ce);
		} catch (ParseException pe) {
			logger.error("Improper configurations settings", pe);
		}
		catch (GeneratorException ge) {
			logger.error("Failed generating unit tests", ge);
		}
	}

	private static Options createOptions() {
		Options options = new Options();
		Option option;

		option = new Option("c", "classPath", true, "The classpath on which to generate unit tests");
		option.setRequired(true);
		options.addOption(option);

		option = new Option("a", "auxClassPath", true, "Auxilliary classpath needed to generate unit tests");
		option.setRequired(true);
		options.addOption(option);

		options.addOption("o", "outputPath", true, "The output path where unit tests will be generated");
		option.setRequired(true);
		options.addOption(option);

		options.addOption("r", "rulesFile", true, "The file that holds rules for generating classes");
		options.addOption(option);

		return options;
	}

	private static Configuration getConfiguration(CommandLine cmdLine) throws ConfigurationException {
		Configuration conf = new Configuration();

		conf.setScanClassPath(buildClassPathSet(cmdLine.getOptionValue('c')));
		conf.setAuxClassPath(buildClassPathSet(cmdLine.getOptionValue('a')));

		conf.setOutputDirectory(buildFile('o', cmdLine.getOptionValue('o'), false));
		conf.setRulesFile(buildFile('r', cmdLine.getOptionValue('r'), true));

		return conf;
	}

	private static Set<File> buildClassPathSet(String paths) throws ConfigurationException {
		Set<File> classPath = new HashSet<File>();
		for (String path : paths.split("(:|;)")) {
			File f = new File(path);
			if (!f.exists()) {
				throw new ConfigurationException("'classPath' specifies non-existent path: " + path);
			}
			if (!f.isDirectory() && (!f.getName().endsWith(".jar"))) {
				throw new ConfigurationException("'classPath' specifies non-usable path: " + path);
			}
			classPath.add(f);
		}

		return classPath;
	}

	private static File buildFile(char prop, String path, boolean mustExist) throws ConfigurationException {
		if (path == null) {
			return null;
		}

		File fp = new File(path);

		if (mustExist) {
			if (!fp.exists()) {
				throw new ConfigurationException("'" + prop + "' specifies a non existent file: " + fp);
			}
			if (fp.isDirectory()) {
				throw new ConfigurationException("'" + prop + "' specifies a directory, must be a file: " + fp);
			}
		}

		return fp;
	}
}
