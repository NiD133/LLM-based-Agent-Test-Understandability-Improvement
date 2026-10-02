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
     * When the escaper is configured to escape code points within an inclusive
     * range, a code point that lies above the range's upper boundary must not be
     * escaped. Here the range is [55296, 1166]; since the upper bound (1166) is
     * lower than the code point being translated (55296), the code point falls
     * outside the range and {@link UnicodeEscaper#translate(int, Writer)} should
     * report that nothing was written by returning {@code false}.
     */
    @Test(timeout = 4000)
    public void translateReturnsFalseForCodePointAboveRange() throws Throwable {
        final int rangeLowerBound = 55296;
        final int rangeUpperBound = 1166;
        UnicodeEscaper escaperForRange = UnicodeEscaper.between(rangeLowerBound, rangeUpperBound);

        StringWriter output = new StringWriter();
        int codePointAboveUpperBound = 55296;
        boolean wasEscaped = escaperForRange.translate(codePointAboveUpperBound, (Writer) output);

        assertFalse(wasEscaped);
    }
}
