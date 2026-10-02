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

    /**
     * Decoding with an offset that lies outside the source array must fail.
     *
     * <p>The source array holds only 3 bytes, but the requested offset is 15.
     * Even though the requested length is 0, {@code Base58.decode} performs an
     * internal {@code System.arraycopy} that reads starting at the offset, so an
     * out-of-bounds offset triggers an {@link ArrayIndexOutOfBoundsException}.</p>
     */
    @Test(timeout = 4000)
    public void decodeWithOffsetBeyondArrayLengthThrowsArrayIndexOutOfBounds() throws Throwable {
        Base58 base58 = new Base58();

        byte[] inputData = new byte[3];
        BaseNCodec.Context context = new BaseNCodec.Context();
        context.buffer = inputData;

        int outOfBoundsOffset = 15;
        int length = 0;
        try {
            base58.decode(inputData, outOfBoundsOffset, length, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException expected) {
            // Reading from offset 15 of a 3-byte array is out of bounds.
        }
    }
}
