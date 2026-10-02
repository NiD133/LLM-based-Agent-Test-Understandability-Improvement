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

    private static final String BASIC_TEMPLATE = "The ${animal} jumps over the ${target}.";
    private static final String BASIC_RESULT = "The quick brown fox jumps over the lazy dog.";

    private Map<String, String> values;

    private void assertNoReplacement(final String template) {
        final StrSubstitutor sub = new StrSubstitutor(values);

        if (template == null) {
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
            return;
        }

        assertEquals(template, sub.replace(template));
        final StrBuilder builder = new StrBuilder(template);
        assertFalse(sub.replaceIn(builder));
        assertEquals(template, builder.toString());
    }

    private void assertReplacementAcrossInputTypes(final String expected, final String template,
            final boolean supportsSubstringChecks) {
        assertReplacementAcrossInputTypes(new StrSubstitutor(values), expected, template, supportsSubstringChecks);
    }

    private void assertReplacementAcrossInputTypes(final StrSubstitutor sub, final String expected,
            final String template, final boolean supportsSubstringChecks) {
        final String expectedSubstringResult = expected.substring(1, expected.length() - 1);

        assertEquals(expected, sub.replace(template));
        if (supportsSubstringChecks) {
            assertEquals(expectedSubstringResult, sub.replace(template, 1, template.length() - 2));
        }

        final char[] chars = template.toCharArray();
        assertEquals(expected, sub.replace(chars));
        if (supportsSubstringChecks) {
            assertEquals(expectedSubstringResult, sub.replace(chars, 1, chars.length - 2));
        }

        StringBuffer buffer = new StringBuffer(template);
        assertEquals(expected, sub.replace(buffer));
        if (supportsSubstringChecks) {
            assertEquals(expectedSubstringResult, sub.replace(buffer, 1, buffer.length() - 2));
        }

        StringBuilder stringBuilder = new StringBuilder(template);
        assertEquals(expected, sub.replace(stringBuilder));
        if (supportsSubstringChecks) {
            assertEquals(expectedSubstringResult, sub.replace(stringBuilder, 1, stringBuilder.length() - 2));
        }

        StrBuilder strBuilder = new StrBuilder(template);
        assertEquals(expected, sub.replace(strBuilder));
        if (supportsSubstringChecks) {
            assertEquals(expectedSubstringResult, sub.replace(strBuilder, 1, strBuilder.length() - 2));
        }

        final MutableObject<String> objectWhoseToStringReturnsTemplate = new MutableObject<>(template);
        assertEquals(expected, sub.replace(objectWhoseToStringReturnsTemplate));

        buffer = new StringBuffer(template);
        assertTrue(sub.replaceIn(buffer));
        assertEquals(expected, buffer.toString());
        if (supportsSubstringChecks) {
            buffer = new StringBuffer(template);
            assertTrue(sub.replaceIn(buffer, 1, buffer.length() - 2));
            assertEquals(expected, buffer.toString());
        }

        stringBuilder = new StringBuilder(template);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(expected, stringBuilder.toString());
        if (supportsSubstringChecks) {
            stringBuilder = new StringBuilder(template);
            assertTrue(sub.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expected, stringBuilder.toString());
        }

        strBuilder = new StrBuilder(template);
        assertTrue(sub.replaceIn(strBuilder));
        assertEquals(expected, strBuilder.toString());
        if (supportsSubstringChecks) {
            strBuilder = new StrBuilder(template);
            assertTrue(sub.replaceIn(strBuilder, 1, strBuilder.length() - 2));
            assertEquals(expected, strBuilder.toString());
        }
    }

    private void addRecursiveAnimalAndTargetValues() {
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");
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
     * Tests constructor.
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
     * Tests constructor.
     */
    @Test
    void testConstructorMapPrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        final StrSubstitutor sub = new StrSubstitutor(map, "<", ">");
        assertEquals("Hi < commons", sub.replace("Hi $< <name>"));
    }

    /**
     * Tests constructor.
     */
    @Test
    void testConstructorNoArgs() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertEquals("Hi ${name}", sub.replace("Hi ${name}"));
    }

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
        map.put("critterType", "${animal}");
        final StrSubstitutor sub = new StrSubstitutor(map);
        assertThrows(IllegalStateException.class, () -> sub.replace(BASIC_TEMPLATE));

        map.put("critterType", "${animal:-fox}");
        assertThrows(IllegalStateException.class, () -> new StrSubstitutor(map).replace(BASIC_TEMPLATE));
    }

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

        sub = new StrSubstitutor(map, "${", "}", '$', "");
        sub.setValueDelimiterMatcher(null);
        assertEquals("The fox jumps over the lazy dog. ${undefined.number!1234567890}.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));

        sub = new StrSubstitutor(map, "${", "}", '$');
        sub.setValueDelimiterMatcher(null);
        assertEquals("The fox jumps over the lazy dog. ${undefined.number!1234567890}.",
                sub.replace("The ${animal} jumps over the lazy ${target}. ${undefined.number!1234567890}."));
    }

    @Test
    void testDisableSubstitutionInValues() {
        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setDisableSubstitutionInValues(true);
        addRecursiveAnimalAndTargetValues();
        assertReplacementAcrossInputTypes(sub, "The ${critter} jumps over the ${pet}.", BASIC_TEMPLATE, true);
    }

    /**
     * Tests get set.
     */
    @Test
    void testGetSetEscape() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertEquals('$', sub.getEscapeChar());
        sub.setEscapeChar('<');
        assertEquals('<', sub.getEscapeChar());
    }

    /**
     * Tests get set.
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
     * Tests get set.
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
     * Tests get set.
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
     * Tests adjacent keys.
     */
    @Test
    void testReplaceAdjacentAtEnd() {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertEquals("Amount is GBP12.50", sub.replace("Amount is ${code}${amount}"));
    }

    /**
     * Tests adjacent keys.
     */
    @Test
    void testReplaceAdjacentAtStart() {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertEquals("GBP12.50 charged", sub.replace("${code}${amount} charged"));
    }

    /**
     * Tests key replace changing map after initialization (not recommended).
     */
    @Test
    void testReplaceChangedMap() {
        final StrSubstitutor sub = new StrSubstitutor(values);
        values.put("target", "moon");
        assertEquals("The quick brown fox jumps over the moon.", sub.replace(BASIC_TEMPLATE));
    }

    /**
     * Tests complex escaping.
     */
    @Test
    void testReplaceComplexEscaping() {
        assertReplacementAcrossInputTypes("The ${quick brown fox} jumps over the lazy dog.",
                "The $${${animal}} jumps over the ${target}.", true);
        assertReplacementAcrossInputTypes("The ${quick brown fox} jumps over the lazy dog. ${1234567890}.",
                "The $${${animal}} jumps over the ${target}. $${${undefined.number:-1234567890}}.", true);
    }

    /**
     * Tests replace with null.
     */
    @Test
    void testReplaceEmpty() {
        assertNoReplacement("");
    }

    /**
     * Tests when no variable name.
     */
    @Test
    void testReplaceEmptyKeys() {
        assertReplacementAcrossInputTypes("The ${} jumps over the lazy dog.", "The ${} jumps over the ${target}.",
                true);
        assertReplacementAcrossInputTypes("The animal jumps over the lazy dog.", "The ${:-animal} jumps over the ${target}.",
                true);
    }

    /**
     * Tests escaping.
     */
    @Test
    void testReplaceEscaping() {
        assertReplacementAcrossInputTypes("The ${animal} jumps over the lazy dog.",
                "The $${animal} jumps over the ${target}.", true);
    }

    /**
     * Tests when no incomplete prefix.
     */
    @Test
    void testReplaceIncompletePrefix() {
        assertReplacementAcrossInputTypes("The {animal} jumps over the lazy dog.",
                "The {animal} jumps over the ${target}.", true);
    }

    @Test
    void testReplaceInTakingStringBufferWithNonNull() {
        final StrSubstitutor strSubstitutor =
                new StrSubstitutor(new HashMap<>(), "WV@i#y?N*[", "WV@i#y?N*[", '*');

        assertFalse(strSubstitutor.isPreserveEscapes());
        assertFalse(strSubstitutor.replaceIn(new StringBuffer("WV@i#y?N*[")));
        assertEquals('*', strSubstitutor.getEscapeChar());
    }

    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        final StrLookup<String> strLookup = StrLookup.systemPropertiesLookup();
        final StrSubstitutor strSubstitutor = new StrSubstitutor(strLookup, "b<H", "b<H", '\'');
        final StringBuilder stringBuilder = new StringBuilder((CharSequence) "b<H");

        assertEquals('\'', strSubstitutor.getEscapeChar());
        assertFalse(strSubstitutor.replaceIn(stringBuilder));
    }

    @Test
    void testReplaceInTakingStringBuilderWithNull() {
        final Map<String, Object> map = new HashMap<>();
        final StrSubstitutor strSubstitutor = new StrSubstitutor(map, "", "", 'T', "K+<'f");

        assertFalse(strSubstitutor.replaceIn((StringBuilder) null));
    }

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
     * Tests whether a variable can be replaced in a variable name.
     */
    @Test
    void testReplaceInVariable() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StrSubstitutor sub = new StrSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);
        assertEquals("The mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));
        values.put("species", "1");
        assertEquals("The fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));
        assertEquals("The fox jumps over the lazy dog.",
                sub.replace("The ${unknown.animal.${unknown.species:-1}:-fox} "
                        + "jumps over the ${unknow.target:-lazy dog}."));
    }

    /**
     * Tests whether substitution in variable names is disabled per default.
     */
    @Test
    void testReplaceInVariableDisabled() {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StrSubstitutor sub = new StrSubstitutor(values);
        assertEquals("The ${animal.${species}} jumps over the lazy dog.",
                sub.replace("The ${animal.${species}} jumps over the ${target}."));
        assertEquals("The ${animal.${species:-1}} jumps over the lazy dog.",
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
        assertEquals("The white mouse jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${color}}} jumps over the ${target}."));
        assertEquals("The brown fox jumps over the lazy dog.",
                sub.replace("The ${animal.${species.${unknownColor:-brown}}} jumps over the ${target}."));
    }

    /**
     * Tests when no prefix or suffix.
     */
    @Test
    void testReplaceNoPrefixNoSuffix() {
        assertReplacementAcrossInputTypes("The animal jumps over the lazy dog.",
                "The animal jumps over the ${target}.", true);
    }

    /**
     * Tests when suffix but no prefix.
     */
    @Test
    void testReplaceNoPrefixSuffix() {
        assertReplacementAcrossInputTypes("The animal} jumps over the lazy dog.",
                "The animal} jumps over the ${target}.", true);
    }

    /**
     * Tests replace with no variables.
     */
    @Test
    void testReplaceNoVariables() {
        assertNoReplacement("The balloon arrived.");
    }

    /**
     * Tests replace with null.
     */
    @Test
    void testReplaceNull() {
        assertNoReplacement(null);
    }

    /**
     * Tests simple key replace.
     */
    @Test
    void testReplacePartialString_noReplace() {
        final StrSubstitutor sub = new StrSubstitutor();
        assertEquals("${animal} jumps", sub.replace("The ${animal} jumps over the ${target}.", 4, 15));
    }

    /**
     * Tests when prefix but no suffix.
     */
    @Test
    void testReplacePrefixNoSuffix() {
        assertReplacementAcrossInputTypes("The ${animal jumps over the ${target} lazy dog.",
                "The ${animal jumps over the ${target} ${target}.", true);
    }

    /**
     * Tests simple recursive replace.
     */
    @Test
    void testReplaceRecursive() {
        addRecursiveAnimalAndTargetValues();
        assertReplacementAcrossInputTypes(BASIC_RESULT, BASIC_TEMPLATE, true);

        values.put("pet", "${petCharacteristicUnknown:-lazy} dog");
        assertReplacementAcrossInputTypes(BASIC_RESULT, BASIC_TEMPLATE, true);
    }

    /**
     * Tests simple key replace.
     */
    @Test
    void testReplaceSimple() {
        assertReplacementAcrossInputTypes(BASIC_RESULT, BASIC_TEMPLATE, true);
    }

    /**
     * Tests simple key replace.
     */
    @Test
    void testReplaceSolo() {
        assertReplacementAcrossInputTypes("quick brown fox", "${animal}", false);
    }

    /**
     * Tests escaping.
     */
    @Test
    void testReplaceSoloEscaping() {
        assertReplacementAcrossInputTypes("${animal}", "$${animal}", false);
    }

    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StrSubstitutor strSubstitutor = new StrSubstitutor((StrLookup<?>) null);

        assertNull(strSubstitutor.replace((CharSequence) null));
        assertFalse(strSubstitutor.isPreserveEscapes());
        assertEquals('$', strSubstitutor.getEscapeChar());
    }

    @Test
    void testReplaceTakingThreeArgumentsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StrSubstitutor.replace(null, (Properties) null));
    }

    /**
     * Tests replace creates output same as input.
     */
    @Test
    void testReplaceToIdentical() {
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");
        assertReplacementAcrossInputTypes("The ${animal} jumps.", "The ${animal} jumps.", true);
    }

    /**
     * Tests unknown key replace.
     */
    @Test
    void testReplaceUnknownKey() {
        assertReplacementAcrossInputTypes("The ${person} jumps over the lazy dog.",
                "The ${person} jumps over the ${target}.", true);
        assertReplacementAcrossInputTypes("The ${person} jumps over the lazy dog. 1234567890.",
                "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.", true);
    }

    /**
     * Tests interpolation with weird boundary patterns.
     */
    @Test
    void testReplaceWeirdPattens() {
        assertNoReplacement("");
        assertNoReplacement("${}");
        assertNoReplacement("${ }");
        assertNoReplacement("${\t}");
        assertNoReplacement("${\n}");
        assertNoReplacement("${\b}");
        assertNoReplacement("${");
        assertNoReplacement("$}");
        assertNoReplacement("}");
        assertNoReplacement("${}$");
        assertNoReplacement("${${");
        assertNoReplacement("${${}}");
        assertNoReplacement("${$${}}");
        assertNoReplacement("${$$${}}");
        assertNoReplacement("${$$${$}}");
        assertNoReplacement("${${}}");
        assertNoReplacement("${${ }}");
    }

    /**
     * Tests protected.
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
     * Tests static.
     */
    @Test
    void testStaticReplace() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEquals("Hi commons!", StrSubstitutor.replace("Hi ${name}!", map));
    }

    /**
     * Tests static.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEquals("Hi commons!", StrSubstitutor.replace("Hi <name>!", map, "<", ">"));
    }

    /**
     * Tests interpolation with system properties.
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
     * Test the replace of a properties object.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String org = "${doesnotwork}";
        System.setProperty("doesnotwork", "It works!");

        final Properties props = new Properties(System.getProperties());

        assertEquals("It works!", StrSubstitutor.replace(org, props));
    }

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
