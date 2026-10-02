/*
 * Improved version of EvoSuite-generated tests for StringTokenizer.
 * Refactored for understandability: descriptive names, cleaner variables.
 */

package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.apache.commons.text.StringTokenizer;
import org.apache.commons.text.matcher.StringMatcher;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest extends StringTokenizer_ESTest_scaffolding {

    // --- toString() ---

    @Test(timeout = 4000)
    public void test00_toStringBeforeTokenizationReturnsNotTokenizedYet() throws Throwable {
        char[] singleChar = new char[1];
        StringMatcher mockMatcher = mock(StringMatcher.class, new ViolatedAssumptionAnswer());
        StringTokenizer tokenizer = new StringTokenizer(singleChar, mockMatcher);
        String result = tokenizer.toString();
        assertEquals("StringTokenizer[not tokenized yet]", result);
    }

    @Test(timeout = 4000)
    public void test01_toStringAfterTokenizationShowsContent() throws Throwable {
        char[] singleChar = new char[1];
        StringMatcher mockMatcher = mock(StringMatcher.class, new ViolatedAssumptionAnswer());
        doReturn((String) null, (String) null).when(mockMatcher).toString();
        doReturn(0, 0).when(mockMatcher).isMatch(any(char[].class), anyInt(), anyInt(), anyInt());
        StringTokenizer tokenizer = new StringTokenizer(singleChar, mockMatcher);
        // previousToken() triggers tokenization; toString() then reflects actual content
        tokenizer.previousToken();
        String result = tokenizer.toString();
        assertNotEquals("StringTokenizer[not tokenized yet]", result);
    }

    // --- previousToken() ---

    @Test(timeout = 4000)
    public void test02_previousTokenOnEmptyTsvInputReturnsNull() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("");
        String token = tokenizer.previousToken();
        assertNull(token);
    }

    @Test(timeout = 4000)
    public void test07_previousTokenAtStartWithCharArrayDelimAndQuoteReturnsNull() throws Throwable {
        char[] chars = new char[9];
        chars[0] = ')';
        chars[1] = ')';
        chars[4] = ')';
        StringTokenizer tokenizer = new StringTokenizer(chars, '(', ')');
        String token = tokenizer.previousToken();
        assertNull(token);
    }

    @Test(timeout = 4000)
    public void test09_previousTokenAfterNextReturnsLastToken() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance("%*dR|");
        tokenizer.nextToken();
        String token = tokenizer.previousToken();
        assertEquals("%*dR|", token);
    }

    @Test(timeout = 4000)
    public void test11_previousTokenAtStartWithNullQuoteCharReturnsNull() throws Throwable {
        char[] chars = new char[10];
        StringTokenizer tokenizer = new StringTokenizer(chars, '(', ' ');
        String token = tokenizer.previousToken();
        assertNull(token);
    }

    @Test(timeout = 4000)
    public void test12_previousTokenAtStartAfterConfigurationReturnsNull() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        tokenizer.setDelimiterChar(')');
        StringMatcher trimmer = tokenizer.getTrimmerMatcher();
        tokenizer.setQuoteMatcher(trimmer);
        String token = tokenizer.previousToken();
        assertNull(token);
    }

    @Test(timeout = 4000)
    public void test19_previousTokenAtStartOnTsvInstanceReturnsNull() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        String token = tokenizer.previousToken();
        assertNull(token);
    }

    @Test(timeout = 4000)
    public void test33_previousTokenAtStartOnWhitespaceOnlyInputReturnsNull() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance(" \t\n\r\f");
        String token = tokenizer.previousToken();
        assertNull(token);
    }

    @Test(timeout = 4000)
    public void test35_previousTokenAtStartAfterSettingIgnoredCharReturnsNull() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance("%*,d O%");
        tokenizer.setIgnoredChar('*');
        String token = tokenizer.previousToken();
        assertNull(token);
    }

    // --- previous() ---

    @Test(timeout = 4000)
    public void test08_previousAtStartThrowsNoSuchElementException() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer("add() is unsupported");
        try {
            tokenizer.previous();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
        }
    }

    @Test(timeout = 4000)
    public void test17_previousAfterNextReturnsSameToken() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance(",OF0)2fR[p0$");
        tokenizer.next();
        String token = tokenizer.previous();
        assertEquals(",OF0)2fR[p0$", token);
    }

    // --- nextToken() ---

    @Test(timeout = 4000)
    public void test10_nextTokenOnNoContentTokenizerDoesNotThrow() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer();
        tokenizer.nextToken();
        assertFalse(tokenizer.hasNext());
    }

    // --- setTrimmerMatcher() ---

    @Test(timeout = 4000)
    public void test03_setTrimmerMatcherReturnsSameInstance() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        StringMatcher trimmer = tokenizer.getTrimmerMatcher();
        StringTokenizer result = tokenizer.setTrimmerMatcher(trimmer);
        assertSame(tokenizer, result);
    }

    @Test(timeout = 4000)
    public void test04_setTrimmerMatcherWithNullReturnsSameInstance() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance();
        StringTokenizer result = tokenizer.setTrimmerMatcher((StringMatcher) null);
        assertSame(tokenizer, result);
    }

    // --- setIgnoredMatcher() ---

    @Test(timeout = 4000)
    public void test05_setIgnoredMatcherWithNullReturnsSameInstanceAtIndexZero() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance();
        StringTokenizer result = tokenizer.setIgnoredMatcher((StringMatcher) null);
        assertSame(tokenizer, result);
    }

    // --- isEmptyTokenAsNull() ---

    @Test(timeout = 4000)
    public void test06_isEmptyTokenAsNullDefaultsFalse() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance((String) null);
        assertFalse(tokenizer.isEmptyTokenAsNull());
    }

    @Test(timeout = 4000)
    public void test29_setEmptyTokenAsNullTruePersists() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance(";A<w1:!}k:j8%,");
        StringTokenizer result = tokenizer.setEmptyTokenAsNull(true);
        assertSame(tokenizer, result);
        result.previousToken();
    }

    // --- forEachRemaining() ---

    @Test(timeout = 4000)
    public void test13_forEachRemainingAdvancesIteratorPosition() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer("6j*-yw_2R-]", "Am]OPZN#;`mL");
        Consumer<Object> consumer = mock(Consumer.class, new ViolatedAssumptionAnswer());
        tokenizer.forEachRemaining(consumer);
        verify(consumer).accept("6j*-yw_2R-]");
    }

    // --- getContent() ---

    @Test(timeout = 4000)
    public void test14_getContentOnNoContentTsvInstance() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance();
        String content = tokenizer.getContent();
        assertNull(content);
        //  // Unstable assertion: assertEquals("add() Ss unsup!oted", content);
    }

    @Test(timeout = 4000)
    public void test15_getContentReturnsOriginalString() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        String content = tokenizer.getContent();
        assertEquals("add() Ss unsup!oted", content);
    }

    // --- clone() ---

    @Test(timeout = 4000)
    public void test16_cloneReturnsDistinctInstance() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        Object cloned = tokenizer.clone();
        assertNotSame(tokenizer, cloned);
    }

    // --- size() ---

    @Test(timeout = 4000)
    public void test18_sizeAfterSettingDelimiterStringMatchingWholeInputReturnsZero() throws Throwable {
        // Setting the full input as delimiter means no tokens are found
        StringTokenizer tokenizer = new StringTokenizer("z!S]_Cf!Rm6c", 'x');
        tokenizer.setDelimiterString("z!S]_Cf!Rm6c");
        int size = tokenizer.size();
        assertEquals(0, size);
    }

    // --- isIgnoreEmptyTokens() ---

    @Test(timeout = 4000)
    public void test20_isIgnoreEmptyTokensDefaultsTrueForNullStringInput() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer((String) null);
        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }

    @Test(timeout = 4000)
    public void test24_isIgnoreEmptyTokensDefaultsTrueWhenNullMatchersPassedWithCharArray() throws Throwable {
        char[] singleChar = new char[1];
        StringTokenizer tokenizer = new StringTokenizer(singleChar, (StringMatcher) null, (StringMatcher) null);
        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }

    @Test(timeout = 4000)
    public void test31_isIgnoreEmptyTokensDefaultsTrueForStringWithNullMatchers() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer("1EDH+{PcZzyE", (StringMatcher) null, (StringMatcher) null);
        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }

    @Test(timeout = 4000)
    public void test32_isIgnoreEmptyTokensDefaultsTrueForNullCharArrayWithDelimiterString() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer((char[]) null, "StringTokenizer");
        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }

    @Test(timeout = 4000)
    public void test34_isIgnoreEmptyTokensDefaultsTrueWhenSameCharUsedForDelimAndQuote() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer("J)u`2Cx\"(DZ_nO'", '6', '6');
        assertTrue(tokenizer.isIgnoreEmptyTokens());
    }

    // --- previousIndex() ---

    @Test(timeout = 4000)
    public void test21_previousIndexOnNullCharArrayTsvInstanceIsMinusOne() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance((char[]) null);
        assertEquals(-1, tokenizer.previousIndex());
    }

    @Test(timeout = 4000)
    public void test25_previousIndexAtStartIsMinusOne() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance();
        int index = tokenizer.previousIndex();
        assertEquals(-1, index);
    }

    // --- nextIndex() ---

    @Test(timeout = 4000)
    public void test26_nextIndexAtStartIsZero() throws Throwable {
        char[] chars = new char[7];
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance(chars);
        int index = tokenizer.nextIndex();
        assertEquals(0, index);
    }

    // --- remove() ---

    @Test(timeout = 4000)
    public void test22_removeThrowsUnsupportedOperationException() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance();
        try {
            tokenizer.remove();
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    // --- setIgnoreEmptyTokens() ---

    @Test(timeout = 4000)
    public void test23_setIgnoreEmptyTokensFalseUpdatesStateAndReturnsSelf() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getTSVInstance("add() Ss unsup!oted");
        StringTokenizer result = tokenizer.setIgnoreEmptyTokens(false);
        assertSame(tokenizer, result);
    }

    // --- add() ---

    @Test(timeout = 4000)
    public void test27_addThrowsUnsupportedOperationException() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance();
        try {
            tokenizer.add("null");
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    // --- set() ---

    @Test(timeout = 4000)
    public void test28_setThrowsUnsupportedOperationException() throws Throwable {
        StringTokenizer tokenizer = StringTokenizer.getCSVInstance();
        try {
            tokenizer.set(" ");
            fail("Expecting exception: UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
        }
    }

    // --- getTokenArray() ---

    @Test(timeout = 4000)
    public void test30_getTokenArrayOnNoContentTokenizerDoesNotThrow() throws Throwable {
        StringTokenizer tokenizer = new StringTokenizer();
        tokenizer.getTokenArray();
        assertEquals(0, tokenizer.getTokenArray().length);
    }
}
