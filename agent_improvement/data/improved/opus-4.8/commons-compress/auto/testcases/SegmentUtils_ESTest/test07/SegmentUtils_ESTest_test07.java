package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test07 extends SegmentUtils_ESTest_scaffolding {

    /**
     * countArgs only inspects the characters between '(' and ')'. For the
     * argument section "0ANr[&rF", each plain character contributes 1 while a
     * '[' simply marks the following character as an array type (still 1),
     * giving a total of 7 arguments.
     */
    @Test(timeout = 4000)
    public void countArgsCountsCharactersBetweenParentheses() throws Throwable {
        int argCount = SegmentUtils.countArgs("(0ANr[&rF)8mUn?");

        assertEquals(7, argCount);
    }
}
