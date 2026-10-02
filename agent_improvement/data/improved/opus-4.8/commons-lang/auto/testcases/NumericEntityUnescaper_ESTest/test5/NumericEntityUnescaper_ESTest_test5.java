package org.apache.commons.lang3.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NumericEntityUnescaper_ESTest_test5 extends NumericEntityUnescaper_ESTest_scaffolding {

    /**
     * Input that begins with '&' but is not a numeric entity (it has no "&#"
     * prefix) should be returned unchanged by translate().
     */
    @Test(timeout = 4000)
    public void translateLeavesNonEntityInputUnchanged() throws Throwable {
        // Input is '&' followed by five NUL characters: a char[] is zero-filled
        // by default, so only index 0 is explicitly set to '&'.
        char[] inputChars = new char[6];
        inputChars[0] = '&';
        CharBuffer input = CharBuffer.wrap(inputChars);

        // No options -> default behaviour (semicolon required).
        NumericEntityUnescaper.OPTION[] noOptions = new NumericEntityUnescaper.OPTION[0];
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noOptions);

        String result = unescaper.translate((CharSequence) input);

        // Nothing matches a numeric entity, so the input is copied verbatim.
        assertEquals("&\u0000\u0000\u0000\u0000\u0000", result);
    }
}
