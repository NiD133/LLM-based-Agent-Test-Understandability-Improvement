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
     * A "between" escaper only escapes code points within the inclusive
     * [low, high] range. Here the range is given backwards (low = 55296,
     * high = 1166), so it covers nothing: every code point lies above the
     * high boundary. translate() should therefore report that it escaped
     * nothing (returns false) and leave the writer untouched.
     */
    @Test(timeout = 4000)
    public void translateReturnsFalseWhenCodePointIsAboveTheBetweenRange() throws Throwable {
        final int lowBoundary = 55296;
        final int highBoundary = 1166;
        UnicodeEscaper escaper = UnicodeEscaper.between(lowBoundary, highBoundary);

        StringWriter writer = new StringWriter();
        int codePointAboveRange = 55296;
        boolean wasEscaped = escaper.translate(codePointAboveRange, (Writer) writer);

        assertFalse(wasEscaped);
    }
}
