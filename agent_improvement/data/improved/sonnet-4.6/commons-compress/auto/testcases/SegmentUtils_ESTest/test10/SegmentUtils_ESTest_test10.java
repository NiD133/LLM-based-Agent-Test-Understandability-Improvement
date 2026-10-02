package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test10 extends SegmentUtils_ESTest_scaffolding {

    /**
     * An empty descriptor string has no parentheses, so countArgs cannot locate
     * argument boundaries and must throw IllegalArgumentException("No arguments").
     * The widthOfLongsAndDoubles value (1740) is irrelevant because the exception
     * is raised before any argument counting begins.
     */
    @Test(timeout = 4000)
    public void test_countArgs_emptyDescriptor_throwsIllegalArgumentException() throws Throwable {
        final String emptyDescriptor = "";
        final int widthOfLongsAndDoubles = 1740;

        try {
            SegmentUtils.countArgs(emptyDescriptor, widthOfLongsAndDoubles);
            fail("Expected IllegalArgumentException for a descriptor with no parentheses");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", e);
        }
    }
}
