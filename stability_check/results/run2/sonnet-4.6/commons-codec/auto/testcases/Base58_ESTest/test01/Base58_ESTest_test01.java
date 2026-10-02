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

    /**
     * Verifies that encode() throws ArrayIndexOutOfBoundsException when the given offset (64)
     * is beyond the end of the input byte array (which holds only 1 decoded byte for "X").
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Base58 codec = new Base58();
        BaseNCodec.Context context = new BaseNCodec.Context();

        // Decode Base58 character "X" → produces a 1-byte array
        byte[] singleByteInput = codec.decode("X");

        // Pre-allocate a buffer large enough to hold expected output
        codec.ensureBufferSize(76, context);

        // Encoding with offset=64 on a 1-byte array must throw ArrayIndexOutOfBoundsException
        try {
            codec.encode(singleByteInput, 64, 64, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
}
