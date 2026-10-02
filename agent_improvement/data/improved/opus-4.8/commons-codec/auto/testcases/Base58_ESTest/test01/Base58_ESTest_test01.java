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
     * Calling {@link Base58#encode(byte[], int, int, BaseNCodec.Context)} with an
     * offset and length that reach past the end of the input array must fail with
     * an {@link ArrayIndexOutOfBoundsException}, because encode copies
     * {@code length} bytes starting at {@code offset} out of the source array.
     */
    @Test(timeout = 4000)
    public void encodeWithOutOfBoundsRangeThrowsArrayIndexOutOfBounds() throws Throwable {
        Base58 base58 = new Base58();
        BaseNCodec.Context context = new BaseNCodec.Context();

        // Decode a single-character Base58 string to obtain a short byte array.
        byte[] decoded = base58.decode("X");

        // Prepare the context's internal buffer.
        base58.ensureBufferSize(76, context);

        // Reading 64 bytes starting at offset 64 runs off the end of the small
        // decoded array, so encode must throw ArrayIndexOutOfBoundsException.
        int outOfBoundsOffset = 64;
        int length = 64;
        try {
            base58.encode(decoded, outOfBoundsOffset, length, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // expected
        }
    }
}
