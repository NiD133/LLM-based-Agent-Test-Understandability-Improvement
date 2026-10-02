package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test01 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that decode(Object) throws an exception when given an argument
     * that is neither a byte[], char[], nor String — the codec only accepts
     * those three types and rejects everything else.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        // Arrange
        BinaryCodec codec = new BinaryCodec();
        Object unsupportedArgument = new Object();

        // Act & Assert — a plain Object is not a supported decode input type
        try {
            codec.decode(unsupportedArgument);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // "argument not a byte array" is the message thrown by BinaryCodec
            verifyException("org.apache.commons.codec.binary.BinaryCodec", e);
        }
    }
}
