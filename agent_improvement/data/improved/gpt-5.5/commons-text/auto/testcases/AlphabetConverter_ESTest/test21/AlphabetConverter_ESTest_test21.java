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
public class AlphabetConverter_ESTest_test21 extends AlphabetConverter_ESTest_scaffolding {

    private static final int INVALID_UNICODE_CODE_POINT = 351608024;
    private static final String ENCODED_VALUE =
            "Can not use 'do not encode' list because original alphabet does not contain '";

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        HashMap<Integer, String> originalToEncoded = new HashMap<Integer, String>();
        Integer invalidOriginalCodePoint = new Integer(INVALID_UNICODE_CODE_POINT);
        originalToEncoded.put(invalidOriginalCodePoint, ENCODED_VALUE);

        // Undeclared exception!
        try {
            AlphabetConverter.createConverterFromMap(originalToEncoded);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Not a valid Unicode code point: 0x14F51CD8
            //
            verifyException("java.lang.Character", e);
        }
    }
}
