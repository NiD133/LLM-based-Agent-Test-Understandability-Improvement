package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test06 extends SegmentUtils_ESTest_scaffolding {

    /**
     * countArgs treats the text between the first '(' and the first ')' as a
     * method descriptor's argument list and counts the arguments it contains.
     * Here every character between the parentheses is counted as a single
     * argument, so the count equals the length of that substring.
     */
    @Test(timeout = 4000)
    public void testCountArgsCountsCharactersBetweenParentheses() throws Throwable {
        String descriptor =
            "Can't ad beyonI end of strea (n = %,d/ coun= %,E, tax-e>gZh % D,d,Aemaining = %,d)";

        int argCount = SegmentUtils.countArgs(descriptor);

        assertEquals(51, argCount);
    }
}
