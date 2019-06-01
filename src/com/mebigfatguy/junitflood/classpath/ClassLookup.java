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
package com.mebigfatguy.junitflood.classpath;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mebigfatguy.junitflood.Configuration;

public class ClassLookup {

    private final ClassLoader classLoader;
    private final Map<String, ClassDetails> classDetails = new HashMap<String, ClassDetails>();
    private final URL[] urls;

    public ClassLookup(Configuration config) throws MalformedURLException {
        Set<File> roots = new HashSet<File>();
        roots.addAll(config.getScanClassPath());
        roots.addAll(config.getAuxClassPath());

        urls = new URL[roots.size()];

        int i = 0;
        for (File root : roots) {
            urls[i++] = new URL("file://" + root.getAbsolutePath());
        }

        classLoader = createClassLoader();
    }

    public final ClassLoader createClassLoader() {
        return AccessController.<URLClassLoader>doPrivileged(new PrivilegedAction<URLClassLoader>() {
            @Override
            public URLClassLoader run() {
                return new URLClassLoader(urls);
            }
        });
    }

    public Set<String> getConstructors(String clsName, String fromClass) {
        ClassDetails classInfo = classDetails.get(clsName);
        if (classInfo == null) {
            classInfo = new ClassDetails(classLoader, clsName);
            classDetails.put(clsName, classInfo);
        }

        return classInfo.getConstructors(fromClass);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
