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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests {@link CharSetUtils}.
 *
 * <p>The second argument of every {@code CharSetUtils} method is a "set" expressed in
 * {@link CharSet} syntax. A few recurring conventions used throughout these tests:</p>
 * <ul>
 *   <li>{@code "a-e"} is an inclusive character <em>range</em> (a, b, c, d, e).</li>
 *   <li>{@code "el"} is an <em>enumeration</em> of individual characters (e and l).</li>
 *   <li>{@code "^l"} is a <em>negation</em> (every character except l).</li>
 *   <li>A {@code null} or empty set is treated as "no characters selected".</li>
 * </ul>
 *
 * <p>Each test is organized into three blocks by the primary string argument:
 * {@code null} input, empty ({@code ""}) input, and the populated input {@code "hello"},
 * so the boundary behavior of every method is easy to follow.</p>
 */
class CharSetUtilsTest extends AbstractLangTest {

    @Test
    @DisplayName("Constructor is public and the class is public but non-final (JavaBean compatibility)")
    void testConstructor() {
        assertNotNull(new CharSetUtils());
        final Constructor<?>[] constructors = CharSetUtils.class.getDeclaredConstructors();
        assertEquals(1, constructors.length, "exactly one constructor is declared");
        assertTrue(Modifier.isPublic(constructors[0].getModifiers()), "the constructor is public");
        assertTrue(Modifier.isPublic(CharSetUtils.class.getModifiers()), "the class is public");
        assertFalse(Modifier.isFinal(CharSetUtils.class.getModifiers()), "the class is not final");
    }

    @Test
    @DisplayName("containsAny(String, String): true only when the string shares a character with the set")
    void testContainsAny_StringString() {
        // null string -> never contains anything, regardless of the set
        assertFalse(CharSetUtils.containsAny(null, (String) null));
        assertFalse(CharSetUtils.containsAny(null, ""));

        // empty string -> never contains anything, regardless of the set
        assertFalse(CharSetUtils.containsAny("", (String) null));
        assertFalse(CharSetUtils.containsAny("", ""));
        assertFalse(CharSetUtils.containsAny("", "a-e"));

        // populated string -> a null or empty set selects nothing, so no match
        assertFalse(CharSetUtils.containsAny("hello", (String) null));
        assertFalse(CharSetUtils.containsAny("hello", ""));
        // "hello" shares 'e' with range a-e, and 'l'/'o' with range l-p
        assertTrue(CharSetUtils.containsAny("hello", "a-e"));
        assertTrue(CharSetUtils.containsAny("hello", "l-p"));
    }

    @Test
    @DisplayName("containsAny(String, String[]): varargs overload behaves like the single-set overload")
    void testContainsAny_StringStringarray() {
        // null string -> never contains anything, for any form of set argument
        assertFalse(CharSetUtils.containsAny(null, (String[]) null));
        assertFalse(CharSetUtils.containsAny(null));
        assertFalse(CharSetUtils.containsAny(null, (String) null));
        assertFalse(CharSetUtils.containsAny(null, "a-e"));

        // empty string -> never contains anything, for any form of set argument
        assertFalse(CharSetUtils.containsAny("", (String[]) null));
        assertFalse(CharSetUtils.containsAny(""));
        assertFalse(CharSetUtils.containsAny("", (String) null));
        assertFalse(CharSetUtils.containsAny("", "a-e"));

        // populated string -> a null/absent/empty set selects nothing, so no match
        assertFalse(CharSetUtils.containsAny("hello", (String[]) null));
        assertFalse(CharSetUtils.containsAny("hello"));
        assertFalse(CharSetUtils.containsAny("hello", (String) null));
        assertTrue(CharSetUtils.containsAny("hello", "a-e"));

        // populated string with various selecting / non-selecting sets
        assertTrue(CharSetUtils.containsAny("hello", "el"));   // 'e' and 'l' are present
        assertFalse(CharSetUtils.containsAny("hello", "x"));   // 'x' is absent
        assertTrue(CharSetUtils.containsAny("hello", "e-i"));  // 'e'/'h' fall in range e-i
        assertTrue(CharSetUtils.containsAny("hello", "a-z"));  // every letter is in range a-z
        assertFalse(CharSetUtils.containsAny("hello", ""));    // empty set selects nothing
    }

    @Test
    @DisplayName("count(String, String): number of characters in the string that belong to the set")
    void testCount_StringString() {
        // null string -> count is 0, regardless of the set
        assertEquals(0, CharSetUtils.count(null, (String) null));
        assertEquals(0, CharSetUtils.count(null, ""));

        // empty string -> count is 0, regardless of the set
        assertEquals(0, CharSetUtils.count("", (String) null));
        assertEquals(0, CharSetUtils.count("", ""));
        assertEquals(0, CharSetUtils.count("", "a-e"));

        // populated string -> a null or empty set selects nothing, so count is 0
        assertEquals(0, CharSetUtils.count("hello", (String) null));
        assertEquals(0, CharSetUtils.count("hello", ""));
        assertEquals(1, CharSetUtils.count("hello", "a-e")); // only 'e' matches
        assertEquals(3, CharSetUtils.count("hello", "l-p")); // 'l', 'l', 'o' match
    }

