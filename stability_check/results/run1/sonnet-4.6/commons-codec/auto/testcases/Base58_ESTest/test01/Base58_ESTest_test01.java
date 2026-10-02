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
     * Verifies that calling encode() with an offset (64) and length (64) that exceed the bounds
     * of the decoded byte array throws ArrayIndexOutOfBoundsException.
     *
     * "X" decodes to a 1-byte array. Passing offset=64, length=64 attempts to read
     * 64 bytes starting at index 64, which is far beyond the array boundary.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Base58 codec = new Base58();
        BaseNCodec.Context context = new BaseNCodec.Context();

        // Decode the single Base58 character "X" — produces a 1-byte array
        byte[] decodedBytes = codec.decode("X");

        // Pre-allocate enough buffer space in the context for subsequent encoding
        codec.ensureBufferSize(76, context);

        // Attempting to encode with offset=64 and length=64 on a 1-byte array must
        // throw ArrayIndexOutOfBoundsException because the array is far too small
        try {
            codec.encode(decodedBytes, 64, 64, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected: offset 64 is out of bounds for a 1-byte array
        }
    }
}
