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
package com.mebigfatguy.junitflood.generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class StatementList implements Iterable<Statement> {

    private final List<Statement> statements = new ArrayList<Statement>();
    private final Set<String> neededPackages = new TreeSet<String>();

    @Override
    public Iterator<Statement> iterator() {
        return statements.iterator();
    }

    public void clear() {
        statements.clear();
    }

    public String addConstructor(String clsName, Object... args) {
        neededPackages.add(getPackageName(clsName));

        Statement statement = Statement.createConstructor(clsName, args);
        statements.add(statement);
        return statement.getObjectName();
    }

    public String addMethodCall(String objectName, String methodName, Object... args) {
        Statement statement = Statement.createMethodCall(objectName, methodName, args);
        statements.add(statement);
        return objectName;
    }

    public Set<String> getNeededPackages() {
        neededPackages.remove(null);
        return Collections.<String>unmodifiableSet(neededPackages);
    }

    private String getPackageName(String clsName) {
        int slashPos = clsName.lastIndexOf('/');
        if (slashPos >= 0) {
            return clsName.substring(0, slashPos).replaceAll("/", ".");
        }

        return null;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
