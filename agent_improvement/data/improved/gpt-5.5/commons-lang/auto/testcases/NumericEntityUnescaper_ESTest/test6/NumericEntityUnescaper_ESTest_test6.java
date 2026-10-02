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
        NumericEntityUnescaper.OPTION[] noExplicitOptions = new NumericEntityUnescaper.OPTION[0];
        char[] ampersandCharacters = new char[1];
        ampersandCharacters[0] = '&';
        CharBuffer ampersandInput = CharBuffer.wrap(ampersandCharacters);
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noExplicitOptions);

        String translatedText = unescaper.translate((CharSequence) ampersandInput);

        assertEquals("&", translatedText);
    }
}
