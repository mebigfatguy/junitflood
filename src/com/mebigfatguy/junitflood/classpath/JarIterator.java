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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.mebigfatguy.junitflood.util.Closer;

public class JarIterator implements Iterator<ClassPathItem> {
    private final File jarFile;
    private JarInputStream jarInputStream;
    private JarEntry jarEntry;

    public JarIterator(File jar) {
        jarFile = jar;
        try {
            jarInputStream = new JarInputStream(new BufferedInputStream(new FileInputStream(jar)));
        } catch (IOException ioe) {
            jarInputStream = null;
            jarEntry = null;
        }
    }

    @Override
    public boolean hasNext() {
        if (jarInputStream == null) {
            return false;
        }

        if (jarEntry != null) {
            return true;
        }

        try {
            jarEntry = jarInputStream.getNextJarEntry();
            return true;
        } catch (IOException ioe) {
            Closer.closeQuietly(jarInputStream);
            jarInputStream = null;
            jarEntry = null;
            return false;
        }
    }

    @Override
    public ClassPathItem next() {
        hasNext();

        if (jarInputStream != null) {
            if (jarEntry != null) {
                ClassPathItem item = new ClassPathItem(jarInputStream, jarEntry);
                jarEntry = null;
                return item;
            }
        }

        throw new NoSuchElementException("No more elements in " + jarFile);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("JarIterator doesn't support remove");
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
