package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test08 extends BinaryCodec_ESTest_scaffolding {

    /**
     * Verifies that {@link BinaryCodec#toByteArray(String)} packs a string of
     * '0'/'1' characters into raw bytes. Each group of 8 characters becomes one
     * byte, with the least-significant byte taken from the right-hand end of the
     * string.
     */
    @Test(timeout = 4000)
    public void toByteArrayPacksBinaryStringIntoBytes() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();
        String binaryString = "00000001110010111111111110111000101100100000000011111111";

        byte[] packedBytes = binaryCodec.toByteArray(binaryString);

        byte[] expectedBytes = new byte[] {
            (byte) -1, (byte) 0, (byte) -78, (byte) -72, (byte) -1, (byte) -53, (byte) 1
        };
        assertArrayEquals(expectedBytes, packedBytes);
    }
}