    @Test
    @DisplayName("count(String, String[]): varargs overload behaves like the single-set overload")
    void testCount_StringStringarray() {
        // null string -> count is 0, for any form of set argument
        assertEquals(0, CharSetUtils.count(null, (String[]) null));
        assertEquals(0, CharSetUtils.count(null));
        assertEquals(0, CharSetUtils.count(null, (String) null));
        assertEquals(0, CharSetUtils.count(null, "a-e"));

        // empty string -> count is 0, for any form of set argument
        assertEquals(0, CharSetUtils.count("", (String[]) null));
        assertEquals(0, CharSetUtils.count(""));
        assertEquals(0, CharSetUtils.count("", (String) null));
        assertEquals(0, CharSetUtils.count("", "a-e"));

        // populated string -> a null/absent/empty set selects nothing, so count is 0
        assertEquals(0, CharSetUtils.count("hello", (String[]) null));
        assertEquals(0, CharSetUtils.count("hello"));
        assertEquals(0, CharSetUtils.count("hello", (String) null));
        assertEquals(1, CharSetUtils.count("hello", "a-e")); // only 'e' matches

        // populated string with various selecting / non-selecting sets
        assertEquals(3, CharSetUtils.count("hello", "el"));  // 'e', 'l', 'l'
        assertEquals(0, CharSetUtils.count("hello", "x"));   // 'x' is absent
        assertEquals(2, CharSetUtils.count("hello", "e-i")); // 'h' and 'e'
        assertEquals(5, CharSetUtils.count("hello", "a-z")); // every character matches
        assertEquals(0, CharSetUtils.count("hello", ""));    // empty set selects nothing
    }

    @Test
    @DisplayName("delete(String, String): removes the characters that belong to the set")
    void testDelete_StringString() {
        // null string -> result is null, regardless of the set
        assertNull(CharSetUtils.delete(null, (String) null));
        assertNull(CharSetUtils.delete(null, ""));

        // empty string -> result is the empty string, regardless of the set
        assertEquals("", CharSetUtils.delete("", (String) null));
        assertEquals("", CharSetUtils.delete("", ""));
        assertEquals("", CharSetUtils.delete("", "a-e"));

        // populated string -> a null/empty/non-matching set leaves it unchanged
        assertEquals("hello", CharSetUtils.delete("hello", (String) null));
        assertEquals("hello", CharSetUtils.delete("hello", ""));
        assertEquals("hllo", CharSetUtils.delete("hello", "a-e")); // 'e' removed
        assertEquals("he", CharSetUtils.delete("hello", "l-p"));   // 'l', 'l', 'o' removed
        assertEquals("hello", CharSetUtils.delete("hello", "z"));  // 'z' is absent
    }

    @Test
    @DisplayName("delete(String, String[]): varargs overload behaves like the single-set overload")
    void testDelete_StringStringarray() {
        // null string -> result is null, for any form of set argument
        assertNull(CharSetUtils.delete(null, (String[]) null));
        assertNull(CharSetUtils.delete(null));
        assertNull(CharSetUtils.delete(null, (String) null));
        assertNull(CharSetUtils.delete(null, "el"));

        // empty string -> result is the empty string, for any form of set argument
        assertEquals("", CharSetUtils.delete("", (String[]) null));
        assertEquals("", CharSetUtils.delete(""));
        assertEquals("", CharSetUtils.delete("", (String) null));
        assertEquals("", CharSetUtils.delete("", "a-e"));

        // populated string -> a null/absent/empty/non-matching set leaves it unchanged
        assertEquals("hello", CharSetUtils.delete("hello", (String[]) null));
        assertEquals("hello", CharSetUtils.delete("hello"));
        assertEquals("hello", CharSetUtils.delete("hello", (String) null));
        assertEquals("hello", CharSetUtils.delete("hello", "xyz"));

        // populated string with various deleting sets
        assertEquals("ho", CharSetUtils.delete("hello", "el"));      // 'e' and 'l' removed
        assertEquals("", CharSetUtils.delete("hello", "elho"));      // all characters removed
        assertEquals("hello", CharSetUtils.delete("hello", ""));     // empty set deletes nothing
        assertEquals("", CharSetUtils.delete("hello", "a-z"));       // whole range removed
        assertEquals("", CharSetUtils.delete("----", "-"));          // every '-' removed
        assertEquals("heo", CharSetUtils.delete("hello", "l"));      // both 'l' removed
    }

