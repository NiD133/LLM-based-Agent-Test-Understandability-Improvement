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
public class UnicodeEscaper_ESTest_test1 extends UnicodeEscaper_ESTest_scaffolding {

    // Code point 629 (0x0275) is above the threshold 571, so it should be escaped.
    private static final int THRESHOLD = 571;
    private static final int CODE_POINT_ABOVE_THRESHOLD = 629; // U+0275 LATIN SMALL LETTER BARRED O
    private static final String EXPECTED_UNICODE_ESCAPE = "\\u0275";

    @Test(timeout = 4000)
    public void test_translateCodePointAboveThreshold_writesUnicodeEscape() throws Throwable {
        StringWriter output = new StringWriter(THRESHOLD);
        UnicodeEscaper escaper = UnicodeEscaper.above(THRESHOLD);

        boolean wasTranslated = escaper.translate(CODE_POINT_ABOVE_THRESHOLD, (Writer) output);

        assertEquals(EXPECTED_UNICODE_ESCAPE, output.toString());
        assertTrue(wasTranslated);
    }
}
