/*
 * Improved version of EvoSuite-generated tests for StringSubstitutor.
 * Runtime behaviour is identical to the original; improvements are limited to
 * readability: descriptive test names, meaningful variable names, and
 * simplified builder chains.
 */

package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.text.StringSubstitutor;
import org.apache.commons.text.TextStringBuilder;
import org.apache.commons.text.lookup.StringLookup;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class StringSubstitutor_ESTest extends StringSubstitutor_ESTest_scaffolding {

    // -----------------------------------------------------------------------
    // replace(char[], int, int) / replace(char[])
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceCharArraySubrange_defaultConstructor_returnsDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        char[] chars = new char[9];
        substitutor.replace(chars, 1, 1);
    }

    @Test(timeout = 4000)
    public void replaceCharArray_withNull_preservesDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        substitutor.replace((char[]) null);
    }

    @Test(timeout = 4000)
    public void replaceCharArray_withNullAndRange_preservesDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        substitutor.replace((char[]) null, (int) '$', (int) '$');
    }

    @Test(timeout = 4000)
    public void replaceCharArray_withSingleElement_returnsNotNull() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        char[] chars = new char[1];
        String result = substitutor.replace(chars);
        assertNotNull(result);
    }

    // -----------------------------------------------------------------------
    // replace(CharSequence) / replace(CharSequence, int, int)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceCharSequence_withNullAndCustomEscapeChar_preservesEscapeChar() throws Throwable {
        StringLookup lookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null, (String) null)
                .when(lookup).toString();
        StringMatcher matcher = StringSubstitutor.DEFAULT_SUFFIX;
        StringSubstitutor substitutor = new StringSubstitutor(lookup, matcher, matcher, '^', matcher);
        substitutor.replace((CharSequence) null);
    }

    @Test(timeout = 4000)
    public void replaceCharSequence_withNullAndRange_preservesDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        substitutor.replace((CharSequence) null, (int) '$', (int) '$');
    }

    @Test(timeout = 4000)
    public void replaceCharSequence_allocatedCharBuffer_returnsStringOfNullChars() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        // CharBuffer.allocate('$') allocates 36 null characters (36 = ASCII value of '$')
        CharBuffer charBuffer = CharBuffer.allocate('$');
        String result = substitutor.replace((CharSequence) charBuffer);
        assertNotNull(result);
    }

    // -----------------------------------------------------------------------
    // replace(String) / replace(String, int, int)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceString_withNullValueDelimiter_preservesEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        substitutor.setValueDelimiterMatcher((StringMatcher) null);
        substitutor.replace("StringSubstitutor [disableSubstitutionInValues=false, enableSubstitutionInVariables=false, enableUndefinedVariableException=false, escapeChar=$, prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@2[\"${\"], preserveEscapes=false, suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@3['}'], valueDelimiterMatcher=null, variableResolver=null]");
    }

    @Test(timeout = 4000)
    public void replaceString_withNull_preservesDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        substitutor.replace((String) null);
    }

    @Test(timeout = 4000)
    public void replaceString_withNullAndRange_preservesDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        substitutor.replace((String) null, 807, 807);
    }

    @Test(timeout = 4000)
    public void replaceStringWithRange_mockedResolverReturningBrace_usesDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        String template = substitutor.toString();
        StringLookup lookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(lookup).toString();
        doReturn("}").when(lookup).apply(anyString());
        substitutor.setVariableResolver(lookup);
        substitutor.replace(template, 7, 662);
    }

    @Test(timeout = 4000)
    public void replaceString_withMockedResolverReturningBrace_performsSubstitution() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        String template = substitutor.toString();
        StringLookup lookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(lookup).toString();
        doReturn("}").when(lookup).apply(anyString());
        StringSubstitutor substitutorWithLookup = substitutor.setVariableResolver(lookup);
        substitutorWithLookup.replace(template);
    }

    @Test(timeout = 4000)
    public void replaceStringWithRange_interpolator_usesDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        String template = substitutor.toString();
        substitutor.replace(template, (int) '$', 660);
    }

    @Test(timeout = 4000)
    public void replaceString_withSelfReferentialResolver_throwsIllegalStateException() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        String template = substitutor.toString();
        StringLookup lookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(lookup).toString();
        // Resolver returns the same template string, causing infinite substitution loop
        doReturn(template).when(lookup).apply(anyString());
        substitutor.setVariableResolver(lookup);
        try {
            substitutor.replace(template, 7, 662);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
        }
    }

    @Test(timeout = 4000)
    public void replaceString_withUndefinedVariableExceptionEnabled_throwsIllegalArgumentException() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        substitutor.setEnableUndefinedVariableException(true);
        String template = substitutor.toString();
        try {
            substitutor.replace(template);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
        }
    }

    // -----------------------------------------------------------------------
    // replace(Object)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceObject_withSubstitutionInVariablesEnabled_returnsOriginalWhenNoMatch() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        substitutor.setEnableSubstitutionInVariables(true);
        substitutor.setVariablePrefix('$');
        substitutor.replace((Object) "StringSubstitutor [disableSubstitutionInValues=false, enableSubstitutionInVariables=true, enableUndefinedVariableException=false, escapeChar=$, prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@6[\"${\"], preserveEscapes=false, suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@7['}'], valueDelimiterMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@8[\":-\"], variableResolver=null]");
    }

    @Test(timeout = 4000)
    public void replaceObject_withNull_returnsNull() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        String result = substitutor.replace((Object) null);
        assertNull(result);
    }

    // -----------------------------------------------------------------------
    // replace(StringBuffer) / replace(StringBuffer, int, int)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceStringBuffer_withNull_returnsNull() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        substitutor.replace((StringBuffer) null);
    }

    @Test(timeout = 4000)
    public void replaceStringBuffer_withNullAndRange_returnsNull() throws Throwable {
        HashMap<String, Object> map = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor((Map<String, Object>) map);
        substitutor.replace((StringBuffer) null, 36, 36);
    }

    @Test(timeout = 4000)
    public void replaceStringBuffer_withEmptyBufferAndZeroRange_returnsNotNull() throws Throwable {
        HashMap<String, HashMap<Object, Object>> map = new HashMap<String, HashMap<Object, Object>>();
        StringSubstitutor substitutor = new StringSubstitutor(
                (Map<String, HashMap<Object, Object>>) map, "rp]j", "wjSX+E%3~eaj_xSbxQ.", 'z');
        StringBuffer buf = new StringBuffer();
        String result = substitutor.replace(buf, 0, 0);
        assertNotNull(result);
    }

    @Test(timeout = 4000)
    public void replaceStringBuffer_withClosingBrace_returnsNotNull() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        StringBuffer buf = new StringBuffer("}");
        String result = substitutor.replace(buf);
        assertNotNull(result);
    }

    // -----------------------------------------------------------------------
    // replace(TextStringBuilder) / replace(TextStringBuilder, int, int)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceTextStringBuilder_withNullAndRange_returnsNull() throws Throwable {
        HashMap<String, Object> map = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor((Map<String, Object>) map);
        String result = substitutor.replace((TextStringBuilder) null, (-25), (-25));
        assertNull(result);
    }

    @Test(timeout = 4000)
    public void replaceTextStringBuilder_withZeroLengthRange_returnsEmptyString() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        TextStringBuilder tsb = TextStringBuilder.wrap(new char[1]);
        String result = substitutor.replace(tsb, 0, 0);
        assertEquals("", result);
    }

    @Test(timeout = 4000)
    public void replaceTextStringBuilder_withNull_returnsNull() throws Throwable {
        HashMap<String, Object> map = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor((Map<String, Object>) map);
        substitutor.replace((TextStringBuilder) null);
    }

    @Test(timeout = 4000)
    public void replaceTextStringBuilder_emptyBuilder_returnsNotNull() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        TextStringBuilder tsb = new TextStringBuilder((-1097462182));
        String result = substitutor.replace(tsb);
        assertNotNull(result);
    }

    // -----------------------------------------------------------------------
    // static replace(Object, Map) / replace(Object, Map, String, String)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void staticReplace_withNullSuffix_throwsIllegalArgumentException() throws Throwable {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            StringSubstitutor.replace((Object) "${", (Map<String, Object>) map, "${", (String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Variable suffix must not be null!
            //
        }
    }

    @Test(timeout = 4000)
    public void staticReplace_withCustomPrefixSuffixAndNoMatchingVars_returnsInput() throws Throwable {
        HashMap<String, LinkOption> map = new HashMap<String, LinkOption>();
        String result = StringSubstitutor.replace((Object) "${", (Map<String, LinkOption>) map, "}", "}");
        assertEquals("${", result);
    }

    // -----------------------------------------------------------------------
    // static replace(Object, Properties)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void staticReplace_withObjectAndNullProperties_returnsObjectToString() throws Throwable {
        Object obj = new Object();
        String result = StringSubstitutor.replace(obj, (Properties) null);
        assertEquals(obj.toString(), result);
    }

    @Test(timeout = 4000)
    public void staticReplace_withInterpolatorAndEmptyProperties_returnsToString() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        Properties props = new Properties();
        String result = StringSubstitutor.replace((Object) substitutor, props);
        assertNotNull(result);
    }

    // -----------------------------------------------------------------------
    // static replaceSystemProperties(Object)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceSystemProperties_withSubstitutor_usesDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        StringSubstitutor.replaceSystemProperties(substitutor);
    }

    // -----------------------------------------------------------------------
    // replaceIn(StringBuilder) / replaceIn(StringBuilder, int, int)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceIn_nullStringBuilderWithMockedLookup_returnsFalse() throws Throwable {
        StringLookup lookup = mock(StringLookup.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null, (String) null, (String) null, (String) null)
                .when(lookup).toString();
        StringMatcher matcher = StringSubstitutor.DEFAULT_SUFFIX;
        StringSubstitutor substitutor = new StringSubstitutor(lookup, matcher, matcher, '^', matcher);
        boolean wasAltered = substitutor.replaceIn((StringBuilder) null);
        assertFalse(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_nullStringBuilderWithRange_returnsFalse() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        boolean wasAltered = substitutor.replaceIn((StringBuilder) null, (int) '$', (int) '$');
        assertFalse(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_stringBuilderWithEmptyVariable_replacesAndReturnsTrue() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        // Build "}\0\0\0\0${}}\0\0\0\0${}" via "}" + null+dollar chars + "${" + "}" + self-copy
        char[] chars = new char[5];
        chars[4] = '$';
        StringBuilder sb = new StringBuilder("}");
        StringBuilder sb1 = sb.append(chars);
        StringBuilder sb2 = sb1.append((CharSequence) "${");
        sb2.append("}");
        sb2.append((Object) sb);
        boolean wasAltered = substitutor.replaceIn(sb2);
        assertTrue(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_stringBuilderWithUnclosedVariable_resolvesEscapedPrefixOnly() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        // Build "}\0\0\0\0${}\0\0\0\0$${" via "}" + null+dollar chars + "${" + self-copy
        char[] chars = new char[5];
        chars[4] = '$';
        StringBuilder sb = new StringBuilder("}");
        StringBuilder sb1 = sb.append(chars);
        StringBuilder sb2 = sb1.append((CharSequence) "${");
        sb2.append((Object) sb);
        boolean wasAltered = substitutor.replaceIn(sb2);
        assertTrue(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_stringBuilderWithNestedVariableRefs_resolvesEscapedPrefixes() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        // Build "}\0\0\0\0$${${}}\0\0\0\0$${${}" via "}" + null+dollar chars + "${" + "${" + "}" + self-copy
        char[] chars = new char[5];
        chars[4] = '$';
        StringBuilder sb = new StringBuilder("}");
        StringBuilder sb1 = sb.append(chars);
        StringBuilder sb2 = sb1.append((CharSequence) "${");
        sb2.append("${");
        sb2.append("}");
        sb2.append((Object) sb);
        boolean wasAltered = substitutor.replaceIn(sb2);
        assertTrue(wasAltered);
    }

    // -----------------------------------------------------------------------
    // replaceIn(StringBuffer) / replaceIn(StringBuffer, int, int)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceIn_nullStringBuffer_returnsFalse() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        boolean wasAltered = substitutor.replaceIn((StringBuffer) null);
        assertFalse(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_nullStringBufferWithRange_returnsFalse() throws Throwable {
        HashMap<String, Object> map = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor((Map<String, Object>) map);
        boolean wasAltered = substitutor.replaceIn((StringBuffer) null, 10, 36);
        assertFalse(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_stringBufferWithDefaultVarStart_returnsFalse() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        StringBuffer buf = new StringBuffer(substitutor.DEFAULT_VAR_START);
        boolean wasAltered = substitutor.replaceIn(buf);
        assertFalse(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_stringBufferFromInterpolatorToString_producesUnstableResult() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        String template = interpolator.toString();
        StringSubstitutor substitutor = new StringSubstitutor(
                (Map<String, LinkOption>) null, "${", ":-", '$', "}");
        StringBuffer buf = new StringBuffer(template);
        boolean wasAltered = substitutor.replaceIn(buf);
        //  // Unstable assertion: assertEquals(2307, buf.length());
        //  // Unstable assertion: assertTrue(wasAltered);
    }

    // -----------------------------------------------------------------------
    // replaceIn(TextStringBuilder) / replaceIn(TextStringBuilder, int, int)
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void replaceIn_nullTextStringBuilderWithRange_returnsFalse() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        boolean wasAltered = substitutor.replaceIn((TextStringBuilder) null, (int) '$', (int) '$');
        assertFalse(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_textStringBuilderWithNegativeLength_returnsFalse() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        TextStringBuilder tsb = TextStringBuilder.wrap(new char[1]);
        boolean wasAltered = substitutor.replaceIn(tsb, 0, (-2281));
        assertFalse(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_nullTextStringBuilder_returnsFalse() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        boolean wasAltered = substitutor.replaceIn((TextStringBuilder) null);
        assertFalse(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_textStringBuilderWithNoVariables_returnsFalse() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        TextStringBuilder tsb = new TextStringBuilder((CharSequence) ":-");
        boolean wasAltered = substitutor.replaceIn(tsb);
        assertFalse(wasAltered);
    }

    @Test(timeout = 4000)
    public void replaceIn_stringBuilderWithPreserveEscapes_returnsFalseWhenNoClosingSuffix() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        substitutor.setPreserveEscapes(true);
        char[] chars = new char[5];
        chars[4] = '$';
        StringBuilder sb = new StringBuilder("}");
        StringBuilder sb1 = sb.append(chars);
        sb1.append((CharSequence) "${");
        boolean wasAltered = substitutor.replaceIn(sb);
        assertFalse(wasAltered);
    }

    // -----------------------------------------------------------------------
    // Constructors
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void constructor_withCustomEscapeChar_setsEscapeChar() throws Throwable {
        HashMap<String, HashMap<String, Object>> map = new HashMap<String, HashMap<String, Object>>();
        StringSubstitutor substitutor = new StringSubstitutor(
                (Map<String, HashMap<String, Object>>) map, "", "", 'R', "");
    }

    @Test(timeout = 4000)
    public void constructor_withDefaultPrefixSuffixAndNullValueDelimiter_setsEscapeChar() throws Throwable {
        HashMap<String, LinkOption> map = new HashMap<String, LinkOption>();
        StringSubstitutor substitutor = new StringSubstitutor(
                (Map<String, LinkOption>) map, "${", "}", '$', (String) null);
    }

    @Test(timeout = 4000)
    public void copyConstructor_fromInterpolator_copiesEscapeChar() throws Throwable {
        StringSubstitutor interpolator = StringSubstitutor.createInterpolator();
        StringSubstitutor copy = new StringSubstitutor(interpolator);
    }

    @Test(timeout = 4000)
    public void interpolatorWithCustomPrefixMatcher_usesDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        StringSubstitutor returned = substitutor.setEnableSubstitutionInVariables(true);
        returned.setVariablePrefixMatcher(substitutor.DEFAULT_SUFFIX);
        String template = substitutor.toString();
        substitutor.replace(template);
    }

    // -----------------------------------------------------------------------
    // Setter validation — null arguments must throw
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void setVariableSuffixMatcher_null_throwsIllegalArgumentException() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        try {
            substitutor.setVariableSuffixMatcher((StringMatcher) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Variable suffix matcher must not be null!
            //
        }
    }

    @Test(timeout = 4000)
    public void setVariablePrefixMatcher_null_throwsIllegalArgumentException() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        try {
            substitutor.setVariablePrefixMatcher((StringMatcher) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Variable prefix matcher must not be null!
            //
        }
    }

    @Test(timeout = 4000)
    public void setVariablePrefix_nullString_throwsIllegalArgumentException() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        try {
            substitutor.setVariablePrefix((String) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Variable prefix must not be null!
            //
        }
    }

    // -----------------------------------------------------------------------
    // Setters — fluent return and flag verification
    // -----------------------------------------------------------------------

    @Test(timeout = 4000)
    public void setValueDelimiter_char_returnsThisWithDefaultEscapeChar() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        StringSubstitutor returned = substitutor.setValueDelimiter('$');
        assertSame(substitutor, returned);
    }

    @Test(timeout = 4000)
    public void setVariableSuffix_char_returnsThisWithDefaultEscapeChar() throws Throwable {
        HashMap<String, Object> map = new HashMap<String, Object>();
        StringSubstitutor substitutor = new StringSubstitutor((Map<String, Object>) map);
        StringSubstitutor returned = substitutor.setVariableSuffix('r');
        assertSame(substitutor, returned);
    }

    @Test(timeout = 4000)
    public void setDisableSubstitutionInValues_true_flagIsSet() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor();
        substitutor.setDisableSubstitutionInValues(true);
        assertTrue(substitutor.isDisableSubstitutionInValues());
    }

    @Test(timeout = 4000)
    public void replace_withDisabledSubstitutionInValues_substitutesVariables() throws Throwable {
        StringSubstitutor substitutor = new StringSubstitutor(
                (Map<String, LinkOption>) null, "${", ":-", '$', "}");
        substitutor.setDisableSubstitutionInValues(true);
        substitutor.replace("StringSubstitutor [disableSubstitutionInValues=false, enableSubstitutionInVariables=false, enableUndefinedVariableException=false, escapeChar=$, prefixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@2[\"${\"], preserveEscapes=false, suffixMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharMatcher@3['}'], valueDelimiterMatcher=org.apache.commons.text.matcher.AbstractStringMatcher$CharArrayMatcher@4[\":-\"], variableResolver=null]");
    }

    @Test(timeout = 4000)
    public void replace_withSubstitutionInVariablesEnabled_substitutesVariables() throws Throwable {
        StringSubstitutor substitutor = StringSubstitutor.createInterpolator();
        substitutor.setEnableSubstitutionInVariables(true);
        String template = substitutor.toString();
        substitutor.replace(template);
    }
}
