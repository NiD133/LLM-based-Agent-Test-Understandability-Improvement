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
// Tests run in alphabetical method-name order so that failures are easy to locate.
@TestMethodOrder(MethodOrderer.MethodName.class)
public class StringSubstitutorTest {

    /** Value bound to the {@code animal} variable in the shared {@link #values} map. */
    private static final String ACTUAL_ANIMAL = "quick brown fox";

    /** Value bound to the {@code target} variable in the shared {@link #values} map. */
    private static final String ACTUAL_TARGET = "lazy dog";

    /** Expected output of {@link #CLASSIC_TEMPLATE} after substitution. */
    private static final String CLASSIC_RESULT = "The quick brown fox jumps over the lazy dog.";

    /** A representative template using the default {@code ${...}} variable syntax. */
    private static final String CLASSIC_TEMPLATE = "The ${animal} jumps over the ${target}.";

    /** A variable expression with an empty (missing) variable name. */
    private static final String EMPTY_EXPR = "${}";

    /** Variable values shared by tests; (re)initialized before each test in {@link #setUp()}. */
    protected Map<String, String> values;

    /**
     * Asserts equality of two char sequences, reporting both lengths on failure to aid debugging.
     */
    private void assertEqualsCharSeq(final CharSequence expected, final CharSequence actual) {
        assertEquals(expected, actual,
                () -> String.format("expected.length()=%,d, actual.length()=%,d", StringUtils.length(expected), StringUtils.length(actual)));
    }

    /**
     * Asserts that the given template is left unchanged by a substitutor backed by {@link #values}.
     */
    protected void doNotReplace(final String replaceTemplate) throws IOException {
        doTestNoReplace(new StringSubstitutor(values), replaceTemplate);
    }

    /**
     * Asserts that a substitutor backed by {@link #values} turns {@code replaceTemplate} into {@code expectedResult}.
     *
     * @param substring whether the template also supports partial (offset/length) substitution.
     */
    protected void doReplace(final String expectedResult, final String replaceTemplate, final boolean substring) throws IOException {
        doTestReplace(new StringSubstitutor(values), expectedResult, replaceTemplate, substring);
    }

    /**
     * Verifies that {@code substitutor} performs no substitution on {@code replaceTemplate}.
     * <p>
     * When {@code replaceTemplate} is {@code null}, every {@code replace}/{@code replaceIn} overload is exercised to
     * confirm null inputs are handled (returning {@code null} or {@code false}). Otherwise, the template is expected to
     * pass through both {@code replace(String)} and {@code replaceIn(TextStringBuilder)} unchanged.
     * </p>
     */
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

    /**
     * Verifies that {@code substitutor} substitutes {@code replaceTemplate} into {@code expectedResult} consistently
     * across every input type accepted by {@link StringSubstitutor}.
     * <p>
     * The same template is fed through each {@code replace(...)} overload (String, char[], StringBuffer, StringBuilder,
     * TextStringBuilder and Object) and each in-place {@code replaceIn(...)} overload, asserting the same result every
     * time.
     * </p>
     * <p>
     * When {@code substring} is {@code true}, the template is also processed with offset/length arguments that skip the
     * first and last characters. {@code expectedShortResult} is the expected output of that partial substitution; note
     * that the in-place {@code replaceIn(..., offset, length)} variants still yield the full result because the skipped
     * remainder is left untouched.
     * </p>
     */
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

        // replace using TextStringBuilder
        TextStringBuilder textStringBuilder = new TextStringBuilder(replaceTemplate);
        assertEquals(expectedResult, sub.replace(textStringBuilder));
        if (substring) {
            assertEquals(expectedShortResult, sub.replace(textStringBuilder, 1, textStringBuilder.length() - 2));
        }

        // replace using Object (toString returns the template)
        final MutableObject<String> obj = new MutableObject<>(replaceTemplate);
        assertEquals(expectedResult, sub.replace(obj));

