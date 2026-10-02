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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Base58 codec = new Base58();
        byte[] inputArray = new byte[3];
        BaseNCodec.Context context = new BaseNCodec.Context();
        context.buffer = inputArray;

        // offset 15 exceeds the bounds of the 3-byte input array, causing ArrayIndexOutOfBoundsException
        try {
            codec.decode(inputArray, 15, 0, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
