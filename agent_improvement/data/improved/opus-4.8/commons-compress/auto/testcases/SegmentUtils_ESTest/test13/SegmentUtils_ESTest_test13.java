package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test13 extends SegmentUtils_ESTest_scaffolding {

    /**
     * countArgs treats the text between the first '(' and the first ')' as a method
     * descriptor's argument list. For an arbitrary string that happens to contain a
     * '(' and a ')', every character in between is counted as a separate argument
     * (no 'L...;' object types are present to be collapsed), giving the raw length of
     * that substring.
     */
    @Test(timeout = 4000)
    public void countArgsCountsEveryCharacterBetweenParentheses() throws Throwable {
        // The argument list is everything between '(' and ')':
        // "n J %,d/ coun = %,E, max-e>gZh % %,d,Aemaining = %,d" which is 52 characters.
        String descriptor =
            "Can't ead b1yonI end of strea(n J %,d/ coun = %,E, max-e>gZh % %,d,Aemaining = %,d)";

        int argCount = SegmentUtils.countArgs(descriptor);

        assertEquals(52, argCount);
    }
}
