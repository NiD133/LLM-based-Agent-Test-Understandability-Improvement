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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemProperties;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.lookup.StringLookupFactory;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * Test class for {@link StringSubstitutor}.
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class StringSubstitutorTest {

    private static final String ACTUAL_ANIMAL = "quick brown fox";
    private static final String ACTUAL_TARGET = "lazy dog";
    private static final String CLASSIC_RESULT = "The quick brown fox jumps over the lazy dog.";
    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";
    /** An empty variable expression with no variable name between the delimiters. */
    private static final String EMPTY_EXPR = "${}";
    /** Delimiter character used in tests where the prefix and suffix are identical. */
    private static final String AT_DELIMITER = "@";

    protected Map<String, String> values;

    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d", StringUtils.length(expected), StringUtils.length(actual)));
    }

    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    protected void doTestNoReplace(final StringSubstitutor substitutor, final String replaceTemplate) throws IOException {
        if (replaceTemplate == null) {
            assertNull(replace(substitutor, (String) null));
            assertNull(substitutor.replace((String) null, 0, 100));
            assertNull(substitutor.replace((char[]) null));
            assertNull(substitutor.replace((char[]) null, 0, 100));
            assertNull(substitutor.replace((StringBuffer) null));
            assertNull(substitutor.replace((StringBuffer) null, 0, 100));
            assertNull(substitutor.replace((TextStringBuilder) null));
            assertNull(substitutor.replace((TextStringBuilder) null, 0, 100));
            assertNull(substitutor.replace((Object) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null));
            assertFalse(substitutor.replaceIn((StringBuffer) null, 0, 100));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null));
            assertFalse(substitutor.replaceIn((TextStringBuilder) null, 0, 100));
        } else {
            assertEquals(replaceTemplate, replace(substitutor, replaceTemplate));
            final TextStringBuilder builder = new TextStringBuilder(replaceTemplate);
            assertFalse(substitutor.replaceIn(builder));
            assertEquals(replaceTemplate, builder.toString());
        }
    }

    protected void doTestReplace(final StringSubstitutor sub, final String expectedResult, final String replaceTemplate, final boolean substring)
            throws IOException {
        final String expectedShortResult = substring ? expectedResult.substring(1, expectedResult.length() - 1) : expectedResult;

        // replace using String
        final String actual = replace(sub, replaceTemplate);
        assertEquals(expectedResult, actual, () -> String.format("Index of difference: %,d", StringUtils.indexOfDifference(expectedResult, actual)));
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
        StringBuffer buf = new StringBuffer(replaceTemplate);
        assertEquals(expectedResult, sub.replace(buf));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(buf, 1, buf.length() - 2));
        }

        // replace using StringBuilder
        StringBuilder builder = new StringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(builder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(builder, 1, builder.length() - 2));
        }

        // replace using TextStringBuilder
        TextStringBuilder bld = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(bld));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(bld, 1, bld.length() - 2));
        }

        // replace using object
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate); // toString returns template
        assertEquals(expectedResult, sub.replace(obj));

        // replace in StringBuffer
        buf = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(buf), replaceTemplate);
        assertEquals(expectedResult, buf.toString());
        if (substring) {
            buf = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(buf, 1, buf.length() - 2));
            assertEquals(expectedResult, buf.toString()); // expect full result as remainder is untouched
        }

        // replace in StringBuilder
        builder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(builder));
        assertEquals(expectedResult, builder.toString());
        if (substring) {
            builder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(builder, 1, builder.length() - 2));
            assertEquals(expectedResult, builder.toString()); // expect full result as remainder is untouched
        }

        // replace in TextStringBuilder
        bld = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(bld));
        assertEquals(expectedResult, bld.toString());
        if (substring) {
            bld = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(bld, 1, bld.length() - 2));
            assertEquals(expectedResult, bld.toString()); // expect full result as remainder is untouched
        }
    }

    /**
     * For subclasses to override.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // Single-character keys with single-digit values
        values.put("a", "1");
        values.put("b", "2");
        // Two-character keys with two-digit values
        values.put("aa", "11");
        values.put("bb", "22");
        // Three-character keys with three-digit values
        values.put("aaa", "111");
        values.put("bbb", "222");
        // Composite key whose value is itself a variable name (used in nested substitution tests)
        values.put("a2b", "b");
        // Descriptive keys used in the classic animal/target substitution examples
        values.put("animal", ACTUAL_ANIMAL);
        values.put("target", ACTUAL_TARGET);
    }

    @AfterEach
    public void tearDown() {
        values = null;
    }

    @Test
    void testConstructorNullMap() {
        final Map<String, Object> parameters = null;
        final StringSubstitutor sub = new StringSubstitutor(parameters, "prefix", "suffix");
        assertNull(sub.getStringLookup().apply("X"));
        assertNull(sub.getStringLookup().lookup("X"));
    }

    @Test
    void testConstructorStringSubstitutor() {
        final StringSubstitutor source = new StringSubstitutor();
        source.setDisableSubstitutionInValues(true);
        source.setEnableSubstitutionInVariables(true);
        source.setEnableUndefinedVariableException(true);
        source.setEscapeChar('e');
        source.setValueDelimiter('d');
        source.setVariablePrefix('p');
        source.setVariableResolver(StringLookupFactory.INSTANCE.nullStringLookup());
        source.setVariableSuffix('s');

        final StringSubstitutor target = new StringSubstitutor(source);

        assertTrue(target.isDisableSubstitutionInValues());
        assertTrue(target.isEnableSubstitutionInVariables());
        assertTrue(target.isEnableUndefinedVariableException());
        assertEquals('e', target.getEscapeChar());
        assertTrue(target.getValueDelimiterMatcher().toString().endsWith("['d']"), target.getValueDelimiterMatcher().toString());
        assertTrue(target.getVariablePrefixMatcher().toString().endsWith("['p']"), target.getValueDelimiterMatcher().toString());
        assertTrue(target.getVariableSuffixMatcher().toString().endsWith("['s']"), target.getValueDelimiterMatcher().toString());
    }

    @Test
    void testDetectsCyclicSubstitution() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "<name>");
        assertThrows(IllegalStateException.class, () -> StringSubstitutor.replace("Hi <name>.", map, "<", ">"));
    }

    /**
     * Tests getting and setting the escape character.
     */
    @Test
    void testGetSetEscape() {
        final StringSubstitutor sub = new StringSubstitutor();
        assertEquals('$', sub.getEscapeChar());
        sub.setEscapeChar('<');
        assertEquals('<', sub.getEscapeChar());
    }

    /**
     * Test for LANG-1055: StringSubstitutor.replaceSystemProperties does not work consistently.
     */
    @Test
    void testLANG1055() {
        System.setProperty("test_key", "test_value");

        final String expected = StringSubstitutor.replace("test_key=${test_key}", System.getProperties());
        final String actual = StringSubstitutor.replaceSystemProperties("test_key=${test_key}");
        assertEquals(expected, actual);
    }

    /**
     * Tests interpolation with weird boundary patterns (part 1):
     * escaped and partial patterns that must not produce any substitution.
     */
    @Test
    void testReplace_JiraText178_WeirdPatterns1() throws IOException {
        doNotReplace("$${");
        doNotReplace("$${a");
        doNotReplace("$$${");
        doNotReplace("$$${a");
        doNotReplace("$${${a");
        doNotReplace("${${a}"); // "${a" is not a registered variable name.
        doNotReplace("${$${a}");
    }

    /**
     * Tests interpolation with weird boundary patterns (part 2):
     * an escaped prefix followed by a variable expression yields a literal variable string.
     */
    @Test
    void testReplace_JiraText178_WeirdPatterns2() throws IOException {
        doReplace("${1}", "$${${a}}", false);
    }

    /**
     * Tests interpolation with weird boundary patterns (part 3):
     * an escaped prefix followed by an incomplete variable leaves the escaped prefix literal
     * without substituting the nested variable name.
     */
    @Test
    void testReplace_JiraText178_WeirdPatterns3() throws IOException {
        doReplace("${${a}", "$${${a}", false); // not "$${1" or "${1"
    }

    /**
     * Tests adjacent keys at the end of a string.
     */
    @Test
    void testReplaceAdjacentAtEnd() throws IOException {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StringSubstitutor sub = new StringSubstitutor(values);
        assertEqualsCharSeq("Amount is GBP12.50", replace(sub, "Amount is ${code}${amount}"));
    }

    /**
     * Tests adjacent keys at the start of a string.
     */
    @Test
    void testReplaceAdjacentAtStart() throws IOException {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StringSubstitutor sub = new StringSubstitutor(values);
        assertEqualsCharSeq("GBP12.50 charged", replace(sub, "${code}${amount} charged"));
    }

    /**
     * Tests that a substitutor reflects live map changes because it holds a reference to the map.
     */
    @Test
    void testReplaceChangedMap() throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);
        // no map change
        final String template = CLASSIC_TEMPLATE;
        assertEqualsCharSeq(CLASSIC_RESULT, replace(sub, template));
        // map change
        values.put("target", "moon");
        assertEqualsCharSeq("The quick brown fox jumps over the moon.", replace(sub, template));
    }

    /**
     * Tests complex escaping: an escaped prefix followed by a variable expression produces a
     * literal "${value}" string in the output.
     */
    @Test
    void testReplaceComplexEscaping() throws IOException {
        doReplace("${1}", "$${${a}}", false);
        doReplace("${11}", "$${${aa}}", false);
        doReplace("${111}", "$${${aaa}}", false);
        doReplace("${quick brown fox}", "$${${animal}}", false);
        doReplace("The ${quick brown fox} jumps over the lazy dog.", "The $${${animal}} jumps over the ${target}.", true);
        doReplace("${${a}}", "$${$${a}}", false);
        doReplace("${${aa}}", "$${$${aa}}", false);
        doReplace("${${aaa}}", "$${$${aaa}}", false);
        doReplace("${${animal}}", "$${$${animal}}", false);
        doReplace(".${${animal}}", ".$${$${animal}}", false);
        doReplace("${${animal}}.", "$${$${animal}}.", false);
        doReplace(".${${animal}}.", ".$${$${animal}}.", false);
        doReplace("The ${${animal}} jumps over the lazy dog.", "The $${$${animal}} jumps over the ${target}.", true);
        doReplace("The ${quick brown fox} jumps over the lazy dog. ${1234567890}.",
                "The $${${animal}} jumps over the ${target}. $${${undefined.number:-1234567890}}.", true);
    }

    /**
     * Tests that an empty key expression (no variable name between delimiters) is left unreplaced.
     */
    @Test
    void testReplaceEmptyKey() throws IOException {
        doReplace("The ${} jumps over the lazy dog.", "The ${} jumps over the ${target}.", true);
    }

    /**
     * Tests that an empty key expression preceded by a character is left unreplaced.
     */
    @Test
    void testReplaceEmptyKeyExtraFirst() throws IOException {
        assertEqualsCharSeq("." + EMPTY_EXPR, replace(new StringSubstitutor(values), "." + EMPTY_EXPR));
    }

    /**
     * Tests that an empty key expression followed by a character is left unreplaced.
     */
    @Test
    void testReplaceEmptyKeyExtraLast() throws IOException {
        assertEqualsCharSeq(EMPTY_EXPR + ".", replace(new StringSubstitutor(values), EMPTY_EXPR + "."));
    }

    /**
     * Tests that a template consisting only of an empty key expression is returned unchanged.
     */
    @Test
    void testReplaceEmptyKeyOnly() throws IOException {
        assertEquals(EMPTY_EXPR, replace(new StringSubstitutor(values), EMPTY_EXPR));
    }

    /**
     * Tests that an empty key expression alone (no adjacent text) is not replaced.
     */
    @Test
    void testReplaceEmptyKeyShortest() throws IOException {
        doNotReplace(EMPTY_EXPR);
    }

    /**
     * Tests that an empty key expression with a default value resolves to that default value.
     */
    @Test
    void testReplaceEmptyKeyWithDefault() throws IOException {
        doReplace("The animal jumps over the lazy dog.", "The ${:-animal} jumps over the ${target}.", true);
    }

    /**
     * Tests that a template with only an empty key and default value resolves to the default.
     */
    @Test
    void testReplaceEmptyKeyWithDefaultOnly() throws IOException {
        doReplace("animal", "${:-animal}", false);
    }

    /**
     * Tests that an empty key with an empty default value resolves to an empty string.
     */
    @Test
    void testReplaceEmptyKeyWithDefaultOnlyEmpty() throws IOException {
        doReplace("", "${:-}", false);
    }

    /**
     * Tests that an empty key with a single-character default value resolves to that character.
     */
    @Test
    void testReplaceEmptyKeyWithDefaultOnlyShortest() throws IOException {
        doReplace("a", "${:-a}", false);
    }

    /**
     * Tests that replacing an empty string produces an empty string (nothing to substitute).
     */
    @Test
    void testReplaceEmptyString() throws IOException {
        doNotReplace(StringUtils.EMPTY);
    }

    /**
     * Tests escaping: a double-dollar sign before a variable reference leaves it as a literal variable string.
     */
    @Test
    void testReplaceEscaping() throws IOException {
        doReplace("The ${animal} jumps over the lazy dog.", "The $${animal} jumps over the ${target}.", true);
        doReplace("${a}", "$${a}", false);
        doReplace("${a${a}}", "$${a$${a}}", false);
        doReplace("${a${a${a}}}", "$${a$${a$${a}}}", false);
    }

    /**
     * Tests that enabling undefined-variable exceptions causes them to be thrown for unresolvable variables,
     * while variables with available default values do not throw.
     */
    @Test
    void testReplaceFailOnUndefinedVariable() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableUndefinedVariableException(true);

        assertEquals("Cannot resolve variable 'animal.${species' (enableSubstitutionInVariables=false).",
                assertThrows(IllegalArgumentException.class, () -> replace(sub, "The ${animal.${species}} jumps over the ${target}.")).getMessage());

        assertEquals("Cannot resolve variable 'animal.${species:-1' (enableSubstitutionInVariables=false).",
                assertThrows(IllegalArgumentException.class, () -> replace(sub, "The ${animal.${species:-1}} jumps over the ${target}.")).getMessage());

        assertEquals("Cannot resolve variable 'unknown' (enableSubstitutionInVariables=false).",
                assertThrows(IllegalArgumentException.class, () -> replace(sub, "The ${test:-statement} is a sample for missing ${unknown}.")).getMessage());

        // if default value is available, exception will not be thrown
        assertEqualsCharSeq("The statement is a sample for missing variable.",
                replace(sub, "The ${test:-statement} is a sample for missing ${unknown:-variable}."));

        assertEqualsCharSeq("The fox jumps over the lazy dog.", replace(sub, "The ${animal.1} jumps over the ${target}."));
    }

    /**
     * Tests undefined-variable exceptions when substitution in variable names is also enabled,
     * verifying that nested variable resolution works while still throwing for truly unresolvable names.
     */
    @Test
    void testReplaceFailOnUndefinedVariableWithReplaceInVariable() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        values.put("statement.1", "2");
        values.put("recursive", "1");
        values.put("word", "variable");
        values.put("testok.2", "statement");
        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableUndefinedVariableException(true);
        sub.setEnableSubstitutionInVariables(true);

        assertEqualsCharSeq("The mouse jumps over the lazy dog.", replace(sub, "The ${animal.${species}} jumps over the ${target}."));
        values.put("species", "1");
        assertEqualsCharSeq("The fox jumps over the lazy dog.", replace(sub, "The ${animal.${species}} jumps over the ${target}."));

        // exception is thrown here because variable with name test.1 is missing
        assertEquals("Cannot resolve variable 'statement' (enableSubstitutionInVariables=true).",
                assertThrows(IllegalArgumentException.class, () -> replace(sub, "The ${test.${statement}} is a sample for missing ${word}.")).getMessage());

        // exception is thrown here because variable with name test.2 is missing
        assertEquals("Cannot resolve variable 'test.2' (enableSubstitutionInVariables=true).",
                assertThrows(IllegalArgumentException.class, () -> replace(sub, "The ${test.${statement.${recursive}}} is a sample for missing ${word}."))
                        .getMessage());

        assertEqualsCharSeq("statement", replace(sub, "${testok.${statement.${recursive}}}"));

        assertEqualsCharSeq("${testok.2}", replace(sub, "$${testok.${statement.${recursive}}}"));

        assertEqualsCharSeq("The statement is a sample for missing variable.",
                replace(sub, "The ${testok.${statement.${recursive}}} is a sample for missing ${word}."));
    }

    /**
     * Tests that a partial prefix without a matching suffix is treated as literal text,
     * while complete variable expressions elsewhere are still replaced.
     */
    @Test
    void testReplaceIncompletePrefix() throws IOException {
        doReplace("The {animal} jumps over the lazy dog.", "The {animal} jumps over the ${target}.", true);
    }

    /**
     * Tests that {@code replaceIn(StringBuffer)} returns false when the template contains no
     * resolvable variables. Uses a custom prefix/suffix that appears as a prefix-only in the content,
     * so no complete variable expression is matched.
     */
    @Test
    void testReplaceInStringBuffer_noMatchingPattern_returnsFalse() {
        final StringSubstitutor sub = new StringSubstitutor(new HashMap<>(), "WV@i#y?N*[", "WV@i#y?N*[", '*');

        assertFalse(sub.isPreserveEscapes());
        assertFalse(sub.replaceIn(new StringBuffer("WV@i#y?N*[")));
        assertEquals('*', sub.getEscapeChar());
    }

    /**
     * Tests that {@code replaceIn(StringBuilder)} returns false when the template contains no
     * resolvable variables. Uses a system-property lookup with a prefix/suffix that does not appear
     * in the test content.
     */
    @Test
    void testReplaceInStringBuilder_noMatchingPattern_returnsFalse() {
        final StringLookup systemPropLookup = StringLookupFactory.INSTANCE.systemPropertyStringLookup();
        final StringSubstitutor sub = new StringSubstitutor(systemPropLookup, "b<H", "b<H", '\'');
        final StringBuilder content = new StringBuilder((CharSequence) "b<H");

        assertEquals('\'', sub.getEscapeChar());
        assertFalse(sub.replaceIn(content));
    }

    /**
     * Tests that {@code replaceIn(StringBuilder)} returns false when passed a null argument.
     */
    @Test
    void testReplaceInStringBuilder_nullSource_returnsFalse() {
        final Map<String, Object> map = new HashMap<>();
        final StringSubstitutor sub = new StringSubstitutor(map, StringUtils.EMPTY, StringUtils.EMPTY, 'T', "K+<'f");

        assertFalse(sub.replaceIn((StringBuilder) null));
    }

    /**
     * Tests that {@code replaceIn(StringBuilder, int, int)} returns false when passed a null argument,
     * and verifies that the escape character and preserve-escapes flag are set correctly on construction.
     */
    @Test
    void testReplaceInStringBuilderWithOffsets_nullSource_returnsFalse() {
        final Map<String, Object> hashMap = new HashMap<>();
        final StringLookup mapStringLookup = StringLookupFactory.INSTANCE.mapStringLookup(hashMap);
        final StringMatcher tabMatcher = StringMatcherFactory.INSTANCE.tabMatcher();
        final StringSubstitutor sub = new StringSubstitutor(mapStringLookup, tabMatcher, tabMatcher, 'b', tabMatcher);

        assertFalse(sub.replaceIn((StringBuilder) null, 1315, -1369));
        assertEquals('b', sub.getEscapeChar());
        assertFalse(sub.isPreserveEscapes());
    }

    /**
     * Tests whether a variable can be replaced in a variable name (nested variable substitution).
     */
    @Test
    void testReplaceInVariable() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);
        assertEqualsCharSeq("The mouse jumps over the lazy dog.", replace(sub, "The ${animal.${species}} jumps over the ${target}."));
        values.put("species", "1");
        assertEqualsCharSeq("The fox jumps over the lazy dog.", replace(sub, "The ${animal.${species}} jumps over the ${target}."));
        assertEqualsCharSeq("The fox jumps over the lazy dog.",
                replace(sub, "The ${unknown.animal.${unknown.species:-1}:-fox} jumps over the ${unknow.target:-lazy dog}."));
    }

    /**
     * Tests that substitution in variable names is disabled by default.
     */
    @Test
    void testReplaceInVariableDisabled() throws IOException {
        values.put("animal.1", "fox");
        values.put("animal.2", "mouse");
        values.put("species", "2");
        final StringSubstitutor sub = new StringSubstitutor(values);
        assertEqualsCharSeq("The ${animal.${species}} jumps over the lazy dog.", replace(sub, "The ${animal.${species}} jumps over the ${target}."));
        assertEqualsCharSeq("The ${animal.${species:-1}} jumps over the lazy dog.", replace(sub, "The ${animal.${species:-1}} jumps over the ${target}."));
    }

    /**
     * Tests complex and recursive substitution in variable names (three levels of nesting).
     */
    @Test
    void testReplaceInVariableRecursive() throws IOException {
        values.put("animal.2", "brown fox");
        values.put("animal.1", "white mouse");
        values.put("color", "white");
        values.put("species.white", "1");
        values.put("species.brown", "2");
        final StringSubstitutor sub = new StringSubstitutor(values);
        sub.setEnableSubstitutionInVariables(true);
        assertEqualsCharSeq("white mouse", replace(sub, "${animal.${species.${color}}}"));
        assertEqualsCharSeq("The white mouse jumps over the lazy dog.", replace(sub, "The ${animal.${species.${color}}} jumps over the ${target}."));
        assertEqualsCharSeq("The brown fox jumps over the lazy dog.",
                replace(sub, "The ${animal.${species.${unknownColor:-brown}}} jumps over the ${target}."));
    }

    /**
     * Tests that a string beginning with the default prefix but missing a closing suffix is left unreplaced.
     */
    @Test
    void testReplaceKeyStartChars() throws IOException {
        final String substring = StringSubstitutor.DEFAULT_VAR_START + "a";
        assertEqualsCharSeq(substring, replace(new StringSubstitutor(values), substring));
    }

    /**
     * Tests that a single character equal to the first character of the default prefix is left unreplaced.
     */
    @Test
    void testReplaceKeyStartChars1Only() throws IOException {
        final String substring = StringSubstitutor.DEFAULT_VAR_START.substring(0, 1);
        assertEqualsCharSeq(substring, replace(new StringSubstitutor(values), substring));
    }

    /**
     * Tests that the full default prefix without any following content or suffix is left unreplaced.
     */
    @Test
    void testReplaceKeyStartChars2Only() throws IOException {
        final String substring = StringSubstitutor.DEFAULT_VAR_START.substring(0, 2);
        assertEqualsCharSeq(substring, replace(new StringSubstitutor(values), substring));
    }

    /**
     * Tests that literal text without a variable prefix is not altered, while later variables are replaced.
     */
    @Test
    void testReplaceNoPrefixNoSuffix() throws IOException {
        doReplace("The animal jumps over the lazy dog.", "The animal jumps over the ${target}.", true);
    }

    /**
     * Tests that a suffix character without a matching prefix is treated as literal text.
     */
    @Test
    void testReplaceNoPrefixSuffix() throws IOException {
        doReplace("The animal} jumps over the lazy dog.", "The animal} jumps over the ${target}.", true);
    }

    /**
     * Tests that a template with no variables is returned unchanged.
     */
    @Test
    void testReplaceNoVariables() throws IOException {
        doNotReplace("The balloon arrived.");
    }

    /**
     * Tests replace with a null template.
     */
    @Test
    void testReplaceNull() throws IOException {
        doNotReplace(null);
    }

    /**
     * Tests that replacing only a portion of a string leaves variables outside the specified range unreplaced.
     */
    @Test
    void testReplacePartialString_noReplace() {
        final StringSubstitutor sub = new StringSubstitutor();
        assertEqualsCharSeq("${animal} jumps", sub.replace(CLASSIC_TEMPLATE, 4, 15));
    }

    /**
     * Tests that an unclosed prefix (no matching suffix) is treated as literal text,
     * while complete variable expressions elsewhere are still replaced.
     */
    @Test
    void testReplacePrefixNoSuffix() throws IOException {
        doReplace("The ${animal jumps over the ${target} lazy dog.", "The ${animal jumps over the ${target} ${target}.", true);
    }

    /**
     * Tests simple recursive replace where variable values themselves contain variable references.
     */
    @Test
    void testReplaceRecursive() throws IOException {
        values.put("animal", "${critter}");
        values.put("target", "${pet}");
        values.put("pet", "${petCharacteristic} dog");
        values.put("petCharacteristic", "lazy");
        values.put("critter", "${critterSpeed} ${critterColor} ${critterType}");
        values.put("critterSpeed", "quick");
        values.put("critterColor", "brown");
        values.put("critterType", "fox");
        doReplace(CLASSIC_RESULT, CLASSIC_TEMPLATE, true);

        values.put("pet", "${petCharacteristicUnknown:-lazy} dog");
        doReplace(CLASSIC_RESULT, CLASSIC_TEMPLATE, true);
    }

    /**
     * Tests simple key replace using the classic animal/target example.
     */
    @Test
    void testReplaceSimple() throws IOException {
        doReplace(CLASSIC_RESULT, CLASSIC_TEMPLATE, true);
    }

    /**
     * Tests simple key replace with a one-character key.
     */
    @Test
    void testReplaceSimpleKeySize1() throws IOException {
        doReplace("1", "${a}", false);
    }

    /**
     * Tests simple key replace with a two-character key.
     */
    @Test
    void testReplaceSimpleKeySize2() throws IOException {
        doReplace("11", "${aa}", false);
    }

    /**
     * Tests simple key replace with a three-character key.
     */
    @Test
    void testReplaceSimpleKeySize3() throws IOException {
        doReplace("111", "${aaa}", false);
    }

    /**
     * Tests that {@code replace(CharSequence)} returns null when the input is null,
     * and that defaults for escape character and preserve-escapes are correct when
     * constructed with a null resolver.
     */
    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StringSubstitutor sub = new StringSubstitutor((StringLookup) null);

        assertNull(sub.replace((CharSequence) null));
        assertFalse(sub.isPreserveEscapes());
        assertEquals('$', sub.getEscapeChar());
    }

    /**
     * Tests that the static {@code replace(Object, Properties)} method throws NullPointerException
     * when the source object is null.
     */
    @Test
    void testReplaceTakingThreeArgumentsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringSubstitutor.replace(null, (Properties) null));
    }

    /**
     * Tests that {@code replace(char[], int, int)} and {@code replace(String, int, int)} throw
     * {@link StringIndexOutOfBoundsException} for invalid offset and length combinations.
     */
    @Test
    void testReplaceThrowsStringIndexOutOfBoundsException() {
        final StringSubstitutor sub = new StringSubstitutor();

        // replace(char[], int, int)
        final char[] emptyCharArray = {};
        // offset greater than array length
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace(emptyCharArray, 0, 1));
        // source != null && (offset > source.length || offset < 0)
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace(emptyCharArray, 1, 0));

        // replace(String, int, int)
        // offset greater than source length
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace("", 1, 1));
        // source != null && offset >= 0 && offset <= source.length() && (length > -offset + source.length() || length < 0)
        assertThrows(StringIndexOutOfBoundsException.class, () -> sub.replace("", 0, 1));
    }

    /**
     * Tests that when a variable's value contains an escaped self-reference, the output matches the
     * original template exactly (no infinite loop, and the escaped reference is preserved literally).
     */
    @Test
    void testReplaceToIdentical() throws IOException {
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");
        doReplace("The ${animal} jumps.", "The ${animal} jumps.", true);
    }

    /**
     * Tests that an unknown variable is left as a literal in the output.
     */
    @Test
    void testReplaceUnknownKey() throws IOException {
        doReplace("The ${person} jumps over the lazy dog.", "The ${person} jumps over the ${target}.", true);
    }

    /**
     * Tests that an unknown variable with a default value resolves the default,
     * leaving other unknown variable references as literals.
     */
    @Test
    void testReplaceUnknownKeyDefaultValue() throws IOException {
        doReplace("The ${person} jumps over the lazy dog. 1234567890.", "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.", true);
    }

    /**
     * Tests that a template consisting only of an unknown variable is returned unchanged.
     */
    @Test
    void testReplaceUnknownKeyOnly() throws IOException {
        final String expected = "${person}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests that an unknown variable with preceding text is returned unchanged.
     */
    @Test
    void testReplaceUnknownKeyOnlyExtraFirst() throws IOException {
        final String expected = ".${person}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests that an unknown variable with trailing text is returned unchanged.
     */
    @Test
    void testReplaceUnknownKeyOnlyExtraLast() throws IOException {
        final String expected = "${person}.";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests that a template consisting only of an unknown single-character variable is returned unchanged.
     */
    @Test
    void testReplaceUnknownShortestKeyOnly() throws IOException {
        final String expected = "${U}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests that an unknown single-character variable with preceding text is returned unchanged.
     */
    @Test
    void testReplaceUnknownShortestKeyOnlyExtraFirst() throws IOException {
        final String expected = ".${U}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests that an unknown single-character variable with trailing text is returned unchanged.
     */
    @Test
    void testReplaceUnknownShortestKeyOnlyExtraLast() throws IOException {
        final String expected = "${U}.";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests simple key replace with one variable in the template.
     */
    @Test
    void testReplaceVariablesCount1() throws IOException {
        doReplace(ACTUAL_ANIMAL, "${animal}", false);
    }

    /**
     * Tests escaping: two consecutive dollar signs before a variable reference reduce to one literal variable string.
     */
    @Test
    void testReplaceVariablesCount1Escaping2To1() throws IOException {
        doReplace("${a}", "$${a}", false);
        doReplace("${animal}", "$${animal}", false);
    }

    /**
     * Tests escaping: three consecutive dollar signs before a variable reference reduce to two dollar signs.
     */
    @Test
    void testReplaceVariablesCount1Escaping3To2() throws IOException {
        doReplace("$${a}", "$$${a}", false);
        doReplace("$${animal}", "$$${animal}", false);
    }

    /**
     * Tests escaping: four consecutive dollar signs before a variable reference reduce to three dollar signs.
     */
    @Test
    void testReplaceVariablesCount1Escaping4To3() throws IOException {
        doReplace("$$${a}", "$$$${a}", false);
        doReplace("$$${animal}", "$$$${animal}", false);
    }

    /**
     * Tests escaping: five consecutive dollar signs before a variable reference reduce to four dollar signs.
     */
    @Test
    void testReplaceVariablesCount1Escaping5To4() throws IOException {
        doReplace("$$$${a}", "$$$$${a}", false);
        doReplace("$$$${animal}", "$$$$${animal}", false);
    }

    /**
     * Tests escaping: six consecutive dollar signs before a variable reference reduce to five dollar signs.
     */
    @Test
    void testReplaceVariablesCount1Escaping6To4() throws IOException {
        doReplace("$$$$${a}", "$$$$$${a}", false);
        doReplace("$$$$${animal}", "$$$$$${animal}", false);
    }

    /**
     * Tests simple key replace with two directly adjacent variables.
     */
    @Test
    void testReplaceVariablesCount2() throws IOException {
        // doTestReplace("12", "${a}${b}", false);
        doReplace("1122", "${aa}${bb}", false);
        doReplace(ACTUAL_ANIMAL + ACTUAL_ANIMAL, "${animal}${animal}", false);
        doReplace(ACTUAL_TARGET + ACTUAL_TARGET, "${target}${target}", false);
        doReplace(ACTUAL_ANIMAL + ACTUAL_TARGET, "${animal}${target}", false);
    }

    /**
     * Tests simple key replace with two variables separated by a space (non-adjacent).
     */
    @Test
    void testReplaceVariablesCount2NonAdjacent() throws IOException {
        doReplace("1 2", "${a} ${b}", false);
        doReplace("11 22", "${aa} ${bb}", false);
        doReplace(ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL, "${animal} ${animal}", false);
        doReplace(ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL, "${animal} ${animal}", false);
        doReplace(ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL, "${animal} ${animal}", false);
    }

    /**
     * Tests simple key replace with three directly adjacent variables.
     */
    @Test
    void testReplaceVariablesCount3() throws IOException {
        doReplace("121", "${a}${b}${a}", false);
        doReplace("112211", "${aa}${bb}${aa}", false);
        doReplace(ACTUAL_ANIMAL + ACTUAL_ANIMAL + ACTUAL_ANIMAL, "${animal}${animal}${animal}", false);
        doReplace(ACTUAL_TARGET + ACTUAL_TARGET + ACTUAL_TARGET, "${target}${target}${target}", false);
    }

    /**
     * Tests simple key replace with three variables separated by spaces (non-adjacent).
     */
    @Test
    void testReplaceVariablesCount3NonAdjacent() throws IOException {
        doReplace("1 2 1", "${a} ${b} ${a}", false);
        doReplace("11 22 11", "${aa} ${bb} ${aa}", false);
        doReplace(ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL, "${animal} ${animal} ${animal}", false);
        doReplace(ACTUAL_TARGET + " " + ACTUAL_TARGET + " " + ACTUAL_TARGET, "${target} ${target} ${target}", false);
    }

    /**
     * Tests interpolation with a comprehensive set of boundary patterns including whitespace keys,
     * nested delimiters, and escape combinations that must not produce substitution, plus select
     * patterns that should be substituted correctly.
     */
    @Test
    void testReplaceWeirdPattens() throws IOException {
        doNotReplace(StringUtils.EMPTY);
        doNotReplace(EMPTY_EXPR);
        doNotReplace("${ }");
        doNotReplace("${\t}");
        doNotReplace("${\n}");
        doNotReplace("${\b}");
        doNotReplace("${");
        doNotReplace("$}");
        doNotReplace("$$}");
        doNotReplace("}");
        doNotReplace("${}$");
        doNotReplace("${}$$");
        doNotReplace("${${");
        doNotReplace("${${}}");
        doNotReplace("${$${}}");
        doNotReplace("${$$${}}");
        doNotReplace("${$$${$}}");
        doNotReplace("${${}}");
        doNotReplace("${${ }}");
        //
        doNotReplace("${$${a}}");
        doNotReplace("${$$${a}}");
        doNotReplace("${${a}}");
        doNotReplace("${${${a}");
        doNotReplace("${ ${a}");
        doNotReplace("${ ${ ${a}");
        //
        doReplace("${1}", "$${${a}}", false);
        doReplace("${ 1}", "$${ ${a}}", false);
        doReplace("${12}", "$${${a}${b}}", false);
        doReplace("${ 1 2 }", "$${ ${a} ${b} }", false);
        doReplace("${${${a}2", "${${${a}${b}", false);
    }

    /**
     * Tests that subclasses can override {@code resolveVariable} to intercept and customize variable resolution.
     */
    @Test
    void testResolveVariable() {
        final TextStringBuilder builder = new TextStringBuilder("Hi ${name}!");
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        final StringSubstitutor sub = new StringSubstitutor(map) {
            @Override
            protected String resolveVariable(final String variableName, final TextStringBuilder buf, final int startPos, final int endPos) {
                assertEquals("name", variableName);
                assertSame(builder, buf);
                assertEquals(3, startPos);
                assertEquals(10, endPos);
                return "jakarta";
            }
        };
        sub.replaceIn(builder);
        assertEqualsCharSeq("Hi jakarta!", builder.toString());
    }

    /**
     * Tests that variables are correctly resolved when the prefix and suffix are the same character.
     */
    @Test
    void testSamePrefixAndSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("greeting", "Hello");
        map.put(" there ", "XXX");
        map.put("name", "commons");
        assertEqualsCharSeq("Hi commons!", StringSubstitutor.replace("Hi @name@!", map, AT_DELIMITER, AT_DELIMITER));
        assertEqualsCharSeq("Hello there commons!", StringSubstitutor.replace("@greeting@ there @name@!", map, AT_DELIMITER, AT_DELIMITER));
    }

    /**
     * Tests the static replace convenience method using the default {@code ${...}} syntax.
     */
    @Test
    void testStaticReplace() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEqualsCharSeq("Hi commons!", StringSubstitutor.replace("Hi ${name}!", map));
    }

    /**
     * Tests the static replace convenience method with custom angle-bracket prefix and suffix.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEqualsCharSeq("Hi commons!", StringSubstitutor.replace("Hi <name>!", map, "<", ">"));
    }

    /**
     * Tests interpolation of system property variables using the static convenience method.
     */
    @Test
    void testStaticReplaceSystemProperties() {
        final TextStringBuilder buf = new TextStringBuilder();
        buf.append("Hi ").append(SystemProperties.getUserName());
        buf.append(", you are working with ");
        buf.append(SystemProperties.getOsName());
        buf.append(", your home directory is ");
        buf.append(SystemProperties.getUserHome()).append('.');
        assertEqualsCharSeq(buf.toString(),
                StringSubstitutor.replaceSystemProperties("Hi ${user.name}, you are " + "working with ${os.name}, your home directory is ${user.home}."));
    }

    /**
     * Tests that system property lookups reflect live updates to system properties.
     */
    @Test
    void testStaticReplaceSystemPropertiesWithUpdate() {
        System.setProperty("foo", "bar1");
        try {
            assertEqualsCharSeq("bar1", StringSubstitutor.replaceSystemProperties("${foo}"));
            System.setProperty("foo", "bar2");
            assertEqualsCharSeq("bar2", StringSubstitutor.replaceSystemProperties("${foo}"));
        } finally {
            System.getProperties().remove("foo");
        }
    }

    /**
     * Tests that a Properties object backed by System properties as defaults correctly resolves variables.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String template = "${doesnotwork}";
        System.setProperty("doesnotwork", "It works!");

        // create a new Properties object with System.getProperties as defaults
        final Properties props = new Properties(System.getProperties());

        assertEqualsCharSeq("It works!", StringSubstitutor.replace(template, props));
    }

    /**
     * Tests that preserve-escapes mode keeps the escape character in the output,
     * while the default mode strips it during substitution.
     */
    @Test
    void testSubstitutePreserveEscape() throws IOException {
        final String template = "${not-escaped} $${escaped}";
        final Map<String, String> map = new HashMap<>();
        map.put("not-escaped", "value");

        final StringSubstitutor sub = new StringSubstitutor(map, "${", "}", '$');
        assertFalse(sub.isPreserveEscapes());
        assertEqualsCharSeq("value ${escaped}", replace(sub, template));

        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEqualsCharSeq("value $${escaped}", replace(sub, template));
    }

    /**
     * Tests that {@code toString()} includes the configured prefix string.
     */
    @Test
    void testToString() {
        final StringSubstitutor s = new StringSubstitutor(null, "prefix", "suffix");
        final String str = s.toString();
        assertTrue(str.contains("\"prefix\""), "Had: " + str);
    }
}
