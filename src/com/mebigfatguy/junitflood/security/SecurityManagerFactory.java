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
package com.mebigfatguy.junitflood.security;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

public class SecurityManagerFactory extends SecurityManager {

	private static SecurityManager delegatedSecurityManager = null;

	public static void initialize() {
		//make sure setSecurityManager is called
	}

	private SecurityManagerFactory() {
		System.setSecurityManager(this);
	}

	public static void setSecurityManager(SecurityManager sm) {
		delegatedSecurityManager = sm;
	}

	@Override
	@Deprecated
	public boolean getInCheck() {
		return super.getInCheck();
	}

	@Override
	protected Class<?>[] getClassContext() {
		return super.getClassContext();
	}

	@Override
	@Deprecated
	protected ClassLoader currentClassLoader() {
		return super.currentClassLoader();
	}

	@Override
	@Deprecated
	protected Class<?> currentLoadedClass() {
		return super.currentLoadedClass();
	}

	@Override
	@Deprecated
	protected int classDepth(String name) {
		return super.classDepth(name);
	}

	@Override
	@Deprecated
	protected int classLoaderDepth() {
		return super.classLoaderDepth();
	}

	@Override
	@Deprecated
	protected boolean inClass(String name) {
		return super.inClass(name);
	}

	@Override
	@Deprecated
	protected boolean inClassLoader() {
		return super.inClassLoader();
	}

	@Override
	public Object getSecurityContext() {
		return super.getSecurityContext();
	}

	@Override
	public void checkPermission(Permission perm) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkPermission(perm);
		}
	}

	@Override
	public void checkPermission(Permission perm, Object context) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkPermission(perm, context);
		}
	}

	@Override
	public void checkCreateClassLoader() {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkCreateClassLoader();
		}
	}

	@Override
	public void checkAccess(Thread t) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkAccess(t);
		}
	}

	@Override
	public void checkAccess(ThreadGroup g) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkAccess(g);
		}
	}

	@Override
	public void checkExit(int status) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkExit(status);
		}
	}

	@Override
	public void checkExec(String cmd) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkExec(cmd);
		}
	}

	@Override
	public void checkLink(String lib) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkLink(lib);
		}
	}

	@Override
	public void checkRead(FileDescriptor fd) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkRead(fd);
		}
	}

	@Override
	public void checkRead(String file) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkRead(file);
		}
	}

	@Override
	public void checkRead(String file, Object context) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkRead(file, context);
		}
	}

	@Override
	public void checkWrite(FileDescriptor fd) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkWrite(fd);
		}
	}

	@Override
	public void checkWrite(String file) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkWrite(file);
		}
	}

	@Override
	public void checkDelete(String file) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkDelete(file);
		}
	}

	@Override
	public void checkConnect(String host, int port) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkConnect(host, port);
		}
	}

	@Override
	public void checkConnect(String host, int port, Object context) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkConnect(host, port, context);
		}
	}

	@Override
	public void checkListen(int port) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkListen(port);
		}
	}

	@Override
	public void checkAccept(String host, int port) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkAccept(host, port);
		}
	}

	@Override
	public void checkMulticast(InetAddress maddr) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkMulticast(maddr);
		}
	}

	@Override
	@Deprecated
	public void checkMulticast(InetAddress maddr, byte ttl) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkMulticast(maddr, ttl);
		}
	}

	@Override
	public void checkPropertiesAccess() {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkPropertiesAccess();
		}
	}

	@Override
	public void checkPropertyAccess(String key) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkPropertyAccess(key);
		}
	}

	@Override
	public boolean checkTopLevelWindow(Object window) {
		if (delegatedSecurityManager != null) {
			return delegatedSecurityManager.checkTopLevelWindow(window);
		}

		return true;
	}

	@Override
	public void checkPrintJobAccess() {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkPrintJobAccess();
		}
	}

	@Override
	public void checkSystemClipboardAccess() {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkSystemClipboardAccess();
		}
	}

	@Override
	public void checkAwtEventQueueAccess() {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkAwtEventQueueAccess();
		}
	}

	@Override
	public void checkPackageAccess(String pkg) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkPackageAccess(pkg);
		}
	}

	@Override
	public void checkPackageDefinition(String pkg) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkPackageDefinition(pkg);
		}
	}

	@Override
	public void checkSetFactory() {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkSetFactory();
		}
	}

	@Override
	public void checkMemberAccess(Class<?> clazz, int which) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkMemberAccess(clazz, which);
		}
	}

	@Override
	public void checkSecurityAccess(String target) {
		if (delegatedSecurityManager != null) {
			delegatedSecurityManager.checkSecurityAccess(target);
		}
	}

	@Override
	public ThreadGroup getThreadGroup() {
		return super.getThreadGroup();
	}
}
