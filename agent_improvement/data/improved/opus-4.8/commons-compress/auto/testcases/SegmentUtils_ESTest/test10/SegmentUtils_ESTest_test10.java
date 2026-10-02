package org.apache.commons.compress.harmony.unpack200;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test10 extends SegmentUtils_ESTest_scaffolding {

    /**
     * {@code countArgs} locates the argument list of a method descriptor between
     * '(' and ')'. An empty descriptor contains neither parenthesis, so the
     * method must reject it with an {@link IllegalArgumentException} ("No arguments").
     */
    @Test(timeout = 4000)
    public void countArgsRejectsDescriptorWithoutParentheses() throws Throwable {
        final String descriptorWithoutParentheses = "";
        final int widthOfLongsAndDoubles = 1740;

        try {
            SegmentUtils.countArgs(descriptorWithoutParentheses, widthOfLongsAndDoubles);
            fail("Expected IllegalArgumentException for a descriptor with no '(' or ')'");
        } catch (IllegalArgumentException expected) {
            // Thrown by SegmentUtils.countArgs when the parentheses are missing.
            assertEquals("No arguments", expected.getMessage());
        }
    }
}
