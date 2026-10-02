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
public class UnicodeEscaper_ESTest_test2 extends UnicodeEscaper_ESTest_scaffolding {

    /**
     * A "between" escaper only escapes code points that fall inside its
     * inclusive range. Here the range is the single point -2014, so a code
     * point below that range (-2931) must NOT be escaped: translate() should
     * report that it handled nothing (returns false) and write nothing.
     */
    @Test(timeout = 4000)
    public void translateReturnsFalseForCodePointBelowBetweenRange() throws Throwable {
        int rangeLow = -2014;
        int rangeHigh = -2014;
        UnicodeEscaper betweenEscaper = UnicodeEscaper.between(rangeLow, rangeHigh);

        int codePointBelowRange = -2931;
        StringWriter writer = new StringWriter();
        boolean wasEscaped = betweenEscaper.translate(codePointBelowRange, (Writer) writer);

        assertFalse(wasEscaped);
    }
}
