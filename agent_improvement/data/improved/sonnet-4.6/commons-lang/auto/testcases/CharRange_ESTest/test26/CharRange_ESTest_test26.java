package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test26 extends CharRange_ESTest_scaffolding {

    /**
     * A single-character range where start == end should render as just that character,
     * with no dash or caret in the toString output.
     */
    @Test(timeout = 4000)
    public void test_toString_singleCharRange_returnsJustThatCharacter() throws Throwable {
        CharRange singleCharRange = CharRange.isIn('O', 'O');
        String result = singleCharRange.toString();
        assertEquals("O", result);
    }
}
