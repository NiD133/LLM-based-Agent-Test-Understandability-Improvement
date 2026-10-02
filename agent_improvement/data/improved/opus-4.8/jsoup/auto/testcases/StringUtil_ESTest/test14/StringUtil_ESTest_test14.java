package org.jsoup.internal;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtil_ESTest_test14 extends StringUtil_ESTest_scaffolding {

    /**
     * isAscii() should report false when a string contains any character outside
     * the ASCII range (0-127), even if every other character is ASCII.
     *
     * Here the input is 448 NUL characters (code point 0, all ASCII) followed by a
     * single non-ASCII character U+01C0 (code point 448), which forces the result to false.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // a new char[] is zero-initialised, so this is 448 NUL chars (all ASCII)
        String asciiNuls = new String(new char[448]);
        String input = asciiNuls + "ǀ"; // append one non-ASCII character (U+01C0)

        boolean isAscii = StringUtil.isAscii(input);

        assertFalse(isAscii);
    }
}
