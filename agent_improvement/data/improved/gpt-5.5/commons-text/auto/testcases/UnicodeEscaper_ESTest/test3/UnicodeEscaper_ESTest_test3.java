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

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        final int initialWriterCapacity = 571;
        final int outsideRangeLow = 1151;
        final int outsideRangeHigh = 91;
        final int codePointToEscape = 629;
        final String expectedEscapedCodePoint = "\\u0275";

        StringWriter output = new StringWriter(initialWriterCapacity);
        UnicodeEscaper escaper = UnicodeEscaper.outsideOf(outsideRangeLow, outsideRangeHigh);

        boolean translated = escaper.translate(codePointToEscape, (Writer) output);

        assertEquals(expectedEscapedCodePoint, output.toString());
        assertTrue(translated);
    }
}
