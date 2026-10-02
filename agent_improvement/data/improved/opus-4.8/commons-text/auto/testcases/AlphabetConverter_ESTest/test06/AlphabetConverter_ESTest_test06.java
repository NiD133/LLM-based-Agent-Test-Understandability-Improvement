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

    /**
     * Builds a converter whose alphabet contains a single code point (8, the
     * backspace character) and then asks it to encode a string that also
     * contains other characters (a space, '-', '>', '8', etc.).
     *
     * Since only code point 8 can be encoded, encoding fails on the first
     * unsupported character (the space) and {@link AlphabetConverter#encode}
     * throws an {@link UnsupportedEncodingException}.
     */
    @Test(timeout = 4000)
    public void encodeFailsWhenStringContainsUnsupportedCharacter() throws Throwable {
        // Original, encoding and "do not encode" alphabets all contain only code point 8.
        Integer[] singleCharacterAlphabet = { Integer.valueOf(8) };

        AlphabetConverter converter = AlphabetConverter.createConverter(
                singleCharacterAlphabet, singleCharacterAlphabet, singleCharacterAlphabet);

        try {
            // The space character in this string is not part of the alphabet.
            converter.encode("\b -> 8\r\n");
            fail("Expecting exception: UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            // Couldn't find encoding for ' ' in \b -> 8\r
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
