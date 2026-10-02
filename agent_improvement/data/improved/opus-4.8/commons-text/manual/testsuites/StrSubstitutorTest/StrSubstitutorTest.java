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

    /** The standard template used by many tests; references the {@code animal} and {@code target} variables. */
    private static final String ANIMAL_TARGET_TEMPLATE = "The ${animal} jumps over the ${target}.";

    /** The result of resolving {@link #ANIMAL_TARGET_TEMPLATE} against the default {@link #values} map. */
    private static final String ANIMAL_TARGET_RESULT = "The quick brown fox jumps over the lazy dog.";

    /** Variable values shared by the tests; reset before each test by {@link #setUp()}. */
    private Map<String, String> values;

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
     * Asserts that the given template is returned unchanged by every {@code replace}/{@code replaceIn} overload,
     * because it contains no resolvable variables.
     *
     * @param replaceTemplate the template that should pass through untouched; {@code null} exercises the
     *                        null-input contract of every overload instead.
     */
    private void doTestNoReplace(final String replaceTemplate) {
        final StrSubstitutor sub = new StrSubstitutor(values);

        if (replaceTemplate == null) {
            // Every overload must accept null and return null (or false for the in-place variants).
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
            // replace() returns the template verbatim, and replaceIn() reports "not altered" and leaves it intact.
            assertEquals(replaceTemplate, sub.replace(replaceTemplate));
            final StrBuilder builder = new StrBuilder(replaceTemplate);
            assertFalse(sub.replaceIn(builder));
            assertEquals(replaceTemplate, builder.toString());
        }
    }

    /**
     * Asserts that the default substitutor (built from {@link #values}) turns {@code replaceTemplate} into
     * {@code expectedResult} across every input type.
     */
    private void doTestReplace(final String expectedResult, final String replaceTemplate, final boolean substring) {
        final StrSubstitutor sub = new StrSubstitutor(values);
        doTestReplace(sub, expectedResult, replaceTemplate, substring);
    }

    /**
     * Asserts that {@code sub} turns {@code replaceTemplate} into {@code expectedResult} when fed through every
     * supported source type: {@code String}, {@code char[]}, {@code StringBuffer}, {@code StringBuilder},
     * {@code StrBuilder} and arbitrary {@code Object}, plus the in-place {@code replaceIn} variants.
     *
     * @param substring when {@code true}, also verifies the (offset, length) overloads using a window that drops
     *                  the first and last character of the template.
     */
    private void doTestReplace(final StrSubstitutor sub, final String expectedResult, final String replaceTemplate,
            final boolean substring) {
        // The substring overloads process the template without its first and last character, so the expected
        // output is likewise the result without its first and last character.
        final String expectedShortResult = expectedResult.substring(1, expectedResult.length() - 1);

        // replace using String
        assertEquals(expectedResult, sub.replace(replaceTemplate));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(replaceTemplate, 1, replaceTemplate.length() - 2));
        }

        // replace using char[]
        final char[] chars = replaceTemplate.toCharArray();
        assertEquals(expectedResult, sub.replace(chars));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(chars, 1, chars.length - 2));
        }

        // replace using StringBuffer
        StringBuffer stringBuffer = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(stringBuffer));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(stringBuffer, 1, stringBuffer.length() - 2));
        }

        // replace using StringBuilder
        StringBuilder stringBuilder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(stringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        // replace using StrBuilder
        StrBuilder strBuilder = new StrBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(strBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(strBuilder, 1, strBuilder.length() - 2));
        }

        // replace using Object (its toString() returns the template)
        final MutableObject<String> templateAsObject = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(templateAsObject));

        // replace in StringBuffer (in place)
        stringBuffer = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuffer));
        assertEquals(expectedResult, stringBuffer.toString());
        if (substring) {
            stringBuffer = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
            assertEquals(expectedResult, stringBuffer.toString());  // expect full result as remainder is untouched
        }

        // replace in StringBuilder (in place)
        stringBuilder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        if (substring) {
            stringBuilder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expectedResult, stringBuilder.toString());  // expect full result as remainder is untouched
        }

        // replace in StrBuilder (in place)
        strBuilder = new StrBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(expectedResult, strBuilder.toString());
        if (substring) {
            strBuilder = new StrBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(strBuilder, 1, strBuilder.length() - 2));
            assertEquals(expectedResult, strBuilder.toString());  // expect full result as remainder is untouched
        }
    }

    /**
     * Asserts that the substitutor's variable <em>prefix</em> matcher is implemented by the {@link StrMatcher}
     * subclass with the given simple name (for example {@code "StringMatcher"} or {@code "CharMatcher"}).
     */
    private static void assertStrMatcherPrefixImpl(final String expectedImplName, final StrSubstitutor sub) {
        assertEquals(expectedImplName, sub.getVariablePrefixMatcher().getClass().getSimpleName());
    }

    /**
     * Asserts that the substitutor's variable <em>suffix</em> matcher is implemented by the {@link StrMatcher}
     * subclass with the given simple name (for example {@code "StringMatcher"} or {@code "CharMatcher"}).
     */
    private static void assertStrMatcherSuffixImpl(final String expectedImplName, final StrSubstitutor sub) {
        assertEquals(expectedImplName, sub.getVariableSuffixMatcher().getClass().getSimpleName());
    }

    /**
     * Tests the map constructor that also customizes prefix, suffix, escape and (optionally) the default-value
     * delimiter.
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
     * Tests the map constructor that customizes only the prefix and suffix.
     */
    @Test
    void testConstructorMapPrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        final StrSubstitutor sub = new StrSubstitutor(map, "<", ">");
        assertEquals("Hi < commons", sub.replace("Hi $< <name>"));
    }

    /**
     * Tests the no-argument constructor: with no variable resolver, references are left as-is.
     */
    @Test
    void testConstructorNoArgs() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertEquals("Hi ${name}", sub.replace("Hi ${name}"));
    }

    /**
     * Tests that the {@link StrLookup} constructor produces a substitutor whose default escape character is
     * {@code '$'} and whose {@code replace(CharSequence, int, int)} overload handles a null source.
     */
    @Test
    void testCreatesStrSubstitutorTakingStrLookupAndCallsReplaceTakingTwoAndThreeInts() {
        final Map<String, CharacterPredicates> map = new HashMap<>();
        final StrLookup<CharacterPredicates> strLookupMapStrLookup = StrLookup.mapLookup(map);
        final StrSubstitutor strSubstitutor = new StrSubstitutor(strLookupMapStrLookup);

        assertNull(strSubstitutor.replace((CharSequence) null, 0, 0));
        assertEquals('$', strSubstitutor.getEscapeChar());
    }

    /**
     * Tests a cyclic replace operation.
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
        map.put("critterType", "${animal}");  // closes the loop: animal -> critter -> ... -> animal
        final StrSubstitutor sub = new StrSubstitutor(map);
        assertThrows(IllegalStateException.class, () -> sub.replace(ANIMAL_TARGET_TEMPLATE));

        // also check even when default value is set.
        map.put("critterType", "${animal:-fox}");
        assertThrows(IllegalStateException.class,
                () -> new StrSubstitutor(map).replace(ANIMAL_TARGET_TEMPLATE));
    }

    /**
     * Tests that a variety of default-value delimiters resolve an undefined variable to its default, and that a
     * null delimiter matcher disables default-value resolution entirely.
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

        // An empty delimiter string disables default-value resolution, so the "!..." default is left in place.
        sub = new StrSubstitutor(map, "${", "}", '$', "");
        sub.setValueDelimiterMatcher(null);
        assertEquals("The fox jumps over the lazy dog. ${undefined.number!1234567890}.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));

        // Likewise, clearing the delimiter matcher disables default-value resolution.
        sub = new StrSubstitutor(map, "${", "}", '$');
        sub.setValueDelimiterMatcher(null);
        assertEquals("The fox jumps over the lazy dog. ${undefined.number!1234567890}.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));
    }

    /**
     * Tests that when substitution in values is disabled, variables found inside resolved values are left
     * untouched (only the top-level template variables are replaced).
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
        doTestReplace(sub, "The ${critter} jumps over the ${pet}.", ANIMAL_TARGET_TEMPLATE, true);
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
     * Tests getting and setting the variable prefix (as char, as String, and as a matcher).
     */
    @Test
    void testGetSetPrefix() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);
        sub.setVariablePrefix('<');
        assertStrMatcherPrefixImpl("CharMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);

        sub.setVariablePrefix("<<");
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);
        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefix((String) null));
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);

        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setVariablePrefixMatcher(matcher);
        assertSame(matcher, sub.getVariablePrefixMatcher());
        assertThrows(IllegalArgumentException.class, () -> sub.setVariablePrefixMatcher((StrMatcher) null));
        assertSame(matcher, sub.getVariablePrefixMatcher());
    }

    /**
     * Tests getting and setting the variable suffix (as char, as String, and as a matcher).
     */
    @Test
    void testGetSetSuffix() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);
        sub.setVariableSuffix('<');
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("CharMatcher", sub);

        sub.setVariableSuffix("<<");
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);
        assertThrows(IllegalArgumentException.class, () -> sub.setVariableSuffix((String) null));
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);

        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setVariableSuffixMatcher(matcher);
        assertSame(matcher, sub.getVariableSuffixMatcher());
        assertThrows(IllegalArgumentException.class, () -> sub.setVariableSuffixMatcher((StrMatcher) null));
        assertSame(matcher, sub.getVariableSuffixMatcher());
    }

    /**
     * Tests getting and setting the default-value delimiter; a null delimiter clears the matcher.
     */
    @Test
    void testGetSetValueDelimiter() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);
        sub.setValueDelimiter(':');
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);

        sub.setValueDelimiter("||");
        assertStrMatcherPrefixImpl("StringMatcher", sub);
        assertStrMatcherSuffixImpl("StringMatcher", sub);
        sub.setValueDelimiter((String) null);
        assertNull(sub.getValueDelimiterMatcher());

        final StrMatcher matcher = StrMatcher.commaMatcher();
        sub.setValueDelimiterMatcher(matcher);
        assertSame(matcher, sub.getValueDelimiterMatcher());
        sub.setValueDelimiterMatcher((StrMatcher) null);
        assertNull(sub.getValueDelimiterMatcher());
    }

    /**
     * Test for LANG-1055: StrSubstitutor.replaceSystemProperties does not work consistently
     */
    @Test
    void testLANG1055() {
        System.setProperty("test_key", "test_value");

        final String expected = StrSubstitutor.replace("test_key=${test_key}", System.getProperties());
        final String actual = StrSubstitutor.replaceSystemProperties("test_key=${test_key}");
        assertEquals(expected, actual);
    }

    /**
     * Tests two variables that are adjacent at the end of the template.
     */
    @Test
    void testReplaceAdjacentAtEnd() {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertEquals("Amount is GBP12.50", sub.replace("Amount is ${code}${amount}"));
    }

    /**
     * Tests two variables that are adjacent at the start of the template.
     */
    @Test
    void testReplaceAdjacentAtStart() {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertEquals("GBP12.50 charged", sub.replace("${code}${amount} charged"));
    }

    /**
     * Tests that mutating the backing map after construction affects later replacements (not recommended usage).
     */
    @Test
    void testReplaceChangedMap() {
        final StrSubstitutor sub = new StrSubstitutor(values);
        values.put("target", "moon");
        assertEquals("The quick brown fox jumps over the moon.",
                sub.replace(ANIMAL_TARGET_TEMPLATE));
    }

    /**
     * Tests complex escaping, including an escaped prefix wrapping a real variable reference.
     */
    @Test
    void testReplaceComplexEscaping() {
        doTestReplace("The ${quick brown fox} jumps over the lazy dog.",
                "The $${${animal}} jumps over the ${target}.", true);
        doTestReplace("The ${quick brown fox} jumps over the lazy dog. ${1234567890}.",
                "The $${${animal}} jumps over the ${target}. $${${undefined.number:-1234567890}}.", true);
    }

    /**
     * Tests replace with an empty template.
     */
    @Test
    void testReplaceEmpty() {
        doTestNoReplace("");
    }

    /**
     * Tests templates with an empty variable name.
     */
    @Test
    void testReplaceEmptyKeys() {
        doTestReplace("The ${} jumps over the lazy dog.", "The ${} jumps over the ${target}.", true);
        doTestReplace("The animal jumps over the lazy dog.", "The ${:-animal} jumps over the ${target}.", true);
    }

    /**
     * Tests escaping a single variable reference.
     */
    @Test
    void testReplaceEscaping() {
        doTestReplace("The ${animal} jumps over the lazy dog.", "The $${animal} jumps over the ${target}.", true);
    }

    /**
     * Tests a template with an incomplete (missing) prefix.
     */
    @Test
    void testReplaceIncompletePrefix() {
        doTestReplace("The {animal} jumps over the lazy dog.", "The {animal} jumps over the ${target}.", true);
    }

    /**
     * Tests {@code replaceIn(StringBuffer)} with a non-null buffer whose prefix and suffix never match, so nothing
     * is altered.
     */
    @Test
    void testReplaceInTakingStringBufferWithNonNull() {
        final StrSubstitutor strSubstitutor =
                new StrSubstitutor(new HashMap<>(), "WV@i#y?N*[", "WV@i#y?N*[", '*');

        assertFalse(strSubstitutor.isPreserveEscapes());
        assertFalse(strSubstitutor.replaceIn(new StringBuffer("WV@i#y?N*[")));
        assertEquals('*', strSubstitutor.getEscapeChar());
    }

    /**
     * Tests {@code replaceIn(StringBuilder)} with a non-null builder that contains no resolvable variable.
     */
    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        final StrLookup<String> strLookup = StrLookup.systemPropertiesLookup();
        final StrSubstitutor strSubstitutor = new StrSubstitutor(strLookup, "b<H", "b<H", '\'');
        final StringBuilder stringBuilder = new StringBuilder((CharSequence) "b<H");

        assertEquals('\'', strSubstitutor.getEscapeChar());
        assertFalse(strSubstitutor.replaceIn(stringBuilder));
    }

    /**
     * Tests that {@code replaceIn(StringBuilder)} returns false for a null builder.
     */
    @Test
    void testReplaceInTakingStringBuilderWithNull() {
        final Map<String, Object> map = new HashMap<>();
        final StrSubstitutor strSubstitutor = new StrSubstitutor(map, "", "", 'T', "K+<'f");

        assertFalse(strSubstitutor.replaceIn((StringBuilder) null));
    }

    /**
     * Tests that the {@code replaceIn(StringBuilder, int, int)} overload returns false for a null builder.
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
     * Tests that a variable can be substituted inside a variable name once substitution-in-variables is enabled.
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
     * Tests that substitution within variable names is disabled by default.
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
     * Tests complex and recursive substitution in variable names.
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
     * Tests a template with no variable prefix or suffix at all.
     */
    @Test
    void testReplaceNoPrefixNoSuffix() {
        doTestReplace("The animal jumps over the lazy dog.", "The animal jumps over the ${target}.", true);
    }

    /**
     * Tests a template containing a suffix with no matching prefix.
     */
    @Test
    void testReplaceNoPrefixSuffix() {
        doTestReplace("The animal} jumps over the lazy dog.", "The animal} jumps over the ${target}.", true);
    }

    /**
     * Tests replace with a template that contains no variables.
     */
    @Test
    void testReplaceNoVariables() {
        doTestNoReplace("The balloon arrived.");
    }

    /**
     * Tests replace with a null template.
     */
    @Test
    void testReplaceNull() {
        doTestNoReplace(null);
    }

    /**
     * Tests that the (offset, length) overload only processes the requested window and ignores variables outside it.
     */
    @Test
    void testReplacePartialString_noReplace() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertEquals("${animal} jumps", sub.replace("The ${animal} jumps over the ${target}.", 4, 15));
    }

    /**
     * Tests a template with a prefix that has no matching suffix.
     */
    @Test
    void testReplacePrefixNoSuffix() {
        doTestReplace("The ${animal jumps over the ${target} lazy dog.",
                "The ${animal jumps over the ${target} ${target}.", true);
    }

    /**
     * Tests recursive replacement, where resolved values themselves contain further variables.
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
        doTestReplace(ANIMAL_TARGET_RESULT, ANIMAL_TARGET_TEMPLATE, true);

        values.put("pet", "${petCharacteristicUnknown:-lazy} dog");
        doTestReplace(ANIMAL_TARGET_RESULT, ANIMAL_TARGET_TEMPLATE, true);
    }

    /**
     * Tests a simple key replacement.
     */
    @Test
    void testReplaceSimple() {
        doTestReplace(ANIMAL_TARGET_RESULT, ANIMAL_TARGET_TEMPLATE, true);
    }

    /**
     * Tests replacing a single standalone variable.
     */
    @Test
    void testReplaceSolo() {
        doTestReplace("quick brown fox", "${animal}", false);
    }

    /**
     * Tests escaping a single standalone variable.
     */
    @Test
    void testReplaceSoloEscaping() {
        doTestReplace("${animal}", "$${animal}", false);
    }

    /**
     * Tests that {@code replace} on a null CharSequence returns null without altering escape settings.
     */
    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StrSubstitutor strSubstitutor = new StrSubstitutor((StrLookup<?>) null);

        assertNull(strSubstitutor.replace((CharSequence) null));
        assertFalse(strSubstitutor.isPreserveEscapes());
        assertEquals('$', strSubstitutor.getEscapeChar());
    }

    /**
     * Tests that the static {@code replace(Object, Properties)} throws on a null source.
     */
    @Test
    void testReplaceTakingThreeArgumentsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StrSubstitutor.replace(null, (Properties) null));
    }

    /**
     * Tests a template that resolves to itself (output identical to input).
     */
    @Test
    void testReplaceToIdentical() {
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");
        doTestReplace("The ${animal} jumps.", "The ${animal} jumps.", true);
    }

    /**
     * Tests replacement of an unknown key, which is left as-is.
     */
    @Test
    void testReplaceUnknownKey() {
        doTestReplace("The ${person} jumps over the lazy dog.", "The ${person} jumps over the ${target}.", true);
        doTestReplace("The ${person} jumps over the lazy dog. 1234567890.",
                "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.", true);
    }

    /**
     * Tests interpolation with a variety of weird boundary patterns that must all pass through unchanged.
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
     * Tests overriding the protected {@code resolveVariable} hook, verifying the arguments it receives and that its
     * return value is substituted in place.
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
     * Tests using the same string as both prefix and suffix.
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
     * Tests the static {@code replace(Object, Map)} convenience method.
     */
    @Test
    void testStaticReplace() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEquals("Hi commons!", StrSubstitutor.replace("Hi ${name}!", map));
    }

    /**
     * Tests the static {@code replace(Object, Map, prefix, suffix)} convenience method.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEquals("Hi commons!", StrSubstitutor.replace("Hi <name>!", map, "<", ">"));
    }

    /**
     * Tests interpolation against system properties.
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
     * Tests replacement against a Properties object that resolves via its System.getProperties defaults.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String org = "${doesnotwork}";
        System.setProperty("doesnotwork", "It works!");

        // create a new Properties object with the System.getProperties as default
        final Properties props = new Properties(System.getProperties());

        assertEquals("It works!", StrSubstitutor.replace(org, props));
    }

    /**
     * Tests the preserve-escapes flag: when enabled, the escape character is retained in the output.
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
