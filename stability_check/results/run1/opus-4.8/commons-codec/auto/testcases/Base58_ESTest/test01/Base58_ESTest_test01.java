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
     * Encoding with an offset and length that exceed the bounds of the given
     * array must throw an ArrayIndexOutOfBoundsException.
     */
    @Test(timeout = 4000)
    public void encodeWithOutOfBoundsOffsetThrowsAioobe() throws Throwable {
        Base58 base58 = new Base58();
        BaseNCodec.Context context = new BaseNCodec.Context();

        // A single Base58 character decodes to a very short byte array.
        byte[] decodedBytes = base58.decode("X");
        base58.ensureBufferSize(76, context);

        try {
            // Offset 64 and length 64 read far past the end of decodedBytes.
            base58.encode(decodedBytes, 64, 64, context);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Expected: the requested range lies outside the source array.
        }
    }
}
