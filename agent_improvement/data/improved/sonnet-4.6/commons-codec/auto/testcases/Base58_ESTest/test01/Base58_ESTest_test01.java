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
     * Verifies that encode() throws ArrayIndexOutOfBoundsException when the
     * given offset (64) is beyond the end of the source byte array.
     *
     * "X" decodes to a single byte, so the resulting array has length 1.
     * Passing offset=64, length=64 into encode() attempts to read past the
     * end of that 1-byte array, which must trigger the exception.
     */
    @Test(timeout = 4000)
    public void test01() throws Throwable {
        Base58 codec = new Base58();
        BaseNCodec.Context encodingContext = new BaseNCodec.Context();

        // Decode a single Base58 character; result is a 1-byte array.
        byte[] decodedBytes = codec.decode("X");

        // Pre-allocate space in the context buffer (does not affect the source array).
        codec.ensureBufferSize(76, encodingContext);

        // Attempt to read 64 bytes starting at offset 64 from a 1-byte array.
        try {
            codec.encode(decodedBytes, 64, 64, encodingContext);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected: offset 64 is out of bounds for a 1-byte array
        }
    }
}
