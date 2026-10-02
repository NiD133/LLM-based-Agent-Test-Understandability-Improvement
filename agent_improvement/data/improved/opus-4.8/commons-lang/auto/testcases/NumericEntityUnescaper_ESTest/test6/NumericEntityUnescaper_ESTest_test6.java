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
public class NumericEntityUnescaper_ESTest_test6 extends NumericEntityUnescaper_ESTest_scaffolding {

    /**
     * A lone "&" is not a numeric entity (there is no "&#..." sequence), so the
     * unescaper must leave the input untouched.
     */
    @Test(timeout = 4000)
    public void translateLoneAmpersandReturnsItUnchanged() throws Throwable {
        NumericEntityUnescaper.OPTION[] noOptions = new NumericEntityUnescaper.OPTION[0];
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noOptions);

        CharBuffer loneAmpersand = CharBuffer.wrap(new char[] { '&' });
        String result = unescaper.translate((CharSequence) loneAmpersand);

        assertEquals("&", result);
    }
}
