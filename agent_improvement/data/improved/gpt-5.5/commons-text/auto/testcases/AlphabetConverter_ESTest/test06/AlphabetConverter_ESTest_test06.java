package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test06 extends AlphabetConverter_ESTest_scaffolding {

    private static final Integer DO_NOT_ENCODE_BACKSPACE = new Integer(8);
    private static final String TEXT_WITH_UNMAPPED_SPACE = "\b -> 8\r\n";

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Integer[] singleCharacterAlphabet = new Integer[1];
        singleCharacterAlphabet[0] = DO_NOT_ENCODE_BACKSPACE;

        AlphabetConverter converter = AlphabetConverter.createConverter(
                singleCharacterAlphabet,
                singleCharacterAlphabet,
                singleCharacterAlphabet);

        try {
            converter.encode(TEXT_WITH_UNMAPPED_SPACE);
            fail("Expecting exception: UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            //
            // Couldn't find encoding for ' ' in \b -> 8\r
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
