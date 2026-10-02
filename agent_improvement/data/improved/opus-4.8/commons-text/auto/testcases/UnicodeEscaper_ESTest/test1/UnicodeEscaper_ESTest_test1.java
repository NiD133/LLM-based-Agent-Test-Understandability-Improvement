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

    /**
     * An escaper built with {@link UnicodeEscaper#above(int)} escapes every code
     * point strictly above the given boundary. Here the boundary is 571, so the
     * code point 629 (0x0275) is outside the kept range and must be written to the
     * writer as its "\\uXXXX" escape sequence, with translate(...) reporting success.
     */
    @Test(timeout = 4000)
    public void translateCodePointAboveBoundaryWritesUnicodeEscape() throws Throwable {
        final int boundary = 571;
        final int codePointAboveBoundary = 629; // 0x0275

        UnicodeEscaper escaperAboveBoundary = UnicodeEscaper.above(boundary);
        StringWriter output = new StringWriter();

        boolean wasEscaped = escaperAboveBoundary.translate(codePointAboveBoundary, (Writer) output);

        assertTrue("Code point above the boundary should be escaped", wasEscaped);
        assertEquals("\\u0275", output.toString());
    }
}
