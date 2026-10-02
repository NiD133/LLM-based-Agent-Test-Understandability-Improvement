/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils}.
 */
class CharSetUtilsTest extends AbstractLangTest {

    @Test
    void testConstructor() {
        assertNotNull(new CharSetUtils());
        final Constructor<?>[] cons = CharSetUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
        assertTrue(Modifier.isPublic(CharSetUtils.class.getModifiers()));
        assertFalse(Modifier.isFinal(CharSetUtils.class.getModifiers()));
    }

    @Test
    void testContainsAny_StringString() {
        assertContainsAny(false, null, (String) null);
        assertContainsAny(false, null, "");

        assertContainsAny(false, "", (String) null);
        assertContainsAny(false, "", "");
        assertContainsAny(false, "", "a-e");

        assertContainsAny(false, "hello", (String) null);
        assertContainsAny(false, "hello", "");
        assertContainsAny(true, "hello", "a-e");
        assertContainsAny(true, "hello", "l-p");
    }

    @Test
    void testContainsAny_StringStringarray() {
        assertContainsAny(false, null, (String[]) null);
        assertContainsAny(false, null);
        assertContainsAny(false, null, (String) null);
        assertContainsAny(false, null, "a-e");

        assertContainsAny(false, "", (String[]) null);
        assertContainsAny(false, "");
        assertContainsAny(false, "", (String) null);
        assertContainsAny(false, "", "a-e");

        assertContainsAny(false, "hello", (String[]) null);
        assertContainsAny(false, "hello");
        assertContainsAny(false, "hello", (String) null);
        assertContainsAny(true, "hello", "a-e");

        assertContainsAny(true, "hello", "el");
        assertContainsAny(false, "hello", "x");
        assertContainsAny(true, "hello", "e-i");
        assertContainsAny(true, "hello", "a-z");
        assertContainsAny(false, "hello", "");
    }

    @Test
    void testCount_StringString() {
        assertCount(0, null, (String) null);
        assertCount(0, null, "");

        assertCount(0, "", (String) null);
        assertCount(0, "", "");
        assertCount(0, "", "a-e");

        assertCount(0, "hello", (String) null);
        assertCount(0, "hello", "");
        assertCount(1, "hello", "a-e");
        assertCount(3, "hello", "l-p");
    }

    @Test
    void testCount_StringStringarray() {
        assertCount(0, null, (String[]) null);
        assertCount(0, null);
        assertCount(0, null, (String) null);
        assertCount(0, null, "a-e");

        assertCount(0, "", (String[]) null);
        assertCount(0, "");
        assertCount(0, "", (String) null);
        assertCount(0, "", "a-e");

        assertCount(0, "hello", (String[]) null);
        assertCount(0, "hello");
        assertCount(0, "hello", (String) null);
        assertCount(1, "hello", "a-e");

        assertCount(3, "hello", "el");
        assertCount(0, "hello", "x");
        assertCount(2, "hello", "e-i");
        assertCount(5, "hello", "a-z");
        assertCount(0, "hello", "");
    }

    @Test
    void testDelete_StringString() {
        assertDelete(null, null, (String) null);
        assertDelete(null, null, "");

        assertDelete("", "", (String) null);
        assertDelete("", "", "");
        assertDelete("", "", "a-e");

        assertDelete("hello", "hello", (String) null);
        assertDelete("hello", "hello", "");
        assertDelete("hllo", "hello", "a-e");
        assertDelete("he", "hello", "l-p");
        assertDelete("hello", "hello", "z");
    }

    @Test
    void testDelete_StringStringarray() {
        assertDelete(null, null, (String[]) null);
        assertDelete(null, null);
        assertDelete(null, null, (String) null);
        assertDelete(null, null, "el");

        assertDelete("", "", (String[]) null);
        assertDelete("", "");
        assertDelete("", "", (String) null);
        assertDelete("", "", "a-e");

        assertDelete("hello", "hello", (String[]) null);
        assertDelete("hello", "hello");
        assertDelete("hello", "hello", (String) null);
        assertDelete("hello", "hello", "xyz");

        assertDelete("ho", "hello", "el");
        assertDelete("", "hello", "elho");
        assertDelete("hello", "hello", "");
        assertDelete("", "hello", "a-z");
        assertDelete("", "----", "-");
        assertDelete("heo", "hello", "l");
    }

