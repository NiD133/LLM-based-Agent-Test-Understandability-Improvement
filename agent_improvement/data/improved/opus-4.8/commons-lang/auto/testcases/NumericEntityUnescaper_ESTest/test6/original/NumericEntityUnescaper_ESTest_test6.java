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

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        NumericEntityUnescaper.OPTION[] numericEntityUnescaper_OPTIONArray0 = new NumericEntityUnescaper.OPTION[0];
        char[] charArray0 = new char[1];
        charArray0[0] = '&';
        CharBuffer charBuffer0 = CharBuffer.wrap(charArray0);
        NumericEntityUnescaper numericEntityUnescaper0 = new NumericEntityUnescaper(numericEntityUnescaper_OPTIONArray0);
        String string0 = numericEntityUnescaper0.translate((CharSequence) charBuffer0);
        assertEquals("&", string0);
    }
}
