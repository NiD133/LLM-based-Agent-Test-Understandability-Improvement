package org.apache.commons.text;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;

import java.util.HashMap;
import java.util.Map;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class AlphabetConverter_ESTest_test21 extends AlphabetConverter_ESTest_scaffolding {

    /**
     * createConverterFromMap() turns each map key into a string via the source
     * language's code point. The integer 351608024 (0x14F51CD8) lies above the
     * highest valid Unicode code point (0x10FFFF), so converting it must fail
     * with an IllegalArgumentException thrown from java.lang.Character.
     */
    @Test(timeout = 4000)
    public void createConverterFromMapRejectsInvalidCodePointKey() throws Throwable {
        final int invalidCodePoint = 351608024; // 0x14F51CD8, beyond Unicode range
        Map<Integer, String> originalToEncoded = new HashMap<Integer, String>();
        originalToEncoded.put(invalidCodePoint, "some-encoded-value");

        try {
            AlphabetConverter.createConverterFromMap(originalToEncoded);
            fail("Expected IllegalArgumentException for invalid Unicode code point");
        } catch (IllegalArgumentException e) {
            // Not a valid Unicode code point: 0x14F51CD8
            verifyException("java.lang.Character", e);
        }
    }
}
