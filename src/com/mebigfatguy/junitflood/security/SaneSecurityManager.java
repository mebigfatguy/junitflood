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
package com.mebigfatguy.junitflood.security;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

public class SaneSecurityManager extends SecurityManager {

	@Override
	public boolean getInCheck() {
		return super.getInCheck();
	}

	@Override
	protected Class<?>[] getClassContext() {
		return super.getClassContext();
	}

	@Override
	protected ClassLoader currentClassLoader() {
		return super.currentClassLoader();
	}

	@Override
	protected Class<?> currentLoadedClass() {
		return super.currentLoadedClass();
	}

	@Override
	protected int classDepth(String name) {
		return super.classDepth(name);
	}

	@Override
	protected int classLoaderDepth() {
		return super.classLoaderDepth();
	}

	@Override
	protected boolean inClass(String name) {
		return super.inClass(name);
	}

	@Override
	protected boolean inClassLoader() {
		return super.inClassLoader();
	}

	@Override
	public Object getSecurityContext() {
		return super.getSecurityContext();
	}

	@Override
	public void checkPermission(Permission perm) {
		super.checkPermission(perm);
	}

	@Override
	public void checkPermission(Permission perm, Object context) {
		super.checkPermission(perm, context);
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
	public boolean checkTopLevelWindow(Object window) {
		return true;
	}

	@Override
	public void checkPrintJobAccess() {
		super.checkPrintJobAccess();
	}

	@Override
	public void checkSystemClipboardAccess() {
		super.checkSystemClipboardAccess();
	}

	@Override
	public void checkAwtEventQueueAccess() {
		super.checkAwtEventQueueAccess();
	}

	@Override
	public void checkPackageAccess(String pkg) {
		super.checkPackageAccess(pkg);
	}

	@Override
	public void checkPackageDefinition(String pkg) {
		super.checkPackageDefinition(pkg);
	}

	@Override
	public void checkSetFactory() {
		super.checkSetFactory();
	}

	@Override
	public void checkMemberAccess(Class<?> clazz, int which) {
		super.checkMemberAccess(clazz, which);
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
