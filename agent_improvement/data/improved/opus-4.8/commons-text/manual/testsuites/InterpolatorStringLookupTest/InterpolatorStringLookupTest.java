/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache license, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */
package org.apache.commons.text.lookup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link InterpolatorStringLookup}.
 *
 * <p>
 * An {@link InterpolatorStringLookup} resolves a key by inspecting an optional
 * {@code prefix:name} structure. The prefix selects a delegate lookup (for
 * example {@code sys} for system properties, {@code env} for environment
 * variables, {@code date} for the current date, {@code java} for runtime
 * details). When no prefix matches, the key is resolved by the default lookup.
 * </p>
 */
class InterpolatorStringLookupTest {

    /** A system property key set up before the tests and removed afterwards. */
    private static final String SYS_PROP_KEY = "TestKey";

    /** A second system property key, kept for parity with the original fixture. */
    private static final String SYS_PROP_KEY_2 = "TestKey2";

    /** The value stored under both system property keys. */
    private static final String SYS_PROP_VALUE = "TestValue";

    @BeforeAll
    public static void registerSystemProperties() {
        System.setProperty(SYS_PROP_KEY, SYS_PROP_VALUE);
        System.setProperty(SYS_PROP_KEY_2, SYS_PROP_VALUE);
    }

    @AfterAll
    public static void clearSystemProperties() {
        System.clearProperty(SYS_PROP_KEY);
        System.clearProperty(SYS_PROP_KEY_2);
    }

    /**
     * Asserts that looking up {@code key} via the given lookup yields a
     * non-null, non-empty value.
     */
    private void assertLookupReturnsNonEmptyValue(final StringLookup lookup, final String key) {
        final String value = lookup.apply(key);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    /**
     * Exercises the default set of prefixed lookups that an interpolator should
     * resolve out of the box: system property, environment variable, current
     * date, and several {@code java:*} runtime descriptors.
     */
    private void assertDefaultPrefixedLookupsResolve(final StringLookup lookup) {
        // sys: resolves a system property registered for this test.
        assertEquals(SYS_PROP_VALUE, lookup.apply("sys:" + SYS_PROP_KEY));

        // env: resolves the PATH environment variable, which is always present.
        assertNotNull(lookup.apply("env:PATH"));

        // date: formats the current date using the supplied pattern.
        final String formattedDate = lookup.apply("date:yyyy-MM-dd");
        assertNotNull(formattedDate, "No Date");
        final String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        assertEquals(formattedDate, today);

        // java: exposes various runtime descriptors, all of which are non-empty.
        assertLookupReturnsNonEmptyValue(lookup, "java:version");
        assertLookupReturnsNonEmptyValue(lookup, "java:runtime");
        assertLookupReturnsNonEmptyValue(lookup, "java:vm");
        assertLookupReturnsNonEmptyValue(lookup, "java:os");
        assertLookupReturnsNonEmptyValue(lookup, "java:locale");
        assertLookupReturnsNonEmptyValue(lookup, "java:hardware");
    }

    @Test
    void testLookup() {
        final Map<String, String> defaultMap = new HashMap<>();
        defaultMap.put(SYS_PROP_KEY, SYS_PROP_VALUE);
        final StringLookup lookup =
            new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(defaultMap));

        // An unprefixed key falls back to the default (map-backed) lookup.
        assertEquals(SYS_PROP_VALUE, lookup.apply(SYS_PROP_KEY));

        // The "ctx:" prefix targets the supplied default map lookup.
        assertEquals(SYS_PROP_VALUE, lookup.apply("ctx:" + SYS_PROP_KEY));

        // The "sys:" prefix targets system properties.
        assertEquals(SYS_PROP_VALUE, lookup.apply("sys:" + SYS_PROP_KEY));

        // An unknown key resolves to null.
        assertNull(lookup.apply("BadKey"));

        // Resolving via "ctx:" again still works (no state was consumed).
        assertEquals(SYS_PROP_VALUE, lookup.apply("ctx:" + SYS_PROP_KEY));
    }

    @Test
    void testLookupKeys() {
        final InterpolatorStringLookup lookup = new InterpolatorStringLookup((Map<String, Object>) null);
        final Map<String, StringLookup> stringLookupMap = lookup.getStringLookupMap();
        StringLookupFactoryTest.assertDefaultKeys(stringLookupMap);
    }

    @Test
    void testLookupWithDefaultInterpolator() {
        assertDefaultPrefixedLookupsResolve(new InterpolatorStringLookup());
    }

    @Test
    void testLookupWithNullDefaultInterpolator() {
        assertDefaultPrefixedLookupsResolve(new InterpolatorStringLookup((StringLookup) null));
    }

    @Test
    void testNull() {
        // A null key always resolves to null.
        assertNull(InterpolatorStringLookup.INSTANCE.apply(null));
    }

    @Test
    void testToString() {
        // toString must not blow up and must produce some non-empty text.
        assertFalse(new InterpolatorStringLookup().toString().isEmpty());
    }
}
