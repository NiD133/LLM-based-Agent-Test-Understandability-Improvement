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
     * Encoding a byte range that lies outside the source array must fail with an
     * {@link ArrayIndexOutOfBoundsException}.
     *
     * <p>Decoding the single Base58 character "X" yields a very small byte array,
     * so asking {@code encode} to read 64 bytes starting at offset 64 reaches past
     * the end of that array.</p>
     */
    @Test(timeout = 4000)
    public void encodeWithRangeBeyondArrayThrowsArrayIndexOutOfBounds() throws Throwable {
        Base58 base58 = new Base58();
        BaseNCodec.Context context = new BaseNCodec.Context();

        byte[] decoded = base58.decode("X");
        base58.ensureBufferSize(76, context);

        final int offsetPastArrayEnd = 64;
        final int lengthPastArrayEnd = 64;
        try {
            base58.encode(decoded, offsetPastArrayEnd, lengthPastArrayEnd, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected: the requested range extends beyond the decoded array.
        }
    }
}
