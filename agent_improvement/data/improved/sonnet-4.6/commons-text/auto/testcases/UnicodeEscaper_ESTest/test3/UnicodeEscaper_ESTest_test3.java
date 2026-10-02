package org.apache.commons.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.io.Writer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnicodeEscaper_ESTest_test3 extends UnicodeEscaper_ESTest_scaffolding {

    // Code point 629 decimal = 0x0275, which escapes to "ɵ"
    private static final int CODE_POINT_629 = 629;
    private static final String EXPECTED_UNICODE_ESCAPE = "\\u0275";

    @Test(timeout = 4000)
    public void test_translateCodePoint_outsideOf_invertedRange_alwaysEscapes() throws Throwable {
        StringWriter output = new StringWriter(571);

        // outsideOf(1151, 91) creates an escaper with an inverted range (low > high).
        // Because between=false, it escapes code points where NOT (codePoint >= 1151 && codePoint <= 91).
        // Since 1151 > 91, that inner condition is never true, so every code point is escaped.
        UnicodeEscaper escaper = UnicodeEscaper.outsideOf(1151, 91);

        boolean wasTranslated = escaper.translate(CODE_POINT_629, (Writer) output);

        // Code point 629 (0x0275) falls outside the inverted range, so it must be escaped.
        assertEquals(EXPECTED_UNICODE_ESCAPE, output.toString());
        assertTrue(wasTranslated);
    }
}
