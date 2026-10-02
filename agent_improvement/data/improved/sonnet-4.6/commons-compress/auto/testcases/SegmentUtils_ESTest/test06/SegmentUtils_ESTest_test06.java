package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test06 extends SegmentUtils_ESTest_scaffolding {

    /**
     * Verifies that countArgs counts each character between '(' and ')' as one argument,
     * except 'D' and 'J' which also count as one (widthOfLongsAndDoubles defaults to 1),
     * and 'L'..';' sequences (object types) which count as one unit together.
     * The string below is not a real Java descriptor; it is an arbitrary string whose
     * parenthesised content happens to contain 51 countable tokens.
     */
    @Test(timeout = 4000)
    public void testCountArgsCountsCharactersBetweenParenthesesInArbitraryString() throws Throwable {
        // The content between the parentheses has 51 characters/tokens that the method counts.
        // 'D' inside the substring is treated as a double-type argument but widthOfLongsAndDoubles=1,
        // so it contributes 1 just like any other non-special character.
        String descriptorLikeString =
            "Can't ad beyonI end of strea (n = %,d/ coun= %,E, tax-e>gZh % D,d,Aemaining = %,d)";

        int argCount = SegmentUtils.countArgs(descriptorLikeString);

        assertEquals(51, argCount);
    }
}
