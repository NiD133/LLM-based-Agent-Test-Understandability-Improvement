package org.apache.commons.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.io.Writer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true,
        resetStaticState = true, separateClassLoader = false)
public class UnicodeEscaper_ESTest extends UnicodeEscaper_ESTest_scaffolding {

    /**
     * A default UnicodeEscaper escapes all code points.
     * Integer.MAX_VALUE (0x7FFFFFFF) exceeds 0xFFFF, so toUtf16Escape is used,
     * producing the 10-character sequence 翿FFFF.
     */
    @Test(timeout = 4000)
    public void test_translateSupplementaryCodePoint_writesExtendedUnicodeEscape() throws Throwable {
        UnicodeEscaper escaper = new UnicodeEscaper();
        StringWriter writer = new StringWriter(571);

        boolean translated = escaper.translate(Integer.MAX_VALUE, (Writer) writer);

        assertEquals("\\u7FFFFFFF", writer.toString());
        assertTrue(translated);
    }

    /**
     * UnicodeEscaper.above(571) escapes code points strictly above 571.
     * Code point 629 (0x0275) is above 571, so it is escaped as ɵ.
     */
    @Test(timeout = 4000)
    public void test_above_escapesCodePointAboveThreshold() throws Throwable {
        StringWriter writer = new StringWriter(571);
        UnicodeEscaper escaper = UnicodeEscaper.above(571);

        boolean translated = escaper.translate(629, (Writer) writer);

        assertEquals("\\u0275", writer.toString());
        assertTrue(translated);
    }

    /**
     * UnicodeEscaper.between(-2014, -2014) only escapes code point -2014.
     * Code point -2931 is outside that single-value range, so translate returns false
     * and nothing is written.
     */
    @Test(timeout = 4000)
    public void test_between_doesNotEscapeCodePointOutsideRange() throws Throwable {
        UnicodeEscaper escaper = UnicodeEscaper.between(-2014, -2014);
        StringWriter writer = new StringWriter();

        boolean translated = escaper.translate(-2931, (Writer) writer);

        assertFalse(translated);
    }

    /**
     * UnicodeEscaper.outsideOf(1151, 91) escapes code points that are NOT in [91, 1151].
     * Code point 629 satisfies codePoint < 1151 (the stored lower boundary), so it is
     * considered outside and escaped as ɵ.
     */
    @Test(timeout = 4000)
    public void test_outsideOf_escapesCodePointOutsideRange() throws Throwable {
        StringWriter writer = new StringWriter(571);
        UnicodeEscaper escaper = UnicodeEscaper.outsideOf(1151, 91);

        boolean translated = escaper.translate(629, (Writer) writer);

        assertEquals("\\u0275", writer.toString());
        assertTrue(translated);
    }

    /**
     * UnicodeEscaper.between(55296, 1166) has below=55296 and above=1166 (inverted bounds).
     * With between=true the escape condition is codePoint in [55296, 1166], which is empty.
     * Code point 55296 > above (1166), so the range check fails and translate returns false.
     */
    @Test(timeout = 4000)
    public void test_between_invertedBounds_neverEscapes() throws Throwable {
        UnicodeEscaper escaper = UnicodeEscaper.between(55296, 1166);
        StringWriter writer = new StringWriter();

        boolean translated = escaper.translate(55296, (Writer) writer);

        assertFalse(translated);
    }

    /**
     * UnicodeEscaper.below(0) escapes code points strictly below 0 (i.e. none in practice).
     * Translating the CharSequence "5F8" produces no escaping, so the output equals the input.
     */
    @Test(timeout = 4000)
    public void test_below_zeroThreshold_leavesAsciiCharSequenceUnchanged() throws Throwable {
        UnicodeEscaper escaper = UnicodeEscaper.below(0);
        StringWriter writer = new StringWriter(0);

        escaper.translate((CharSequence) "5F8", (Writer) writer);

        assertEquals("5F8", writer.toString());
    }
}
