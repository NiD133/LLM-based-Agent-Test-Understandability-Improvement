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
public class NumericEntityUnescaper_ESTest_test2 extends NumericEntityUnescaper_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        NumericEntityUnescaper.OPTION[] noOptions = new NumericEntityUnescaper.OPTION[0];
        char[] inputCharacters = new char[7];

        // The text ends with "&#3" and intentionally has no trailing semicolon.
        inputCharacters[4] = '&';
        inputCharacters[5] = '#';
        inputCharacters[6] = '3';

        CharBuffer inputBuffer = CharBuffer.wrap(inputCharacters);
        NumericEntityUnescaper unescaper = new NumericEntityUnescaper(noOptions);
        String translatedText = unescaper.translate((CharSequence) inputBuffer);

        assertEquals("\u0000\u0000\u0000\u0000&#3", translatedText);
    }
}
