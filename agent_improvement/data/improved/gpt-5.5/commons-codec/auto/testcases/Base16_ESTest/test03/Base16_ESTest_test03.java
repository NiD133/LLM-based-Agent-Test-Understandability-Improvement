package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test03 extends Base16_ESTest_scaffolding {

    private static final int INPUT_SIZE = 8;
    private static final int OVERFLOWING_OFFSET = -38;
    private static final int OVERFLOWING_LENGTH = 2147483639;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Base16.Builder builder = new Base16.Builder();
        byte[] input = new byte[INPUT_SIZE];
        Base16 base16 = builder.get();

        try {
            base16.encode(input, OVERFLOWING_OFFSET, OVERFLOWING_LENGTH);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
