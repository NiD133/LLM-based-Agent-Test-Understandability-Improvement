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
        Base58 base58_0 = new Base58();
        byte[] byteArray0 = new byte[3];
        BaseNCodec.Context baseNCodec_Context0 = new BaseNCodec.Context();
        baseNCodec_Context0.buffer = byteArray0;
        // Undeclared exception!
        try {
            base58_0.decode(byteArray0, (int) (byte) 15, (int) (byte) 0, baseNCodec_Context0);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
