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
 */
class InterpolatorStringLookupTest {

    private static final String TESTKEY = "TestKey";

    private static final String TESTKEY2 = "TestKey2";

    private static final String TESTVAL = "TestValue";

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    @AfterAll
    public static void afterAll() {
        System.clearProperty(TESTKEY);
        System.clearProperty(TESTKEY2);
    }

    @BeforeAll
    public static void beforeAll() {
        System.setProperty(TESTKEY, TESTVAL);
        System.setProperty(TESTKEY2, TESTVAL);
    }

    private void assertLookupNotEmpty(final StringLookup lookup, final String key) {
        final String value = lookup.apply(key);
        assertNotNull(value);
        assertFalse(value.isEmpty());
    }

    private void assertDefaultInterpolatorLookups(final StringLookup lookup) {
        assertSystemPropertyLookup(lookup);
        assertEnvironmentLookup(lookup);
        assertDateLookup(lookup);
        assertJavaLookups(lookup);
    }

    private void assertDateLookup(final StringLookup lookup) {
        final String value = lookup.apply("date:" + DATE_PATTERN);
        assertNotNull(value, "No Date");

        final SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
        final String today = format.format(new Date());
        assertEquals(value, today);
    }

    private void assertEnvironmentLookup(final StringLookup lookup) {
        final String value = lookup.apply("env:PATH");
        assertNotNull(value);
    }

    private void assertJavaLookups(final StringLookup lookup) {
        assertLookupNotEmpty(lookup, "java:version");
        assertLookupNotEmpty(lookup, "java:runtime");
        assertLookupNotEmpty(lookup, "java:vm");
        assertLookupNotEmpty(lookup, "java:os");
        assertLookupNotEmpty(lookup, "java:locale");
        assertLookupNotEmpty(lookup, "java:hardware");
    }

    private void assertSystemPropertyLookup(final StringLookup lookup) {
        final String value = lookup.apply("sys:" + TESTKEY);
        assertEquals(TESTVAL, value);
    }

    @Test
    void testLookup() {
        final Map<String, String> map = new HashMap<>();
        map.put(TESTKEY, TESTVAL);
        final StringLookup lookup = new InterpolatorStringLookup(StringLookupFactory.INSTANCE.mapStringLookup(map));

        String value = lookup.apply(TESTKEY);
        assertEquals(TESTVAL, value);

        value = lookup.apply("ctx:" + TESTKEY);
        assertEquals(TESTVAL, value);

        value = lookup.apply("sys:" + TESTKEY);
        assertEquals(TESTVAL, value);

        value = lookup.apply("BadKey");
        assertNull(value);

        value = lookup.apply("ctx:" + TESTKEY);
        assertEquals(TESTVAL, value);
    }

    @Test
    void testLookupKeys() {
        final InterpolatorStringLookup lookup = new InterpolatorStringLookup((Map<String, Object>) null);
        final Map<String, StringLookup> stringLookupMap = lookup.getStringLookupMap();
        StringLookupFactoryTest.assertDefaultKeys(stringLookupMap);
    }

    @Test
    void testLookupWithDefaultInterpolator() {
        assertDefaultInterpolatorLookups(new InterpolatorStringLookup());
    }

    @Test
    void testLookupWithNullDefaultInterpolator() {
        assertDefaultInterpolatorLookups(new InterpolatorStringLookup((StringLookup) null));
    }

    @Test
    void testNull() {
        assertNull(InterpolatorStringLookup.INSTANCE.apply(null));
    }

    @Test
    void testToString() {
        assertFalse(new InterpolatorStringLookup().toString().isEmpty());
    }
}
