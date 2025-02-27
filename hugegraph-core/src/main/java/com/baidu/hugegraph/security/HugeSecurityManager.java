/*
 * Copyright 2017 HugeGraph Authors
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.baidu.hugegraph.security;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class HugeSecurityManager extends SecurityManager {

    private static final String GREMLIN_SERVER_WORKER = "gremlin-server-exec";
    private static final String TASK_WORKER = "task-worker";
    private static final Set<String> GREMLIN_EXECUTOR_CLASS = ImmutableSet.of(
            "org.apache.tinkerpop.gremlin.groovy.jsr223.GremlinGroovyScriptEngine"
    );

    private static final Set<String> DENIED_PERMISSIONS = ImmutableSet.of(
            "setSecurityManager"
    );

    private static final Set<String> ACCEPT_CLASS_LOADERS = ImmutableSet.of(
            "groovy.lang.GroovyClassLoader",
            "sun.reflect.DelegatingClassLoader",
            "org.codehaus.groovy.reflection.SunClassLoader",
            "org.codehaus.groovy.runtime.callsite.CallSiteClassLoader"
    );

    private static final Set<String> WHITE_SYSTEM_PROPERTYS = ImmutableSet.of(
            "line.separator",
            "file.separator"
    );

    @Override
    public void checkPermission(Permission permission) {
        if (DENIED_PERMISSIONS.contains(permission.getName()) &&
            callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to access denied permission via Gremlin");
        }
    }

    @Override
    public void checkPermission(Permission permission, Object context) {
        if (DENIED_PERMISSIONS.contains(permission.getName()) &&
            callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to access denied permission via Gremlin");
        }
    }

    @Override
    public void checkCreateClassLoader() {
        if (!callFromAcceptClassLoaders() && callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to create class loader via Gremlin");
        }
        super.checkCreateClassLoader();
    }

    @Override
    public void checkLink(String lib) {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to link library via Gremlin");
        }
        super.checkLink(lib);
    }

    @Override
    public void checkAccess(Thread thread) {
        if (callFromGremlin() && !callFromCaffeine()) {
            throw new SecurityException(
                      "Not allowed to access thread via Gremlin");
        }
        super.checkAccess(thread);
    }

    @Override
    public void checkAccess(ThreadGroup threadGroup) {
        if (callFromGremlin() && !callFromCaffeine()) {
            throw new SecurityException(
                      "Not allowed to access thread group via Gremlin");
        }
        super.checkAccess(threadGroup);
    }

    @Override
    public void checkExit(int status) {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to call System.exit() via Gremlin");
        }
        super.checkExit(status);
    }

    @Override
    public void checkExec(String cmd) {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to execute command via Gremlin");
        }
        super.checkExec(cmd);
    }

    @Override
    public void checkRead(FileDescriptor fd) {
        if (callFromGremlin()) {
            throw new SecurityException("Not allowed to read fd via Gremlin");
        }
        super.checkRead(fd);
    }

    @Override
    public void checkRead(String file) {
        if (callFromGremlin()) {
            throw new SecurityException("Not allowed to read file via Gremlin");
        }
        super.checkRead(file);
    }

    @Override
    public void checkRead(String file, Object context) {
        if (callFromGremlin()) {
            throw new SecurityException("Not allowed to read file via Gremlin");
        }
        super.checkRead(file, context);
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
        if (callFromGremlin()) {
            throw new SecurityException("Not allowed to write fd via Gremlin");
        }
        super.checkWrite(fd);
    }

    @Override
    public void checkWrite(String file) {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to write file via Gremlin");
        }
        super.checkWrite(file);
    }

    @Override
    public void checkDelete(String file) {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to delete file via Gremlin");
        }
        super.checkDelete(file);
    }

    @Override
    public void checkListen(int port) {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to listen socket via Gremlin");
        }
        super.checkListen(port);
    }

    @Override
    public void checkAccept(String host, int port) {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to accept socket via Gremlin");
        }
        super.checkAccept(host, port);
    }

    @Override
    public void checkConnect(String host, int port) {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to connect socket via Gremlin");
        }
        super.checkConnect(host, port);
    }

    @Override
    public void checkConnect(String host, int port, Object context) {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to connect socket via Gremlin");
        }
        super.checkConnect(host, port, context);
    }

    @Override
    public void checkMulticast(InetAddress maddr) {
        if (callFromGremlin()) {
            throw new SecurityException("Not allowed to multicast via Gremlin");
        }
        super.checkMulticast(maddr);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void checkMulticast(InetAddress maddr, byte ttl) {
        if (callFromGremlin()) {
            throw new SecurityException("Not allowed to multicast via Gremlin");
        }
        super.checkMulticast(maddr, ttl);
    }

    @Override
    public void checkSetFactory() {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to set socket factory via Gremlin");
        }
        super.checkSetFactory();
    }

    @Override
    public void checkPropertiesAccess() {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to access system properties via Gremlin");
        }
        super.checkPropertiesAccess();
    }

    @Override
    public void checkPropertyAccess(String key) {
        if (!callFromAcceptClassLoaders() && callFromGremlin() &&
            !WHITE_SYSTEM_PROPERTYS.contains(key)) {
            throw new SecurityException(String.format(
                      "Not allowed to access system property(%s) via Gremlin",
                      key));
        }
        super.checkPropertyAccess(key);
    }

    @Override
    public void checkPrintJobAccess() {
        if (callFromGremlin()) {
            throw new SecurityException("Not allowed to print job via Gremlin");
        }
        super.checkPrintJobAccess();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void checkSystemClipboardAccess() {
        if (callFromGremlin()) {
            throw new SecurityException(
                      "Not allowed to access system clipboard via Gremlin");
        }
        super.checkSystemClipboardAccess();
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
    public void checkSecurityAccess(String target) {
        super.checkSecurityAccess(target);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void checkMemberAccess(Class<?> clazz, int which) {
        super.checkMemberAccess(clazz, which);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean checkTopLevelWindow(Object window) {
        return super.checkTopLevelWindow(window);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void checkAwtEventQueueAccess() {
        super.checkAwtEventQueueAccess();
    }

    private static boolean callFromGremlin() {
        return callFromWorkerWithClass(GREMLIN_EXECUTOR_CLASS);
    }

    private static boolean callFromAcceptClassLoaders() {
        return callFromWorkerWithClass(ACCEPT_CLASS_LOADERS);
    }

    private static boolean callFromCaffeine() {
        String clazz = "com.github.benmanes.caffeine.cache.BoundedLocalCache";
        String method = "scheduleDrainBuffers";
        return callFromMethod(clazz, method);
    }

    private static boolean callFromWorkerWithClass(Set<String> classes) {
        Thread curThread = Thread.currentThread();
        if (curThread.getName().startsWith(GREMLIN_SERVER_WORKER) ||
            curThread.getName().startsWith(TASK_WORKER)) {
            StackTraceElement[] elements = curThread.getStackTrace();
            for (StackTraceElement element : elements) {
                String className = element.getClassName();
                if (classes.contains(className)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean callFromMethod(String clazz, String method) {
        Thread curThread = Thread.currentThread();
        StackTraceElement[] elements = curThread.getStackTrace();
        for (StackTraceElement element : elements) {
            if (clazz.equals(element.getClassName()) &&
                method.equals(element.getMethodName())) {
                return true;
            }
        }
        return false;
    }
}
