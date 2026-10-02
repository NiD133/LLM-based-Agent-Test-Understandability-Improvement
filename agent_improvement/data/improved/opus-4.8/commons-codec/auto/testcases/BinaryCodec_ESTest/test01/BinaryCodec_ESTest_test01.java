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
     * BinaryCodec.decode(Object) only accepts byte[], char[], String, or null.
     * Passing any other type must raise a DecoderException complaining that the
     * argument is "not a byte array".
     */
    @Test(timeout = 4000)
    public void decodeWithUnsupportedArgumentTypeThrowsException() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();
        Object unsupportedArgument = new Object();

        try {
            binaryCodec.decode(unsupportedArgument);
            fail("Expected an exception because the argument is not a byte array");
        } catch (Exception expected) {
            // Thrown by BinaryCodec with the message "argument not a byte array".
            verifyException("org.apache.commons.codec.binary.BinaryCodec", expected);
        }
    }
}
