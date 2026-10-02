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

    /**
     * An escaper configured with outsideOf(1151, 91) escapes any code point that
     * falls outside the [1151, 91] bounds. The code point 629 (U+0275) lies outside
     * that range, so translate() writes its "\\uXXXX" escape and reports success.
     */
    @Test(timeout = 4000)
    public void escapesCodePointOutsideConfiguredRange() throws Throwable {
        StringWriter writer = new StringWriter();
        UnicodeEscaper escaper = UnicodeEscaper.outsideOf(1151, 91);

        int codePoint = 629; // U+0275
        boolean wasEscaped = escaper.translate(codePoint, (Writer) writer);

        assertTrue("Code point outside the range should be escaped", wasEscaped);
        assertEquals("\\u0275", writer.toString());
    }
}
