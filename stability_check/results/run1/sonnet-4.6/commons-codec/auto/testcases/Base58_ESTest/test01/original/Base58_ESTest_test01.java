package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test01 extends Base58_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Base58 base58_0 = new Base58();
        BaseNCodec.Context baseNCodec_Context0 = new BaseNCodec.Context();
        byte[] byteArray0 = base58_0.decode("X");
        base58_0.ensureBufferSize(76, baseNCodec_Context0);
        // Undeclared exception!
        try {
            base58_0.encode(byteArray0, 64, 64, baseNCodec_Context0);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
