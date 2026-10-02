package org.apache.commons.compress.harmony.unpack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class SegmentUtils_ESTest_test12 extends SegmentUtils_ESTest_scaffolding {

    /**
     * A method descriptor must contain both a '(' and a matching ')' to delimit
     * its argument list. The string "(+," has an opening parenthesis but no
     * closing one, so it is malformed and countInvokeInterfaceArgs should reject
     * it with an IllegalArgumentException ("No arguments").
     */
    @Test(timeout = 4000)
    public void countInvokeInterfaceArgsRejectsDescriptorMissingClosingParen() throws Throwable {
        String malformedDescriptor = "(+,";

        try {
            SegmentUtils.countInvokeInterfaceArgs(malformedDescriptor);
            fail("Expected IllegalArgumentException for a descriptor with no closing parenthesis");
        } catch (IllegalArgumentException e) {
            // Thrown by SegmentUtils.countArgs with the message "No arguments".
            verifyException("org.apache.commons.compress.harmony.unpack200.SegmentUtils", e);
        }
    }
}
