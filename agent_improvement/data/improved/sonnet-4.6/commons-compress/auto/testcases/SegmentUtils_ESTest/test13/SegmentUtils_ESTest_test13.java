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
     * Verifies that countArgs counts each character between the parentheses of
     * a non-standard descriptor string. The input is not a valid Java method
     * descriptor; it happens to contain '(' and ')' so countArgs treats all
     * characters between them as individual argument tokens (none trigger the
     * L…; object-reference or [ array-prefix rules), yielding a count of 52.
     */
    @Test(timeout = 4000)
    public void testCountArgsWithNonDescriptorStringCountsAllCharsBetweenParens() throws Throwable {
        // A free-form string that contains '(' and ')' but no valid JVM type characters
        String nonDescriptorInput = "Can't ead b1yonI end of strea(n J %,d/ coun = %,E, max-e>gZh % %,d,Aemaining = %,d)";

        int argCount = SegmentUtils.countArgs(nonDescriptorInput);

        // All 52 characters between the parentheses are treated as separate argument tokens
        assertEquals(52, argCount);
    }
}
