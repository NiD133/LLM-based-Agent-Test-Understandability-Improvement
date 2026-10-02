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
public class AlphabetConverter_ESTest_test10 extends AlphabetConverter_ESTest_scaffolding {

    private static final String ENCODED_VALUE =
            "Must have at least two encoding characters (excluding those in the 'do not encode' list), but has ";
    private static final String TRUNCATED_ENCODED_INPUT = "\uFFFF -> -1\r\n";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        HashMap<Integer, String> originalToEncoded = new HashMap<Integer, String>();
        Integer originalCodePoint = new Integer((-1));
        originalToEncoded.put(originalCodePoint, ENCODED_VALUE);

        AlphabetConverter converter = AlphabetConverter.createConverterFromMap(originalToEncoded);

        try {
            converter.decode(TRUNCATED_ENCODED_INPUT);
            fail("Expecting exception: UnsupportedEncodingException");
        } catch (UnsupportedEncodingException e) {
            //
            // Unexpected end of string while decoding \uFFFF -> -1\r
            //
            verifyException("org.apache.commons.text.AlphabetConverter", e);
        }
    }
}