    @Test
    void testKeep_StringString() {
        assertKeep(null, null, (String) null);
        assertKeep(null, null, "");

        assertKeep("", "", (String) null);
        assertKeep("", "", "");
        assertKeep("", "", "a-e");

        assertKeep("", "hello", (String) null);
        assertKeep("", "hello", "");
        assertKeep("", "hello", "xyz");
        assertKeep("hello", "hello", "a-z");
        assertKeep("hello", "hello", "oleh");
        assertKeep("ell", "hello", "el");
    }

    @Test
    void testKeep_StringStringarray() {
        assertKeep(null, null, (String[]) null);
        assertKeep(null, null);
        assertKeep(null, null, (String) null);
        assertKeep(null, null, "a-e");

        assertKeep("", "", (String[]) null);
        assertKeep("", "");
        assertKeep("", "", (String) null);
        assertKeep("", "", "a-e");

        assertKeep("", "hello", (String[]) null);
        assertKeep("", "hello");
        assertKeep("", "hello", (String) null);
        assertKeep("e", "hello", "a-e");

        assertKeep("e", "hello", "a-e");
        assertKeep("ell", "hello", "el");
        assertKeep("hello", "hello", "elho");
        assertKeep("hello", "hello", "a-z");
        assertKeep("----", "----", "-");
        assertKeep("ll", "hello", "l");
    }

    @Test
    void testSqueeze_StringString() {
        assertSqueeze(null, null, (String) null);
        assertSqueeze(null, null, "");

        assertSqueeze("", "", (String) null);
        assertSqueeze("", "", "");
        assertSqueeze("", "", "a-e");

        assertSqueeze("hello", "hello", (String) null);
        assertSqueeze("hello", "hello", "");
        assertSqueeze("hello", "hello", "a-e");
        assertSqueeze("helo", "hello", "l-p");
        assertSqueeze("heloo", "helloo", "l");
        assertSqueeze("hello", "helloo", "^l");
    }

    @Test
    void testSqueeze_StringStringarray() {
        assertSqueeze(null, null, (String[]) null);
        assertSqueeze(null, null);
        assertSqueeze(null, null, (String) null);
        assertSqueeze(null, null, "el");

        assertSqueeze("", "", (String[]) null);
        assertSqueeze("", "");
        assertSqueeze("", "", (String) null);
        assertSqueeze("", "", "a-e");

        assertSqueeze("hello", "hello", (String[]) null);
        assertSqueeze("hello", "hello");
        assertSqueeze("hello", "hello", (String) null);
        assertSqueeze("hello", "hello", "a-e");

        assertSqueeze("helo", "hello", "el");
        assertSqueeze("hello", "hello", "e");
        assertSqueeze("fofof", "fooffooff", "of");
        assertSqueeze("fof", "fooooff", "fo");
    }

    private void assertContainsAny(final boolean expected, final String str, final String... set) {
        assertEquals(expected, CharSetUtils.containsAny(str, set));
    }

    private void assertCount(final int expected, final String str, final String... set) {
        assertEquals(expected, CharSetUtils.count(str, set));
    }

    private void assertDelete(final String expected, final String str, final String... set) {
        assertStringResult(expected, CharSetUtils.delete(str, set));
    }

    private void assertKeep(final String expected, final String str, final String... set) {
        assertStringResult(expected, CharSetUtils.keep(str, set));
    }

    private void assertSqueeze(final String expected, final String str, final String... set) {
        assertStringResult(expected, CharSetUtils.squeeze(str, set));
    }

    private void assertStringResult(final String expected, final String actual) {
        if (expected == null) {
            assertNull(actual);
        } else {
            assertEquals(expected, actual);
        }
    }
}
