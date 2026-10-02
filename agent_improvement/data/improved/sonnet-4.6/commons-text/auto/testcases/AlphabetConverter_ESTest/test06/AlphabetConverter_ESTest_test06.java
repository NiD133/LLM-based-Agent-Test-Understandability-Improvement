package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.UnsupportedEncodingException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test06 extends AlphabetConverter_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Build a converter whose entire alphabet is just the backspace character (code point 8).
        // Encoding any string that contains a character outside that single-element alphabet
        // must throw UnsupportedEncodingException — here the first unknown character is a space.
        Integer backspaceCodePoint = Integer.valueOf(8);
        Integer[] singleCharAlphabet = new Integer[] { backspaceCodePoint };

        AlphabetConverter converterWithBackspaceOnly = AlphabetConverter.createConverter(
                singleCharAlphabet, singleCharAlphabet, singleCharAlphabet);

        try {
            // "\b -> 8\r\n" starts with backspace ('\b'), then immediately hits a space,
            // which is not in the alphabet, triggering the exception.
            converterWithBackspaceOnly.encode("\b -> 8\r\n");
            fail("Expecting exception: UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            //
            // Couldn't find encoding for ' ' in \b -> 8\r
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
