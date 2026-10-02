package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test10 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that count() returns 0 when the input string is null.
     *
     * A single-element String array initialized without values holds null at index 0,
     * so both the string argument and the char-set array contain null. According to
     * the CharSetUtils contract, count(null, *) = 0.
     */
    @Test(timeout = 4000)
    public void test_countReturnsZero_whenInputStringIsNull() throws Throwable {
        String[] charSetWithNullEntry = new String[1]; // charSetWithNullEntry[0] == null
        String nullInputString = charSetWithNullEntry[0];

        int result = CharSetUtils.count(nullInputString, charSetWithNullEntry);

        assertEquals(0, result);
    }
}
