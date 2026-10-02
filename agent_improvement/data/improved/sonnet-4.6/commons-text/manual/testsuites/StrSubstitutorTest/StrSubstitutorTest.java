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

package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.lang3.mutable.MutableObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link StrSubstitutor}.
 *
 * @deprecated This class will be removed in 2.0.
 */
class StrSubstitutorTest {

    private Map<String, String> values;

    /**
     * Verifies that the substitutor performs no replacement for the given template,
     * or handles null input by returning null / false across all replace overloads.
     */
    private void doTestNoReplace(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);

        if (replaceTemplate == null) {
            assertNull(sub.replace((String) null));
            assertNull(sub.replace((String) null, 0, 100));
            assertNull(sub.replace((char[]) null));
            assertNull(sub.replace((char[]) null, 0, 100));
            assertNull(sub.replace((StringBuffer) null));
            assertNull(sub.replace((StringBuffer) null, 0, 100));
            assertNull(sub.replace((StrBuilder) null));
            assertNull(sub.replace((StrBuilder) null, 0, 100));
            assertNull(sub.replace((Object) null));
            assertFalse(sub.replaceIn((StringBuffer) null));
            assertFalse(sub.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(sub.replaceIn((StrBuilder) null));
            assertFalse(sub.replaceIn((StrBuilder) null, 0, 100));
        } else {
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));
            final StrBuilder bld = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(bld));
            assertEquals(replaceTemplate, bld.toString());
        }
    }

    /**
     * Verifies replacement using the shared {@code values} map and the default substitutor.
     *
     * @param expectedResult         the fully-substituted result expected for the full template
     * @param replaceTemplate        the template string containing variable references
     * @param testSubstringVariants  whether to also test replacement of an inner substring
     *                               (the template without its first and last characters)
     */
    private void doTestReplace(final String expectedResult, final String replaceTemplate,
            final boolean testSubstringVariants) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, testSubstringVariants);
    }

    /**
     * Verifies replacement across all supported input types: String, char[], StringBuffer,
     * StringBuilder, StrBuilder, and Object; also validates in-place replaceIn variants.
     * When {@code testSubstringVariants} is true the test also covers the offset+length overloads.
     *
     * @param sub                    the configured substitutor to use
     * @param expectedResult         the fully-substituted result expected for the full template
     * @param replaceTemplate        the template string containing variable references
     * @param testSubstringVariants  whether to also test replacement of an inner substring
     */
    private void doTestReplace(final StrSubstitutor sub, final String expectedResult, final String replaceTemplate,
            final boolean testSubstringVariants) {
        // expectedShortResult is the result when only the inner portion of the template is substituted
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // replace using String
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (testSubstringVariants) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // replace using char[]
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (testSubstringVariants) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // replace using StringBuffer
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (testSubstringVariants) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // replace using StringBuilder
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (testSubstringVariants) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // replace using StrBuilder
        StrBuilder bld = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (testSubstringVariants) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // replace using Object (toString() is used as the template)
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // replaceIn mutates a StringBuffer in place
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf));
        assertEquals(expectedResult, buf.toString());
        if (testSubstringVariants) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            assertEquals(expectedResult, buf.toString());  // expect full result as remainder is untouched
        }

        // replaceIn mutates a StringBuilder in place
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (testSubstringVariants) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString());  // expect full result as remainder is untouched
        }

        // replaceIn mutates a StrBuilder in place
        bld = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (testSubstringVariants) {
            bld = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString());  // expect full result as remainder is untouched
        }
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        values.put("animal", "quick brown fox");
        values.put("target", "lazy dog");
    }

    @AfterEach
    public void tearDown() throws Exception {
        values = null;
    }

    /**
     * Tests constructor with map, custom prefix/suffix, escape character, and value delimiter.
     */
    @Test
    void testConstructorMapFull() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        StrSubstitutor sub = new StrSubstitutor(map, "<", ">", '!');
        assertEquals("Hi < commons", sub.replace("Hi !< <name>"));
        sub = new StrSubstitutor(map, "<", ">", '!', "||");
        assertEquals("Hi < commons", sub.replace("Hi !< <name2||commons>"));
    }

    /**
     * Tests constructor with map and custom prefix/suffix (uses default escape character).
     */
    @Test
    void testConstructorMapPrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        final StrSubstitutor sub = new StrSubstitutor(map, "<", ">");
        assertEquals("Hi < commons", sub.replace("Hi $< <name>"));
    }

    /**
     * Tests no-arg constructor leaves variables unresolved (no lookup registered).
     */
    @Test
    void testConstructorNoArgs() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertEquals("Hi ${name}", sub.replace("Hi ${name}"));
    }

    /**
     * Tests construction via a {@link StrLookup} and the CharSequence replace overload
     * that accepts an offset and length; also verifies the default escape character.
     */
    @Test
    void testConstructorWithStrLookupAndReplaceCharSequenceWithOffsets() {
        final Map<String, CharacterPredicates> map = new HashMap<>();
        final StrLookup<CharacterPredicates> strLookup = StrLookup.mapLookup(map);
        final StrSubstitutor strSubstitutor = new StrSubstitutor(strLookup);

        assertNull(strSubstitutor.replace((CharSequence) null, 0, 0));
        assertEquals('$', strSubstitutor.getEscapeChar());
    }

    /**
     * Tests that a cyclic variable reference is detected and throws {@link IllegalStateException}.
     * The cycle should be detected and cause an exception to be thrown.
     */
    @Test
    void testCyclicReplacement() {
        final Map<String, String> map = new HashMap<>();
        map.put("animal", "${critter}");
        map.put("target", "${pet}");
        map.put("pet", "${petCharacteristic} dog");
        map.put("petCharacteristic", "lazy");
        map.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        map.put("critterSpeed", "quick");
        map.put("critterColor", "brown");
        map.put("critterType", "${animal}");
        final StrSubstitutor sub = new StrSubstitutor(map);
        assertThrows(IllegalStateException.class, () -> sub.replace("The ${animal} jumps over the ${target}."));

        // cycle detection must also work when a default value is present in the cycle
        map.put("critterType", "${animal:-fox}");
        assertThrows(IllegalStateException.class,
                () -> new StrSubstitutor(map).replace("The ${animal} jumps over the ${target}."));
    }

    /**
     * Tests that various value-delimiter strings (e.g., ":-", "?:", "||", "!") all correctly
     * trigger default-value resolution for undefined variables, and that disabling the delimiter
     * leaves the variable reference intact.
     */
    @Test
    void testDefaultValueDelimiters() {
        final Map<String, String> map = new HashMap<>();
        map.put("animal", "fox");
        map.put("target", "dog");

        StrSubstitutor sub = new StrSubstitutor(map, "${", "}", '$');
        assertEquals("The fox jumps over the lazy dog. 1234567890.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number:-1234567890}."));

        sub = new StrSubstitutor(map, "${", "}", '$', "?:");
        assertEquals("The fox jumps over the lazy dog. 1234567890.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number?:1234567890}."));

        sub = new StrSubstitutor(map, "${", "}", '$', "||");
        assertEquals("The fox jumps over the lazy dog. 1234567890.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number||1234567890}."));

        sub = new StrSubstitutor(map, "${", "}", '$', "!");
        assertEquals("The fox jumps over the lazy dog. 1234567890.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));

        // Setting an empty delimiter string then nulling the matcher disables default-value resolution
        sub = new StrSubstitutor(map, "${", "}", '$', "");
        sub.setValueDelimiterMatcher(null);
        assertEquals("The fox jumps over the lazy dog. ${undefined.number!1234567890}.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));

        // Explicitly nulling the matcher also disables default-value resolution
        sub = new StrSubstitutor(map, "${", "}", '$');
        sub.setValueDelimiterMatcher(null);
        assertEquals("The fox jumps over the lazy dog. ${undefined.number!1234567890}.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));
    }

    /**
     * Tests that when substitution in variable values is disabled, variable references
     * inside values are left verbatim instead of being recursively resolved.
     */
    @Test
    void testDisableSubstitutionInValues() {
        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setDisableSubstitutionInValues(true);
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");
        doTestReplace(sub, "The ${critter} jumps over the ${pet}.", "The ${animal} jumps over the ${target}.", true);
    }

    /**
     * Tests getting and setting the escape character.
     */
    @Test
    void testGetSetEscape() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertEquals('$', sub.getEscapeChar());
        sub.setEscapeChar('<');
        assertEquals('<', sub.getEscapeChar());
    }

    /**
     * Tests getting and setting the variable prefix (via character, string, and matcher overloads),
     * including validation that a null prefix throws {@link IllegalArgumentException}.
     */
    @Test
    void testGetSetPrefix() {
        final StrSubstitutor sub = new StrSubstitutor();
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);
        sub.setVariablePrefix('<');
        StrMatcherTest.assertStrMatcherPrefixImpl("CharMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        sub.setVariablePrefix("<<");
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);
        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefix((String) null));
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setVariablePrefixMatcher(matcher);
        assertSame(matcher, sub.getVariablePrefixMatcher());
        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefixMatcher((StrMatcher) null));
        assertSame(matcher, sub.getVariablePrefixMatcher());
    }

    /**
     * Tests getting and setting the variable suffix (via character, string, and matcher overloads),
     * including validation that a null suffix throws {@link IllegalArgumentException}.
     */
    @Test
    void testGetSetSuffix() {
        final StrSubstitutor sub = new StrSubstitutor();
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);
        sub.setVariableSuffix('<');
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("CharMatcher", sub);

        sub.setVariableSuffix("<<");
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);
        assertThrows(IllegalArgumentException.class, () -> sub.setVariableSuffix((String) null));
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setVariableSuffixMatcher(matcher);
        assertSame(matcher, sub.getVariableSuffixMatcher());
        assertThrows(IllegalArgumentException.class, () -> sub.setVariableSuffixMatcher((StrMatcher) null));
        assertSame(matcher, sub.getVariableSuffixMatcher());
    }

    /**
     * Tests getting and setting the value delimiter (via character, string, and matcher overloads),
     * and verifies that setting null disables default-value resolution.
     */
    @Test
    void testGetSetValueDelimiter() {
        final StrSubstitutor sub = new StrSubstitutor();
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);
        sub.setValueDelimiter(':');
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);

        sub.setValueDelimiter("||");
        StrMatcherTest.assertStrMatcherPrefixImpl("StringMatcher", sub);
        StrMatcherTest.assertStrMatcherSuffixImpl("StringMatcher", sub);
        sub.setValueDelimiter((String) null);
        assertNull(sub.getValueDelimiterMatcher());

        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setValueDelimiterMatcher(matcher);
        assertSame(matcher, sub.getValueDelimiterMatcher());
        sub.setValueDelimiterMatcher((StrMatcher) null);
        assertNull(sub.getValueDelimiterMatcher());
    }

    /**
     * Test for LANG-1055: StrSubstitutor.replaceSystemProperties does not work consistently.
     */
    @Test
    void testLANG1055() {
        System.setProperty("test_key", "test_value");

        final String expected = StrSubstitutor.replace("test_key=${test_key}", System.getProperties());
        final String actual = StrSubstitutor.replaceSystemProperties("test_key=${test_key}");
        assertEquals(expected, actual);
    }

    /**
     * Tests two adjacent variable references at the end of the template.
     */
    @Test
    void testReplaceAdjacentAtEnd() {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertEquals("Amount is GBP12.50", sub.replace("Amount is ${code}${amount}"));
    }

    /**
     * Tests two adjacent variable references at the start of the template.
     */
    @Test
    void testReplaceAdjacentAtStart() {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertEquals("GBP12.50 charged", sub.replace("${code}${amount} charged"));
    }

    /**
     * Tests that modifying the backing map after construction affects subsequent replacements.
     */
    @Test
    void testReplaceChangedMap() {
        final StrSubstitutor sub = new StrSubstitutor(values);
        values.put("target", "moon");
        assertEquals("The quick brown fox jumps over the moon.",
                sub.replace("The ${animal} jumps over the ${target}."));
    }

    /**
     * Tests complex escaping: a variable reference preceded by the escape character is left as
     * a literal {@code ${...}} in the output while adjacent real variables are still resolved.
     */
    @Test
    void testReplaceComplexEscaping() {
        doTestReplace("The ${quick brown fox} jumps over the lazy dog.",
                "The $${${animal}} jumps over the ${target}.", true);
        doTestReplace("The ${quick brown fox} jumps over the lazy dog. ${1234567890}.",
                "The $${${animal}} jumps over the ${target}. $${${undefined.number:-1234567890}}.", true);
    }

    /**
     * Tests that an empty template string produces an empty result (no replacement).
     */
    @Test
    void testReplaceEmpty() {
        doTestNoReplace("");
    }

    /**
     * Tests that an empty variable name (${}) is left intact, and that an empty name with a
     * default value (${:-animal}) resolves to the default.
     */
    @Test
    void testReplaceEmptyKeys() {
        doTestReplace("The ${} jumps over the lazy dog.", "The ${} jumps over the ${target}.", true);
        doTestReplace("The animal jumps over the lazy dog.", "The ${:-animal} jumps over the ${target}.", true);
    }

    /**
     * Tests that a variable reference preceded by the escape character is not substituted.
     */
    @Test
    void testReplaceEscaping() {
        doTestReplace("The ${animal} jumps over the lazy dog.", "The $${animal} jumps over the ${target}.", true);
    }

    /**
     * Tests that an incomplete prefix (missing the leading '$') is treated as literal text.
     */
    @Test
    void testReplaceIncompletePrefix() {
        doTestReplace("The {animal} jumps over the lazy dog.", "The {animal} jumps over the ${target}.", true);
    }

    /**
     * Tests that replaceIn(StringBuffer) returns false when the buffer contains no matching
     * variables, and verifies the configured escape character and preserveEscapes default.
     * Uses a deliberately unusual delimiter so it never conflicts with ordinary text.
     */
    @Test
    void testReplaceInTakingStringBufferWithNonNull() {
        // prefix == suffix == customDelimiter ensures the delimiter is unlikely to appear in content
        final String customDelimiter = "WV@i#y?N*[";
        final char escapeChar = '*';
        final StrSubstitutor strSubstitutor =
                new StrSubstitutor(new HashMap<>(), customDelimiter, customDelimiter, escapeChar);

        assertFalse(strSubstitutor.isPreserveEscapes());
        assertFalse(strSubstitutor.replaceIn(new StringBuffer(customDelimiter)));
        assertEquals(escapeChar, strSubstitutor.getEscapeChar());
    }

    /**
     * Tests that replaceIn(StringBuilder) returns false when no variable delimiters are present,
     * and verifies the configured escape character.
     */
    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        final StrLookup<String> strLookup = StrLookup.systemPropertiesLookup();
        final String customDelimiter = "b<H";
        final char escapeChar = '\'';
        final StrSubstitutor strSubstitutor = new StrSubstitutor(strLookup, customDelimiter, customDelimiter, escapeChar);
        final StringBuilder stringBuilder = new StringBuilder((CharSequence) customDelimiter);

        assertEquals(escapeChar, strSubstitutor.getEscapeChar());
        assertFalse(strSubstitutor.replaceIn(stringBuilder));
    }

    /**
     * Tests that replaceIn(StringBuilder) returns false when given a null StringBuilder.
     */
    @Test
    void testReplaceInTakingStringBuilderWithNull() {
        final Map<String, Object> map = new HashMap<>();
        final StrSubstitutor strSubstitutor = new StrSubstitutor(map, "", "", 'T', "K+<'f");

        assertFalse(strSubstitutor.replaceIn((StringBuilder) null));
    }

    /**
     * Tests that replaceIn(StringBuilder, int, int) returns false for a null StringBuilder
     * with arbitrary offset and length arguments, and verifies the configured escape character
     * and preserveEscapes default.
     */
    @Test
    void testReplaceInTakingTwoAndThreeIntsReturningFalse() {
        final Map<String, Object> hashMap = new HashMap<>();
        final StrLookup<Object> strLookupMapStrLookup = StrLookup.mapLookup(hashMap);
        final StrMatcher strMatcher = StrMatcher.tabMatcher();
        final StrSubstitutor strSubstitutor =
                new StrSubstitutor(strLookupMapStrLookup, strMatcher, strMatcher, 'b', strMatcher);

        assertFalse(strSubstitutor.replaceIn((StringBuilder) null, 1315, -1369));
        assertEquals('b', strSubstitutor.getEscapeChar());
        assertFalse(strSubstitutor.isPreserveEscapes());
    }

    /**
     * Tests that substitution can resolve a variable whose name itself contains another variable,
     * and that changing the lookup map between calls updates results immediately.
     */
    @Test
    void testReplaceInVariable() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);
        assertEquals(
                "The mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));
        values.put("species", "1");
        assertEquals(
                "The fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));
        assertEquals(
                "The fox jumps over the lazy dog.",
                sub.replace("The ${unknown.animal.${unknown.species:-1}:-fox} "
                        + "jumps over the ${unknow.target:-lazy dog}."));
    }

    /**
     * Tests that substitution in variable names is disabled by default, leaving nested
     * references like {@code ${animal.${species}}} unresolved.
     */
    @Test
    void testReplaceInVariableDisabled() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertEquals(
                "The ${animal.${species}} jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));
        assertEquals(
                "The ${animal.${species:-1}} jumps over the lazy dog.",
                sub.replace("The ${animal.${species:-1}} jumps over the ${target}."));
    }

    /**
     * Tests recursive substitution in variable names across multiple levels of nesting.
     */
    @Test
    void testReplaceInVariableRecursive() {
        values.put("animal.2", "brown fox");
        values.put("animal.1", "white mouse");
        values.put("color", "white");
        values.put("species.white", "1");
        values.put("species.brown", "2");
        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);
        assertEquals(
                "The white mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${color}}} jumps over the ${target}."));
        assertEquals(
                "The brown fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${unknownColor:-brown}}} jumps over the ${target}."));
    }

    /**
     * Tests that text with no variable prefix is returned unchanged.
     */
    @Test
    void testReplaceNoPrefixNoSuffix() {
        doTestReplace("The animal jumps over the lazy dog.", "The animal jumps over the ${target}.", true);
    }

    /**
     * Tests that a suffix without a matching prefix is treated as literal text.
     */
    @Test
    void testReplaceNoPrefixSuffix() {
        doTestReplace("The animal} jumps over the lazy dog.", "The animal} jumps over the ${target}.", true);
    }

    /**
     * Tests that text containing no variables is returned unchanged.
     */
    @Test
    void testReplaceNoVariables() {
        doTestNoReplace("The balloon arrived.");
    }

    /**
     * Tests that null input returns null across all replace and replaceIn overloads.
     */
    @Test
    void testReplaceNull() {
        doTestNoReplace(null);
    }

    /**
     * Tests partial-string replacement: only the specified offset+length portion of the source
     * is processed; variables outside that range are left as-is.
     */
    @Test
    void testReplacePartialString_noReplace() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertEquals("${animal} jumps", sub.replace("The ${animal} jumps over the ${target}.", 4, 15));
    }

    /**
     * Tests that an unclosed prefix (no closing suffix) is treated as literal text.
     */
    @Test
    void testReplacePrefixNoSuffix() {
        doTestReplace("The ${animal jumps over the ${target} lazy dog.",
                "The ${animal jumps over the ${target} ${target}.", true);
    }

    /**
     * Tests multi-level recursive variable substitution where variable values themselves
     * contain variable references, including default-value fallbacks in recursive chains.
     */
    @Test
    void testReplaceRecursive() {
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");
        doTestReplace("The quick brown fox jumps over the lazy dog.", "The ${animal} jumps over the ${target}.", true);

        // default value is also expanded recursively
        values.put("pet", "${petCharacteristicUnknown:-lazy} dog");
        doTestReplace("The quick brown fox jumps over the lazy dog.", "The ${animal} jumps over the ${target}.", true);
    }

    /**
     * Tests simple two-variable replacement using the default {@code ${...}} syntax.
     */
    @Test
    void testReplaceSimple() {
        doTestReplace("The quick brown fox jumps over the lazy dog.", "The ${animal} jumps over the ${target}.", true);
    }

    /**
     * Tests replacement when the entire template is a single variable reference.
     */
    @Test
    void testReplaceSolo() {
        doTestReplace("quick brown fox", "${animal}", false);
    }

    /**
     * Tests that escaping a lone variable reference results in the literal {@code ${animal}}.
     */
    @Test
    void testReplaceSoloEscaping() {
        doTestReplace("${animal}", "$${animal}", false);
    }

    /**
     * Tests that replace(CharSequence) returns null when the input is null,
     * and verifies default preserveEscapes and escape character values when
     * constructed with a null resolver.
     */
    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StrSubstitutor strSubstitutor = new StrSubstitutor((StrLookup<?>) null);

        assertNull(strSubstitutor.replace((CharSequence) null));
        assertFalse(strSubstitutor.isPreserveEscapes());
        assertEquals('$', strSubstitutor.getEscapeChar());
    }

    /**
     * Tests that the static replace(Object, Properties) method throws NullPointerException
     * when the source object is null.
     */
    @Test
    void testReplaceTakingThreeArgumentsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StrSubstitutor.replace(null, (Properties) null));
    }

    /**
     * Tests that output identical to the input is produced when an escape sequence resolves
     * to a variable-like string (the substituted value itself looks like a variable reference).
     */
    @Test
    void testReplaceToIdentical() {
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");
        doTestReplace("The ${animal} jumps.", "The ${animal} jumps.", true);
    }

    /**
     * Tests that unknown variable keys are left as-is, and that a default value
     * is used when specified with the {@code :-} delimiter.
     */
    @Test
    void testReplaceUnknownKey() {
        doTestReplace("The ${person} jumps over the lazy dog.", "The ${person} jumps over the ${target}.", true);
        doTestReplace("The ${person} jumps over the lazy dog. 1234567890.",
                "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.", true);
    }

    /**
     * Tests boundary patterns that should produce no replacement (e.g., empty variable names,
     * incomplete delimiters, whitespace-only names, nested unclosed prefixes).
     */
    @Test
    void testReplaceWeirdPattens() {
        doTestNoReplace("");
        doTestNoReplace("${}");
        doTestNoReplace("${ }");
        doTestNoReplace("${\t}");
        doTestNoReplace("${\n}");
        doTestNoReplace("${\b}");
        doTestNoReplace("${");
        doTestNoReplace("$}");
        doTestNoReplace("}");
        doTestNoReplace("${}$");
        doTestNoReplace("${${");
        doTestNoReplace("${${}}");
        doTestNoReplace("${$${}}");
        doTestNoReplace("${$$${}}");
        doTestNoReplace("${$$${$}}");
        doTestNoReplace("${${}}");
        doTestNoReplace("${${ }}");
    }

    /**
     * Tests the protected resolveVariable hook: a subclass can intercept variable resolution,
     * validate the arguments it receives, and return a custom value.
     */
    @Test
    void testResolveVariable() {
        final StrBuilder builder = new StrBuilder("Hi ${name}!");
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        final StrSubstitutor sub = new StrSubstitutor(map) {
            @Override
            protected String resolveVariable(final String variableName, final StrBuilder buf, final int startPos,
                    final int endPos) {
                assertEquals("name", variableName);
                assertSame(builder, buf);
                assertEquals(3, startPos);
                assertEquals(10, endPos);
                return "jakarta";
            }
        };
        sub.replaceIn(builder);
        assertEquals("Hi jakarta!", builder.toString());
    }

    /**
     * Tests the static replace overload that supports the same string for both prefix and suffix.
     */
    @Test
    void testSamePrefixAndSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("greeting", "Hello");
        map.put(" there ", "XXX");
        map.put("name", "commons");
        assertEquals("Hi commons!", StrSubstitutor.replace("Hi @name@!", map, "@", "@"));
        assertEquals("Hello there commons!", StrSubstitutor.replace("@greeting@ there @name@!", map, "@", "@"));
    }

    /**
     * Tests the static replace(Object, Map) convenience method.
     */
    @Test
    void testStaticReplace() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEquals("Hi commons!", StrSubstitutor.replace("Hi ${name}!", map));
    }

    /**
     * Tests the static replace(Object, Map, String, String) convenience method with custom prefix/suffix.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEquals("Hi commons!", StrSubstitutor.replace("Hi <name>!", map, "<", ">"));
    }

    /**
     * Tests replacement of system property placeholders via the static replaceSystemProperties method.
     */
    @Test
    void testStaticReplaceSystemProperties() {
        final StrBuilder buf = new StrBuilder();
        buf.append("Hi ").append(SystemProperties.getUserName());
        buf.append(", you are working with ");
        buf.append(SystemProperties.getOsName());
        buf.append(", your home directory is ");
        buf.append(SystemProperties.getUserHome()).append('.');
        assertEquals(buf.toString(), StrSubstitutor.replaceSystemProperties("Hi ${user.name}, you are "
            + "working with ${os.name}, your home "
            + "directory is ${user.home}."));
    }

    /**
     * Tests that replace(Object, Properties) resolves keys that exist only in the default
     * Properties layer (i.e., the properties passed as defaults to the Properties constructor).
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String org = "${doesnotwork}";
        System.setProperty("doesnotwork", "It works!");

        // Properties(System.getProperties()) creates a Properties with system props as defaults
        final Properties props = new Properties(System.getProperties());

        assertEquals("It works!", StrSubstitutor.replace(org, props));
    }

    /**
     * Tests that setting preserveEscapes to true keeps the escape character in the output,
     * while the default (false) removes it.
     */
    @Test
    void testSubstitutePreserveEscape() {
        final String org = "${not-escaped} $${escaped}";
        final Map<String, String> map = new HashMap<>();
        map.put("not-escaped", "value");

        final StrSubstitutor sub = new StrSubstitutor(map, "${", "}", '$');
        assertFalse(sub.isPreserveEscapes());
        assertEquals("value ${escaped}", sub.replace(org));

        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEquals("value $${escaped}", sub.replace(org));
    }

}