        // replace in StringBuffer (mutated in place)
        stringBuffer = new StringBuffer(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuffer), replaceTemplate);
        assertEquals(expectedResult, stringBuffer.toString());
        if (substring) {
            stringBuffer = new StringBuffer(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuffer, 1, stringBuffer.length() - 2));
            assertEquals(expectedResult, stringBuffer.toString()); // expect full result as remainder is untouched
        }

        // replace in StringBuilder (mutated in place)
        stringBuilder = new StringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(stringBuilder));
        assertEquals(expectedResult, stringBuilder.toString());
        if (substring) {
            stringBuilder = new StringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(stringBuilder, 1, stringBuilder.length() - 2));
            assertEquals(expectedResult, stringBuilder.toString()); // expect full result as remainder is untouched
        }

        // replace in TextStringBuilder (mutated in place)
        textStringBuilder = new TextStringBuilder(replaceTemplate);
        assertTrue(sub.replaceIn(textStringBuilder));
        assertEquals(expectedResult, textStringBuilder.toString());
        if (substring) {
            textStringBuilder = new TextStringBuilder(replaceTemplate);
            assertTrue(sub.replaceIn(textStringBuilder, 1, textStringBuilder.length() - 2));
            assertEquals(expectedResult, textStringBuilder.toString()); // expect full result as remainder is untouched
        }
    }

    /**
     * Performs the basic string replacement. Subclasses may override to vary how the replacement is invoked.
     *
     * @throws IOException Thrown by subclasses.
     */
    protected String replace(final StringSubstitutor stringSubstitutor, final String template) throws IOException {
        return stringSubstitutor.replace(template);
    }

    @BeforeEach
    public void setUp() throws Exception {
        values = new HashMap<>();
        // Short keys/values that stress single- and multi-character variable names.
        values.put("a", "1");
        values.put("aa", "11");
        values.put("aaa", "111");
        values.put("b", "2");
        values.put("bb", "22");
        values.put("bbb", "222");
        values.put("a2b", "b");
        // Normal, human-readable keys/values.
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
        final StringSubstitutor s = new StringSubstitutor(parameters, "prefix", "suffix");
        assertNull(s.getStringLookup().apply("X"));
        assertNull(s.getStringLookup().lookup("X"));
    }

    @Test
    void testConstructorStringSubstitutor() {
        // Configure every option on the source instance...
        final StringSubstitutor source = new StringSubstitutor();
        source.setDisableSubstitutionInValues(true);
        source.setEnableSubstitutionInVariables(true);
        source.setEnableUndefinedVariableException(true);
        source.setEscapeChar('e');
        source.setValueDelimiter('d');
        source.setVariablePrefix('p');
        source.setVariableResolver(StringLookupFactory.INSTANCE.nullStringLookup());
        source.setVariableSuffix('s');

        // ...then verify the copy constructor carries every option across.
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
        map.put("name", "<name>"); // value references itself, creating a cycle
        assertThrows(IllegalStateException.class, () -> StringSubstitutor.replace("Hi <name>.", map, "<", ">"));
    }

    /**
     * Tests the escape character getter and setter.
     */
    @Test
    void testGetSetEscape() {
        final StringSubstitutor sub = new StringSubstitutor();
        assertEquals('$', sub.getEscapeChar());
        sub.setEscapeChar('<');
        assertEquals('<', sub.getEscapeChar());
    }

    /**
     * Test for LANG-1055: StringSubstitutor.replaceSystemProperties does not work consistently
     */
    @Test
    void testLANG1055() {
        System.setProperty("test_key", "test_value");

        // Replacing via the System properties map must match the dedicated system-properties shortcut.
        final String expected = StringSubstitutor.replace("test_key=${test_key}", System.getProperties());
        final String actual = StringSubstitutor.replaceSystemProperties("test_key=${test_key}");
        assertEquals(expected, actual);
    }

    /**
     * Tests interpolation with weird boundary patterns that must NOT be replaced.
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
     * Tests interpolation with weird boundary patterns.
     */
    @Test
    void testReplace_JiraText178_WeirdPatterns2() throws IOException {
        doReplace("${1}", "$${${a}}", false);
    }

    /**
     * Tests interpolation with weird boundary patterns.
     */
    @Test
    void testReplace_JiraText178_WeirdPatterns3() throws IOException {
        doReplace("${${a}", "$${${a}", false); // not "$${1" or "${1"
    }

    /**
     * Tests two adjacent variables at the end of the template.
     */
    @Test
    void testReplaceAdjacentAtEnd() throws IOException {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StringSubstitutor sub = new StringSubstitutor(values);
        assertEqualsCharSeq("Amount is GBP12.50", replace(sub, "Amount is ${code}${amount}"));
    }

    /**
     * Tests two adjacent variables at the start of the template.
     */
    @Test
    void testReplaceAdjacentAtStart() throws IOException {
        values.put("code", "GBP");
        values.put("amount", "12.50");
        final StringSubstitutor sub = new StringSubstitutor(values);
        assertEqualsCharSeq("GBP12.50 charged", replace(sub, "${code}${amount} charged"));
    }

    /**
     * Tests that mutating the backing map after construction affects later replacements (not recommended usage).
     */
    @Test
    void testReplaceChangedMap() throws IOException {
        final StringSubstitutor sub = new StringSubstitutor(values);
        // no map change
        final String template = CLASSIC_TEMPLATE;
        assertEqualsCharSeq(CLASSIC_RESULT, replace(sub, template));
        // map change is reflected on the next replace
        values.put("target", "moon");
        assertEqualsCharSeq("The quick brown fox jumps over the moon.", replace(sub, template));
    }

    /**
     * Tests complex escaping combinations.
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
     * Tests an empty variable name embedded in a larger template.
     */
    @Test
    void testReplaceEmptyKey() throws IOException {
        doReplace("The ${} jumps over the lazy dog.", "The ${} jumps over the ${target}.", true);
    }

    /**
     * Tests an empty variable name preceded by extra text.
     */
    @Test
    void testReplaceEmptyKeyExtraFirst() throws IOException {
        assertEqualsCharSeq("." + EMPTY_EXPR, replace(new StringSubstitutor(values), "." + EMPTY_EXPR));
    }

    /**
     * Tests an empty variable name followed by extra text.
     */
    @Test
    void testReplaceEmptyKeyExtraLast() throws IOException {
        assertEqualsCharSeq(EMPTY_EXPR + ".", replace(new StringSubstitutor(values), EMPTY_EXPR + "."));
    }

    /**
     * Tests an empty variable name as the whole template.
     */
    @Test
    void testReplaceEmptyKeyOnly() throws IOException {
        assertEquals(EMPTY_EXPR, replace(new StringSubstitutor(values), EMPTY_EXPR));
    }

    /**
     * Tests an empty variable name via the no-replace helper.
     */
    @Test
    void testReplaceEmptyKeyShortest() throws IOException {
        doNotReplace(EMPTY_EXPR);
    }

    /**
     * Tests an empty variable name with a default value, embedded in a template.
     */
    @Test
    void testReplaceEmptyKeyWithDefault() throws IOException {
        doReplace("The animal jumps over the lazy dog.", "The ${:-animal} jumps over the ${target}.", true);
    }

    /**
     * Tests an empty variable name with a default value as the whole template.
     */
    @Test
    void testReplaceEmptyKeyWithDefaultOnly() throws IOException {
        doReplace("animal", "${:-animal}", false);
    }

    /**
     * Tests an empty variable name with an empty default value.
     */
    @Test
    void testReplaceEmptyKeyWithDefaultOnlyEmpty() throws IOException {
        doReplace("", "${:-}", false);
    }

    /**
     * Tests an empty variable name with a single-character default value.
     */
    @Test
    void testReplaceEmptyKeyWithDefaultOnlyShortest() throws IOException {
        doReplace("a", "${:-a}", false);
    }

    /**
     * Tests that an empty input string is returned unchanged.
     */
    @Test
    void testReplaceEmptyString() throws IOException {
        doNotReplace(StringUtils.EMPTY);
    }

    /**
     * Tests escaping of variable references.
     */
    @Test
    void testReplaceEscaping() throws IOException {
        doReplace("The ${animal} jumps over the lazy dog.", "The $${animal} jumps over the ${target}.", true);
        doReplace("${a}", "$${a}", false);
        doReplace("${a${a}}", "$${a$${a}}", false);
        doReplace("${a${a${a}}}", "$${a$${a$${a}}}", false);
    }

    /**
     * Tests that undefined variables throw when the fail-on-undefined flag is enabled, while defaults still resolve.
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
     * Tests fail-on-undefined behavior when substitution within variable names is also enabled.
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
     * Tests that an incomplete prefix (no "$") is left as literal text.
     */
    @Test
    void testReplaceIncompletePrefix() throws IOException {
        doReplace("The {animal} jumps over the lazy dog.", "The {animal} jumps over the ${target}.", true);
    }

    @Test
    void testReplaceInTakingStringBufferWithNonNull() {
        final StringSubstitutor strSubstitutor = new StringSubstitutor(new HashMap<>(), "WV@i#y?N*[", "WV@i#y?N*[", '*');

        assertFalse(strSubstitutor.isPreserveEscapes());
        assertFalse(strSubstitutor.replaceIn(new StringBuffer("WV@i#y?N*[")));
        assertEquals('*', strSubstitutor.getEscapeChar());
    }

    @Test
    void testReplaceInTakingStringBuilderWithNonNull() {
        final StringLookup strLookup = StringLookupFactory.INSTANCE.systemPropertyStringLookup();
        final StringSubstitutor strSubstitutor = new StringSubstitutor(strLookup, "b<H", "b<H", '\'');
        final StringBuilder stringBuilder = new StringBuilder((CharSequence) "b<H");

        assertEquals('\'', strSubstitutor.getEscapeChar());
        assertFalse(strSubstitutor.replaceIn(stringBuilder));
    }

    @Test
    void testReplaceInTakingStringBuilderWithNull() {
        final Map<String, Object> map = new HashMap<>();
        final StringSubstitutor strSubstitutor = new StringSubstitutor(map, StringUtils.EMPTY, StringUtils.EMPTY, 'T', "K+<'f");

        assertFalse(strSubstitutor.replaceIn((StringBuilder) null));
    }

    @Test
    void testReplaceInTakingTwoAndThreeIntsReturningFalse() {
        final Map<String, Object> hashMap = new HashMap<>();
        final StringLookup mapStringLookup = StringLookupFactory.INSTANCE.mapStringLookup(hashMap);
        final StringMatcher strMatcher = StringMatcherFactory.INSTANCE.tabMatcher();
        final StringSubstitutor strSubstitutor = new StringSubstitutor(mapStringLookup, strMatcher, strMatcher, 'b', strMatcher);

        assertFalse(strSubstitutor.replaceIn((StringBuilder) null, 1315, -1369));
        assertEquals('b', strSubstitutor.getEscapeChar());
        assertFalse(strSubstitutor.isPreserveEscapes());
    }

    /**
     * Tests substituting a variable inside a variable name when the feature is enabled.
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
     * Tests that substitution within variable names is disabled by default.
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
     * Tests complex and recursive substitution within variable names.
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
     * Tests that the full default variable-start sequence followed by a name is left untouched.
     */
    @Test
    void testReplaceKeyStartChars() throws IOException {
        final String substring = StringSubstitutor.DEFAULT_VAR_START + "a";
        assertEqualsCharSeq(substring, replace(new StringSubstitutor(values), substring));
    }

    /**
     * Tests that the first character of the default variable-start sequence is left untouched.
     */
    @Test
    void testReplaceKeyStartChars1Only() throws IOException {
        final String substring = StringSubstitutor.DEFAULT_VAR_START.substring(0, 1);
        assertEqualsCharSeq(substring, replace(new StringSubstitutor(values), substring));
    }

    /**
     * Tests that the first two characters of the default variable-start sequence are left untouched.
     */
    @Test
    void testReplaceKeyStartChars2Only() throws IOException {
        final String substring = StringSubstitutor.DEFAULT_VAR_START.substring(0, 2);
        assertEqualsCharSeq(substring, replace(new StringSubstitutor(values), substring));
    }

    /**
     * Tests a template with no prefix or suffix markers.
     */
    @Test
    void testReplaceNoPrefixNoSuffix() throws IOException {
        doReplace("The animal jumps over the lazy dog.", "The animal jumps over the ${target}.", true);
    }

    /**
     * Tests a template containing a suffix marker but no prefix.
     */
    @Test
    void testReplaceNoPrefixSuffix() throws IOException {
        doReplace("The animal} jumps over the lazy dog.", "The animal} jumps over the ${target}.", true);
    }

    /**
     * Tests a template with no variables.
     */
    @Test
    void testReplaceNoVariables() throws IOException {
        doNotReplace("The balloon arrived.");
    }

    /**
     * Tests that a null template returns null.
     */
    @Test
    void testReplaceNull() throws IOException {
        doNotReplace(null);
    }

    /**
     * Tests partial replacement of a substring that contains no resolvable change.
     */
    @Test
    void testReplacePartialString_noReplace() {
        final StringSubstitutor sub = new StringSubstitutor();
        assertEqualsCharSeq("${animal} jumps", sub.replace(CLASSIC_TEMPLATE, 4, 15));
    }

    /**
     * Tests a template containing a prefix marker but no suffix.
     */
    @Test
    void testReplacePrefixNoSuffix() throws IOException {
        doReplace("The ${animal jumps over the ${target} lazy dog.", "The ${animal jumps over the ${target} ${target}.", true);
    }

    /**
     * Tests recursive replacement, where variable values themselves contain variables.
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
     * Tests a simple, full-template key replacement.
     */
    @Test
    void testReplaceSimple() throws IOException {
        doReplace(CLASSIC_RESULT, CLASSIC_TEMPLATE, true);
    }

    /**
     * Tests replacement of a single-character variable name.
     */
    @Test
    void testReplaceSimpleKeySize1() throws IOException {
        doReplace("1", "${a}", false);
    }

    /**
     * Tests replacement of a two-character variable name.
     */
    @Test
    void testReplaceSimpleKeySize2() throws IOException {
        doReplace("11", "${aa}", false);
    }

    /**
     * Tests replacement of a three-character variable name.
     */
    @Test
    void testReplaceSimpleKeySize3() throws IOException {
        doReplace("111", "${aaa}", false);
    }

    @Test
    void testReplaceTakingCharSequenceReturningNull() {
        final StringSubstitutor strSubstitutor = new StringSubstitutor((StringLookup) null);

        assertNull(strSubstitutor.replace((CharSequence) null));
        assertFalse(strSubstitutor.isPreserveEscapes());
        assertEquals('$', strSubstitutor.getEscapeChar());
    }

    @Test
    void testReplaceTakingThreeArgumentsThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringSubstitutor.replace(null, (Properties) null));
    }

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
     * Tests recursion that ultimately produces output identical to the input.
     */
    @Test
    void testReplaceToIdentical() throws IOException {
        values.put("animal", "$${${thing}}");
        values.put("thing", "animal");
        doReplace("The ${animal} jumps.", "The ${animal} jumps.", true);
    }

    /**
     * Tests that an unknown variable is left untouched.
     */
    @Test
    void testReplaceUnknownKey() throws IOException {
        doReplace("The ${person} jumps over the lazy dog.", "The ${person} jumps over the ${target}.", true);
    }

    /**
     * Tests that an unknown variable with a default value resolves to the default.
     */
    @Test
    void testReplaceUnknownKeyDefaultValue() throws IOException {
        doReplace("The ${person} jumps over the lazy dog. 1234567890.", "The ${person} jumps over the ${target}. ${undefined.number:-1234567890}.", true);
    }

    /**
     * Tests an unknown variable as the whole template.
     */
    @Test
    void testReplaceUnknownKeyOnly() throws IOException {
        final String expected = "${person}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests an unknown variable preceded by extra text.
     */
    @Test
    void testReplaceUnknownKeyOnlyExtraFirst() throws IOException {
        final String expected = ".${person}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests an unknown variable followed by extra text.
     */
    @Test
    void testReplaceUnknownKeyOnlyExtraLast() throws IOException {
        final String expected = "${person}.";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests a single-character unknown variable as the whole template.
     */
    @Test
    void testReplaceUnknownShortestKeyOnly() throws IOException {
        final String expected = "${U}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests a single-character unknown variable preceded by extra text.
     */
    @Test
    void testReplaceUnknownShortestKeyOnlyExtraFirst() throws IOException {
        final String expected = ".${U}";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests a single-character unknown variable followed by extra text.
     */
    @Test
    void testReplaceUnknownShortestKeyOnlyExtraLast() throws IOException {
        final String expected = "${U}.";
        assertEqualsCharSeq(expected, replace(new StringSubstitutor(values), expected));
    }

    /**
     * Tests a template containing exactly one variable.
     */
    @Test
    void testReplaceVariablesCount1() throws IOException {
        doReplace(ACTUAL_ANIMAL, "${animal}", false);
    }

    /**
     * Tests collapsing two escape characters down to one.
     */
    @Test
    void testReplaceVariablesCount1Escaping2To1() throws IOException {
        doReplace("${a}", "$${a}", false);
        doReplace("${animal}", "$${animal}", false);
    }

    /**
     * Tests collapsing three escape characters down to two.
     */
    @Test
    void testReplaceVariablesCount1Escaping3To2() throws IOException {
        doReplace("$${a}", "$$${a}", false);
        doReplace("$${animal}", "$$${animal}", false);
    }

    /**
     * Tests collapsing four escape characters down to three.
     */
    @Test
    void testReplaceVariablesCount1Escaping4To3() throws IOException {
        doReplace("$$${a}", "$$$${a}", false);
        doReplace("$$${animal}", "$$$${animal}", false);
    }

    /**
     * Tests collapsing five escape characters down to four.
     */
    @Test
    void testReplaceVariablesCount1Escaping5To4() throws IOException {
        doReplace("$$$${a}", "$$$$${a}", false);
        doReplace("$$$${animal}", "$$$$${animal}", false);
    }

    /**
     * Tests collapsing six escape characters down to four.
     */
    @Test
    void testReplaceVariablesCount1Escaping6To4() throws IOException {
        doReplace("$$$$${a}", "$$$$$${a}", false);
        doReplace("$$$$${animal}", "$$$$$${animal}", false);
    }

    /**
     * Tests a template with two adjacent variables.
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
     * Tests a template with two non-adjacent variables.
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
     * Tests a template with three adjacent variables.
     */
    @Test
    void testReplaceVariablesCount3() throws IOException {
        doReplace("121", "${a}${b}${a}", false);
        doReplace("112211", "${aa}${bb}${aa}", false);
        doReplace(ACTUAL_ANIMAL + ACTUAL_ANIMAL + ACTUAL_ANIMAL, "${animal}${animal}${animal}", false);
        doReplace(ACTUAL_TARGET + ACTUAL_TARGET + ACTUAL_TARGET, "${target}${target}${target}", false);
    }

    /**
     * Tests a template with three non-adjacent variables.
     */
    @Test
    void testReplaceVariablesCount3NonAdjacent() throws IOException {
        doReplace("1 2 1", "${a} ${b} ${a}", false);
        doReplace("11 22 11", "${aa} ${bb} ${aa}", false);
        doReplace(ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL + " " + ACTUAL_ANIMAL, "${animal} ${animal} ${animal}", false);
        doReplace(ACTUAL_TARGET + " " + ACTUAL_TARGET + " " + ACTUAL_TARGET, "${target} ${target} ${target}", false);
    }

    /**
     * Tests interpolation with a broad mix of weird boundary patterns, covering both no-op and replaced cases.
     */
    @Test
    void testReplaceWeirdPattens() throws IOException {
        // Patterns that should be left unchanged.
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
        // Patterns that should be replaced.
        doReplace("${1}", "$${${a}}", false);
        doReplace("${ 1}", "$${ ${a}}", false);
        doReplace("${12}", "$${${a}${b}}", false);
        doReplace("${ 1 2 }", "$${ ${a} ${b} }", false);
        doReplace("${${${a}2", "${${${a}${b}", false);
    }

    /**
     * Tests overriding the protected {@code resolveVariable} hook.
     */
    @Test
    void testResolveVariable() {
        final TextStringBuilder builder = new TextStringBuilder("Hi ${name}!");
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        final StringSubstitutor sub = new StringSubstitutor(map) {
            @Override
            protected String resolveVariable(final String variableName, final TextStringBuilder buf, final int startPos, final int endPos) {
                // The framework should pass us the variable name and its exact position within the buffer.
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

    @Test
    void testSamePrefixAndSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("greeting", "Hello");
        map.put(" there ", "XXX");
        map.put("name", "commons");
        assertEqualsCharSeq("Hi commons!", StringSubstitutor.replace("Hi @name@!", map, "@", "@"));
        assertEqualsCharSeq("Hello there commons!", StringSubstitutor.replace("@greeting@ there @name@!", map, "@", "@"));
    }

    /**
     * Tests the static replace shortcut using a map.
     */
    @Test
    void testStaticReplace() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEqualsCharSeq("Hi commons!", StringSubstitutor.replace("Hi ${name}!", map));
    }

    /**
     * Tests the static replace shortcut using a map plus custom prefix/suffix.
     */
    @Test
    void testStaticReplacePrefixSuffix() {
        final Map<String, String> map = new HashMap<>();
        map.put("name", "commons");
        assertEqualsCharSeq("Hi commons!", StringSubstitutor.replace("Hi <name>!", map, "<", ">"));
    }

    /**
     * Tests interpolation against the current system properties.
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
     * Tests that system-property interpolation reflects updates between calls.
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
     * Tests replacement against a Properties object that falls back to System properties as its defaults.
     */
    @Test
    void testSubstituteDefaultProperties() {
        final String org = "${doesnotwork}";
        System.setProperty("doesnotwork", "It works!");

        // create a new Properties object with the System.getProperties as default
        final Properties props = new Properties(System.getProperties());

        assertEqualsCharSeq("It works!", StringSubstitutor.replace(org, props));
    }

    /**
     * Tests the preserve-escapes flag, which controls whether the escape character survives substitution.
     */
    @Test
    void testSubstitutePreserveEscape() throws IOException {
        final String org = "${not-escaped} $${escaped}";
        final Map<String, String> map = new HashMap<>();
        map.put("not-escaped", "value");

        final StringSubstitutor sub = new StringSubstitutor(map, "${", "}", '$');
        assertFalse(sub.isPreserveEscapes());
        assertEqualsCharSeq("value ${escaped}", replace(sub, org));

        sub.setPreserveEscapes(true);
        assertTrue(sub.isPreserveEscapes());
        assertEqualsCharSeq("value $${escaped}", replace(sub, org));
    }

    @Test
    void testToString() {
        final StringSubstitutor s = new StringSubstitutor(null, "prefix", "suffix");
        final String str = s.toString();
        assertTrue(str.contains("\"prefix\""), "Had: " + str);
    }
}
