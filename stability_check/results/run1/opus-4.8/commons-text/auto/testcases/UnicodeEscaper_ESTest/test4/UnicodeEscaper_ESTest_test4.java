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
public class UnicodeEscaper_ESTest_test4 extends UnicodeEscaper_ESTest_scaffolding {

    /**
     * When the escaper is configured to escape code points inside an inverted
     * range (low bound 55296 is greater than high bound 1166), no code point can
     * ever fall within it. Translating code point 55296 must therefore escape
     * nothing and report {@code false}.
     */
    @Test(timeout = 4000)
    public void translateReturnsFalseWhenCodePointOutsideInvertedRange() throws Throwable {
        int lowBound = 55296;
        int highBound = 1166;
        UnicodeEscaper escaper = UnicodeEscaper.between(lowBound, highBound);

        StringWriter writer = new StringWriter();
        int codePointToTranslate = 55296;
        boolean wasEscaped = escaper.translate(codePointToTranslate, (Writer) writer);

        assertFalse(wasEscaped);
    }
}