    @Test
    @DisplayName("keep(String, String): keeps only the characters that belong to the set")
    void testKeep_StringString() {
        // null string -> result is null, regardless of the set
        assertNull(CharSetUtils.keep(null, (String) null));
        assertNull(CharSetUtils.keep(null, ""));

        // empty string -> result is the empty string, regardless of the set
        assertEquals("", CharSetUtils.keep("", (String) null));
        assertEquals("", CharSetUtils.keep("", ""));
        assertEquals("", CharSetUtils.keep("", "a-e"));

        // populated string -> a null/empty/non-matching set keeps nothing
        assertEquals("", CharSetUtils.keep("hello", (String) null));
        assertEquals("", CharSetUtils.keep("hello", ""));
        assertEquals("", CharSetUtils.keep("hello", "xyz"));
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));  // every character kept
        assertEquals("hello", CharSetUtils.keep("hello", "oleh")); // set covers all of "hello"
        assertEquals("ell", CharSetUtils.keep("hello", "el"));     // only 'e' and 'l' kept
    }

    @Test
    @DisplayName("keep(String, String[]): varargs overload behaves like the single-set overload")
    void testKeep_StringStringarray() {
        // null string -> result is null, for any form of set argument
        assertNull(CharSetUtils.keep(null, (String[]) null));
        assertNull(CharSetUtils.keep(null));
        assertNull(CharSetUtils.keep(null, (String) null));
        assertNull(CharSetUtils.keep(null, "a-e"));

        // empty string -> result is the empty string, for any form of set argument
        assertEquals("", CharSetUtils.keep("", (String[]) null));
        assertEquals("", CharSetUtils.keep(""));
        assertEquals("", CharSetUtils.keep("", (String) null));
        assertEquals("", CharSetUtils.keep("", "a-e"));

        // populated string -> a null/absent/empty set keeps nothing
        assertEquals("", CharSetUtils.keep("hello", (String[]) null));
        assertEquals("", CharSetUtils.keep("hello"));
        assertEquals("", CharSetUtils.keep("hello", (String) null));
        assertEquals("e", CharSetUtils.keep("hello", "a-e")); // only 'e' is in range a-e

        // populated string with various keeping sets
        assertEquals("e", CharSetUtils.keep("hello", "a-e"));        // only 'e' kept
        assertEquals("ell", CharSetUtils.keep("hello", "el"));       // 'e' and both 'l' kept
        assertEquals("hello", CharSetUtils.keep("hello", "elho"));   // set covers all of "hello"
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));    // every character kept
        assertEquals("----", CharSetUtils.keep("----", "-"));        // every '-' kept
        assertEquals("ll", CharSetUtils.keep("hello", "l"));         // only the two 'l' kept
    }

    @Test
    @DisplayName("squeeze(String, String): collapses runs of set characters to a single character")
    void testSqueeze_StringString() {
        // null string -> result is null, regardless of the set
        assertNull(CharSetUtils.squeeze(null, (String) null));
        assertNull(CharSetUtils.squeeze(null, ""));

        // empty string -> result is the empty string, regardless of the set
        assertEquals("", CharSetUtils.squeeze("", (String) null));
        assertEquals("", CharSetUtils.squeeze("", ""));
        assertEquals("", CharSetUtils.squeeze("", "a-e"));

        // populated string -> a null/empty set, or one that misses the repeated char, changes nothing
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
        assertEquals("hello", CharSetUtils.squeeze("hello", ""));
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e")); // 'l' not in set, run kept
        assertEquals("helo", CharSetUtils.squeeze("hello", "l-p"));  // "ll" squeezed to "l"
        assertEquals("heloo", CharSetUtils.squeeze("helloo", "l"));  // only the "ll" run squeezed
        assertEquals("hello", CharSetUtils.squeeze("helloo", "^l")); // negated set squeezes "oo"
    }

    @Test
    @DisplayName("squeeze(String, String[]): varargs overload behaves like the single-set overload")
    void testSqueeze_StringStringarray() {
        // null string -> result is null, for any form of set argument
        assertNull(CharSetUtils.squeeze(null, (String[]) null));
        assertNull(CharSetUtils.squeeze(null));
        assertNull(CharSetUtils.squeeze(null, (String) null));
        assertNull(CharSetUtils.squeeze(null, "el"));

        // empty string -> result is the empty string, for any form of set argument
        assertEquals("", CharSetUtils.squeeze("", (String[]) null));
        assertEquals("", CharSetUtils.squeeze(""));
        assertEquals("", CharSetUtils.squeeze("", (String) null));
        assertEquals("", CharSetUtils.squeeze("", "a-e"));

        // populated string -> a null/absent/empty set, or one missing the repeated char, changes nothing
        assertEquals("hello", CharSetUtils.squeeze("hello", (String[]) null));
        assertEquals("hello", CharSetUtils.squeeze("hello"));
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e")); // 'l' not in set, run kept

        // populated string with various squeezing sets
        assertEquals("helo", CharSetUtils.squeeze("hello", "el"));        // "ll" squeezed to "l"
        assertEquals("hello", CharSetUtils.squeeze("hello", "e"));        // no run of 'e' to squeeze
        assertEquals("fofof", CharSetUtils.squeeze("fooffooff", "of"));   // each "oo"/"ff" run collapsed
        assertEquals("fof", CharSetUtils.squeeze("fooooff", "fo"));       // "oooo" and "ff" collapsed
    }

}
