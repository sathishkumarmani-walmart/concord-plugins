package com.walmartlabs.concord.plugins.env;

/*-
 * *****
 * Concord
 * -----
 * Copyright (C) 2017 - 2021 Walmart Inc., Concord Authors
 * -----
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =====
 */

import javax.inject.Named;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.requireNonNull;

@Named("env")
public class EnvTask implements com.walmartlabs.concord.sdk.Task, com.walmartlabs.concord.runtime.v2.sdk.Task, Map<String, String> {

    @Override
    public String get(Object key) {
        requireNonNull(key, "Key cannot be null");

        if (!(key instanceof String)) {
            throw new IllegalArgumentException("Invalid key type. Expected a string, got " + key.getClass());
        }

        return System.getenv((String) key);
    }

    @Override
    public int size() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean isEmpty() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean containsKey(Object key) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public boolean containsValue(Object value) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public String put(String key, String value) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public String remove(Object key) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        throw new RuntimeException("Not supported");
    }

    @Override
    public void clear() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public Set<String> keySet() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public Collection<String> values() {
        throw new RuntimeException("Not supported");
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        throw new RuntimeException("Not supported");
    }
}
