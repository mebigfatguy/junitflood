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
import java.util.HashSet;
import java.util.Set;

public class SaneSecurityManager extends SecurityManager {

	private static final Set<String> SANE_PERMISSIONS = new HashSet<String>();
	static {
		SANE_PERMISSIONS.add("getProperty.package.access");
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
		if (!SANE_PERMISSIONS.contains(perm.getName())) {
			super.checkPermission(perm);
		}
	}

	@Override
	public void checkPermission(Permission perm, Object context) {
		if (!SANE_PERMISSIONS.contains(perm.getName())) {
			super.checkPermission(perm, context);
		}
	}

	@Override
	public void checkCreateClassLoader() {
	}

	@Override
	public void checkAccess(Thread t) {
	}

	@Override
	public void checkAccess(ThreadGroup g) {
	}

	@Override
	public void checkExit(int status) {
		throw new SecurityException("Denying exit with status: " + status);
	}

	@Override
	public void checkExec(String cmd) {
		throw new SecurityException("Denying execution of native cmd: " + cmd);
	}

	@Override
	public void checkLink(String lib) {
		throw new SecurityException("Denying linking of native library: " + lib);
	}

	@Override
	public void checkRead(FileDescriptor fd) {
	}

	@Override
	public void checkRead(String file) {
	}

	@Override
	public void checkRead(String file, Object context) {
	}

	@Override
	public void checkWrite(FileDescriptor fd) {
		throw new SecurityException("Denying write of file descriptor: " + fd);
	}

	@Override
	public void checkWrite(String file) {
		throw new SecurityException("Denying write of file: " + file);
	}

	@Override
	public void checkDelete(String file) {
		throw new SecurityException("Denying delete of file: " + file);	}

	@Override
	public void checkConnect(String host, int port) {
		throw new SecurityException("Denying connection to " + host + ":" + port);
	}

	@Override
	public void checkConnect(String host, int port, Object context) {
		throw new SecurityException("Denying connection to host:port: " + host + ":" + port);
	}

	@Override
	public void checkListen(int port) {
		throw new SecurityException("Denying listening on port: " + port);
	}

	@Override
	public void checkAccept(String host, int port) {
		throw new SecurityException("Denying accept from host:port: " + host + ":" + port);
	}

	@Override
	public void checkMulticast(InetAddress maddr) {
		throw new SecurityException("Denying multicast from: " + maddr);
	}

	@Override
	@Deprecated
	public void checkMulticast(InetAddress maddr, byte ttl) {
		throw new SecurityException("Denying multicast from: " + maddr);
	}

	@Override
	public void checkPropertiesAccess() {
	}

	@Override
	public void checkPropertyAccess(String key) {
	}

	@Override
	@Deprecated
	public boolean checkTopLevelWindow(Object window) {
		return true;
	}

	@Override
	public void checkPrintJobAccess() {
		super.checkPrintJobAccess();
	}

	@Override
	@Deprecated
	public void checkSystemClipboardAccess() {
		super.checkSystemClipboardAccess();
	}

	@Override
	@Deprecated
	public void checkAwtEventQueueAccess() {
	}

	@Override
	public void checkPackageAccess(String pkg) {
	}

	@Override
	public void checkPackageDefinition(String pkg) {
	}

	@Override
	public void checkSetFactory() {
	}

	@Override
	@Deprecated
	public void checkMemberAccess(Class<?> clazz, int which) {
	}

	@Override
	public void checkSecurityAccess(String target) {
		super.checkSecurityAccess(target);
	}

	@Override
	public ThreadGroup getThreadGroup() {
		return super.getThreadGroup();
	}
}
