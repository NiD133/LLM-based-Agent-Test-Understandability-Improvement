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

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        Base16.Builder base16_Builder0 = new Base16.Builder();
        byte[] byteArray0 = new byte[8];
        Base16 base16_0 = base16_Builder0.get();
        // Undeclared exception!
        try {
            base16_0.encode(byteArray0, (-38), 2147483639);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Input length exceeds maximum size for encoded data: 2147483639
            //
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
