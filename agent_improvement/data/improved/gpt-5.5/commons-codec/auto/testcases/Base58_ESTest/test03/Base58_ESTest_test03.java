package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test03 extends Base58_ESTest_scaffolding {

    private static final int INPUT_SIZE = 3;
    private static final int OFFSET_PAST_INPUT = 15;
    private static final int ZERO_LENGTH = 0;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Base58 codec = new Base58();
        byte[] input = new byte[INPUT_SIZE];
        BaseNCodec.Context context = new BaseNCodec.Context();
        context.buffer = input;

        try {
            codec.decode(input, OFFSET_PAST_INPUT, ZERO_LENGTH, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
