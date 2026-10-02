package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test07 extends SegmentUtils_ESTest_scaffolding {

    /**
     * Verifies that countArgs correctly counts 7 arguments from a descriptor
     * containing non-standard type characters mixed with an array prefix '['.
     *
     * The descriptor "(0ANr[&rF)8mUn?" has 8 characters between '(' and ')':
     *   '0','A','N','r' - each count as 1 argument (4 total)
     *   '[' followed by '&' - array type, counts as 1 argument (5 total)
     *   'r','F' - each count as 1 argument (7 total)
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Descriptor with 8 tokens between parentheses: 4 plain + 1 array + 2 plain = 7 args
        String descriptorWithMixedTypes = "(0ANr[&rF)8mUn?";

        int argCount = SegmentUtils.countArgs(descriptorWithMixedTypes);

        assertEquals(7, argCount);
    }
}
