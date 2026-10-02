package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test21 extends AlphabetConverter_ESTest_scaffolding {

    // 0x14F51CD8 (351608024) exceeds the maximum valid Unicode code point 0x10FFFF
    private static final int INVALID_UNICODE_CODE_POINT = 0x14F51CD8;

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        HashMap<Integer, String> originalToEncoded = new HashMap<Integer, String>();
        originalToEncoded.put(INVALID_UNICODE_CODE_POINT,
                "Can not use 'do not encode' list because original alphabet does not contain '");

        try {
            AlphabetConverter.createConverterFromMap(originalToEncoded);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Character.toChars() rejects code points beyond the Unicode maximum (0x10FFFF)
            verifyException("java.lang.Character", e);
        }
    }
}
