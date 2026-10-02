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
        Base16.Builder strictBase16Builder = Base16.builder();
        CodecPolicy strictDecodingPolicy = CodecPolicy.STRICT;
        strictBase16Builder.setDecodingPolicy(strictDecodingPolicy);

        BaseNCodec.Context contextWithTrailingCharacter = new BaseNCodec.Context();
        byte[] encodedBytes = new byte[5];
        contextWithTrailingCharacter.ibitWorkArea = (-3481);
        Base16 strictBase16 = strictBase16Builder.get();

        // Undeclared exception!
        try {
            strictBase16.decode(encodedBytes, 31, (-1214), contextWithTrailingCharacter);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Strict decoding: Last encoded character is a valid Base 16 alphabet character but not a possible encoding. Decoding requires at least two characters to create one byte.
            //
            verifyException("org.apache.commons.codec.binary.Base16", e);
        }
    }
}
