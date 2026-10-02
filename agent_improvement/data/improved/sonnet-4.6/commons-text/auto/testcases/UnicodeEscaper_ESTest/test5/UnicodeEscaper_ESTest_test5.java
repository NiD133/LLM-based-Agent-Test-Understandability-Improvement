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
public class UnicodeEscaper_ESTest_test5 extends UnicodeEscaper_ESTest_scaffolding {

    /**
     * UnicodeEscaper.below(0) creates an escaper that would only escape code points
     * strictly below 0 — a vacuous range, since no valid code point is negative.
     * Therefore translating any ASCII/BMP string should leave it completely unchanged.
     */
    @Test(timeout = 4000)
    public void test_escaperBelowCodePointZero_leavesStringUnchanged() throws Throwable {
        // Escaper configured to escape code points below 0 (i.e., nothing gets escaped)
        UnicodeEscaper noOpEscaper = UnicodeEscaper.below(0);

        StringWriter output = new StringWriter(0);
        noOpEscaper.translate((CharSequence) "5F8", (Writer) output);

        // All characters in "5F8" have code points >= 0, so none are escaped
        assertEquals("5F8", output.toString());
    }
}
