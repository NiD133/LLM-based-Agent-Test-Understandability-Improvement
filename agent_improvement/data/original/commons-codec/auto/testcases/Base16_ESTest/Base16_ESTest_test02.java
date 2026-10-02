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
public class Base16_ESTest_test02 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Base16.Builder base16_Builder0 = Base16.builder();
        CodecPolicy codecPolicy0 = CodecPolicy.STRICT;
        base16_Builder0.setDecodingPolicy(codecPolicy0);
        BaseNCodec.Context baseNCodec_Context0 = new BaseNCodec.Context();
        byte[] byteArray0 = new byte[5];
        baseNCodec_Context0.ibitWorkArea = (-3481);
        Base16 base16_0 = base16_Builder0.get();
        // Undeclared exception!
        try {
            base16_0.decode(byteArray0, 31, (-1214), baseNCodec_Context0);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Strict decoding: Last encoded character is a valid Base 16 alphabet character but not a possible encoding. Decoding requires at least two characters to create one byte.
            //
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
