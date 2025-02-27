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

package com.baidu.hugegraph.backend.id;

public interface Id extends Comparable<Id> {

    public static final int UUID_LENGTH = 16;

    public Object asObject();

    public String asString();

    public long asLong();

    public byte[] asBytes();

    public int length();

    public IdType type();

    public default boolean number() {
        return this.type() == IdType.LONG;
    }

    public default boolean uuid() {
        return this.type() == IdType.UUID;
    }

    public default boolean string() {
        return this.type() == IdType.STRING;
    }

    public enum IdType {
        UNKNOWN,
        LONG,
        UUID,
        STRING,
    }
}
