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
        // utility class can be instantiated (JavaBean compatibility)
        assertNotNull(new CharSetUtils());

        // exactly one constructor, and it is public
        final Constructor<?>[] cons = CharSetUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));

        // the class itself is public and non-final
        assertTrue(Modifier.isPublic(CharSetUtils.class.getModifiers()));
        assertFalse(Modifier.isFinal(CharSetUtils.class.getModifiers()));
    }

    @Test
    void testContainsAny_StringString() {
        // null string always returns false
        assertFalse(CharSetUtils.containsAny(null, (String) null));
        assertFalse(CharSetUtils.containsAny(null, ""));

        // empty string always returns false
        assertFalse(CharSetUtils.containsAny("", (String) null));
        assertFalse(CharSetUtils.containsAny("", ""));
        assertFalse(CharSetUtils.containsAny("", "a-e"));

        // non-empty string: returns false when set is null/empty, true when a char matches the range
        assertFalse(CharSetUtils.containsAny("hello", (String) null));
        assertFalse(CharSetUtils.containsAny("hello", ""));
        assertTrue(CharSetUtils.containsAny("hello", "a-e"));   // 'e' is in a-e
        assertTrue(CharSetUtils.containsAny("hello", "l-p"));   // 'l', 'o' are in l-p
    }

    @Test
    void testContainsAny_StringStringarray() {
        // null string always returns false
        assertFalse(CharSetUtils.containsAny(null, (String[]) null));
        assertFalse(CharSetUtils.containsAny(null));
        assertFalse(CharSetUtils.containsAny(null, (String) null));
        assertFalse(CharSetUtils.containsAny(null, "a-e"));

        // empty string always returns false
        assertFalse(CharSetUtils.containsAny("", (String[]) null));
        assertFalse(CharSetUtils.containsAny(""));
        assertFalse(CharSetUtils.containsAny("", (String) null));
        assertFalse(CharSetUtils.containsAny("", "a-e"));

        // null/empty set always returns false
        assertFalse(CharSetUtils.containsAny("hello", (String[]) null));
        assertFalse(CharSetUtils.containsAny("hello"));
        assertFalse(CharSetUtils.containsAny("hello", (String) null));

        // "hello" against various sets
        assertTrue(CharSetUtils.containsAny("hello", "a-e"));   // 'e' matches
        assertTrue(CharSetUtils.containsAny("hello", "el"));    // 'e' and 'l' match
        assertFalse(CharSetUtils.containsAny("hello", "x"));    // no match
        assertTrue(CharSetUtils.containsAny("hello", "e-i"));   // 'e', 'h' match
        assertTrue(CharSetUtils.containsAny("hello", "a-z"));   // all letters match
        assertFalse(CharSetUtils.containsAny("hello", ""));     // empty set never matches
    }

    @Test
    void testCount_StringString() {
        // null string always returns 0
        assertEquals(0, CharSetUtils.count(null, (String) null));
        assertEquals(0, CharSetUtils.count(null, ""));

        // empty string always returns 0
        assertEquals(0, CharSetUtils.count("", (String) null));
        assertEquals(0, CharSetUtils.count("", ""));
        assertEquals(0, CharSetUtils.count("", "a-e"));

        // non-empty string: 0 when set is null/empty, otherwise counts matching chars
        assertEquals(0, CharSetUtils.count("hello", (String) null));
        assertEquals(0, CharSetUtils.count("hello", ""));
        assertEquals(1, CharSetUtils.count("hello", "a-e"));   // only 'e'
        assertEquals(3, CharSetUtils.count("hello", "l-p"));   // 'l', 'l', 'o'
    }

    @Test
    void testCount_StringStringarray() {
        // null string always returns 0
        assertEquals(0, CharSetUtils.count(null, (String[]) null));
        assertEquals(0, CharSetUtils.count(null));
        assertEquals(0, CharSetUtils.count(null, (String) null));
        assertEquals(0, CharSetUtils.count(null, "a-e"));

        // empty string always returns 0
        assertEquals(0, CharSetUtils.count("", (String[]) null));
        assertEquals(0, CharSetUtils.count(""));
        assertEquals(0, CharSetUtils.count("", (String) null));
        assertEquals(0, CharSetUtils.count("", "a-e"));

        // null/empty set always returns 0
        assertEquals(0, CharSetUtils.count("hello", (String[]) null));
        assertEquals(0, CharSetUtils.count("hello"));
        assertEquals(0, CharSetUtils.count("hello", (String) null));

        // "hello" against various sets: counts how many characters belong to the set
        assertEquals(1, CharSetUtils.count("hello", "a-e"));   // 'e'
        assertEquals(3, CharSetUtils.count("hello", "el"));    // 'e', 'l', 'l'
        assertEquals(0, CharSetUtils.count("hello", "x"));     // no match
        assertEquals(2, CharSetUtils.count("hello", "e-i"));   // 'e', 'h'
        assertEquals(5, CharSetUtils.count("hello", "a-z"));   // all five characters
        assertEquals(0, CharSetUtils.count("hello", ""));      // empty set → 0
    }

    @Test
    void testDelete_StringString() {
        // null string returns null
        assertNull(CharSetUtils.delete(null, (String) null));
        assertNull(CharSetUtils.delete(null, ""));

        // empty string always returns ""
        assertEquals("", CharSetUtils.delete("", (String) null));
        assertEquals("", CharSetUtils.delete("", ""));
        assertEquals("", CharSetUtils.delete("", "a-e"));

        // null/empty set: string is returned unchanged
        assertEquals("hello", CharSetUtils.delete("hello", (String) null));
        assertEquals("hello", CharSetUtils.delete("hello", ""));

        // "hello" with sets that match some characters
        assertEquals("hllo", CharSetUtils.delete("hello", "a-e"));   // removes 'e'
        assertEquals("he", CharSetUtils.delete("hello", "l-p"));     // removes 'l', 'l', 'o'
        assertEquals("hello", CharSetUtils.delete("hello", "z"));    // no match, unchanged
    }

    @Test
    void testDelete_StringStringarray() {
        // null string returns null
        assertNull(CharSetUtils.delete(null, (String[]) null));
        assertNull(CharSetUtils.delete(null));
        assertNull(CharSetUtils.delete(null, (String) null));
        assertNull(CharSetUtils.delete(null, "el"));

        // empty string always returns ""
        assertEquals("", CharSetUtils.delete("", (String[]) null));
        assertEquals("", CharSetUtils.delete(""));
        assertEquals("", CharSetUtils.delete("", (String) null));
        assertEquals("", CharSetUtils.delete("", "a-e"));

        // null/empty set: string is returned unchanged
        assertEquals("hello", CharSetUtils.delete("hello", (String[]) null));
        assertEquals("hello", CharSetUtils.delete("hello"));
        assertEquals("hello", CharSetUtils.delete("hello", (String) null));
        assertEquals("hello", CharSetUtils.delete("hello", "xyz"));

        // "hello" with sets that match some or all characters
        assertEquals("ho", CharSetUtils.delete("hello", "el"));       // removes 'e', 'l', 'l'
        assertEquals("", CharSetUtils.delete("hello", "elho"));       // removes all chars
        assertEquals("hello", CharSetUtils.delete("hello", ""));      // empty set, unchanged
        assertEquals("", CharSetUtils.delete("hello", "a-z"));        // all chars removed
        assertEquals("", CharSetUtils.delete("----", "-"));           // removes hyphens
        assertEquals("heo", CharSetUtils.delete("hello", "l"));       // removes both 'l's
    }

    @Test
    void testKeep_StringString() {
        // null string returns null
        assertNull(CharSetUtils.keep(null, (String) null));
        assertNull(CharSetUtils.keep(null, ""));

        // empty string always returns ""
        assertEquals("", CharSetUtils.keep("", (String) null));
        assertEquals("", CharSetUtils.keep("", ""));
        assertEquals("", CharSetUtils.keep("", "a-e"));

        // null/empty set: no characters are kept, result is always ""
        assertEquals("", CharSetUtils.keep("hello", (String) null));
        assertEquals("", CharSetUtils.keep("hello", ""));

        // "hello" with sets that match some or all characters
        assertEquals("", CharSetUtils.keep("hello", "xyz"));        // no match → ""
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));   // all chars kept
        assertEquals("hello", CharSetUtils.keep("hello", "oleh"));  // all chars in set
        assertEquals("ell", CharSetUtils.keep("hello", "el"));      // keeps 'e', 'l', 'l'
    }

    @Test
    void testKeep_StringStringarray() {
        // null string returns null
        assertNull(CharSetUtils.keep(null, (String[]) null));
        assertNull(CharSetUtils.keep(null));
        assertNull(CharSetUtils.keep(null, (String) null));
        assertNull(CharSetUtils.keep(null, "a-e"));

        // empty string always returns ""
        assertEquals("", CharSetUtils.keep("", (String[]) null));
        assertEquals("", CharSetUtils.keep(""));
        assertEquals("", CharSetUtils.keep("", (String) null));
        assertEquals("", CharSetUtils.keep("", "a-e"));

        // null/empty set: no characters are kept, result is always ""
        assertEquals("", CharSetUtils.keep("hello", (String[]) null));
        assertEquals("", CharSetUtils.keep("hello"));
        assertEquals("", CharSetUtils.keep("hello", (String) null));

        // "hello" with various sets
        assertEquals("e", CharSetUtils.keep("hello", "a-e"));       // only 'e' in a-e
        assertEquals("e", CharSetUtils.keep("hello", "a-e"));       // same assertion, confirmed
        assertEquals("ell", CharSetUtils.keep("hello", "el"));      // keeps 'e', 'l', 'l'
        assertEquals("hello", CharSetUtils.keep("hello", "elho"));  // all chars in set
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));   // all chars kept
        assertEquals("----", CharSetUtils.keep("----", "-"));        // keeps hyphens
        assertEquals("ll", CharSetUtils.keep("hello", "l"));        // keeps both 'l's
    }

    @Test
    void testSqueeze_StringString() {
        // null string returns null
        assertNull(CharSetUtils.squeeze(null, (String) null));
        assertNull(CharSetUtils.squeeze(null, ""));

        // empty string always returns ""
        assertEquals("", CharSetUtils.squeeze("", (String) null));
        assertEquals("", CharSetUtils.squeeze("", ""));
        assertEquals("", CharSetUtils.squeeze("", "a-e"));

        // null/empty set: no squeezing applied, string returned unchanged
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
        assertEquals("hello", CharSetUtils.squeeze("hello", ""));

        // "hello"/"helloo" with various sets: consecutive duplicates in-set are collapsed
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"));    // no consecutive dups in a-e
        assertEquals("helo", CharSetUtils.squeeze("hello", "l-p"));     // 'll' → 'l'
        assertEquals("heloo", CharSetUtils.squeeze("helloo", "l"));     // 'll' → 'l', 'oo' not squeezed
        assertEquals("hello", CharSetUtils.squeeze("helloo", "^l"));    // '^l' = not-l, so 'oo' → 'o'
    }

    @Test
    void testSqueeze_StringStringarray() {
        // null string returns null
        assertNull(CharSetUtils.squeeze(null, (String[]) null));
        assertNull(CharSetUtils.squeeze(null));
        assertNull(CharSetUtils.squeeze(null, (String) null));
        assertNull(CharSetUtils.squeeze(null, "el"));

        // empty string always returns ""
        assertEquals("", CharSetUtils.squeeze("", (String[]) null));
        assertEquals("", CharSetUtils.squeeze(""));
        assertEquals("", CharSetUtils.squeeze("", (String) null));
        assertEquals("", CharSetUtils.squeeze("", "a-e"));

        // null/empty set: no squeezing applied, string returned unchanged
        assertEquals("hello", CharSetUtils.squeeze("hello", (String[]) null));
        assertEquals("hello", CharSetUtils.squeeze("hello"));
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"));    // no consecutive dups in a-e

        // "hello"/"fooffooff" with sets: consecutive duplicates in-set are collapsed
        assertEquals("helo", CharSetUtils.squeeze("hello", "el"));          // 'll' → 'l'
        assertEquals("hello", CharSetUtils.squeeze("hello", "e"));          // 'e' has no duplicates
        assertEquals("fofof", CharSetUtils.squeeze("fooffooff", "of"));     // 'oo','ff','oo','ff' each → 1
        assertEquals("fof", CharSetUtils.squeeze("fooooff", "fo"));         // 'oooo' → 'o', 'ff' → 'f'
    }

}
