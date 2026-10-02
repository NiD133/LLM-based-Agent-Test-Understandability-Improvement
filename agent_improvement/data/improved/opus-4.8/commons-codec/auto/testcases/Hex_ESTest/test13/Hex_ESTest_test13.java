package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test13 extends Hex_ESTest_scaffolding {

    /**
     * Encoding more bytes than the source array actually contains must fail.
     * Here the source holds only 4 bytes, but the requested length (678 bytes
     * starting at offset 0) runs far past the end of the array, so encodeHex
     * reads out of bounds and throws ArrayIndexOutOfBoundsException.
     */
    @Test(timeout = 4000)
    public void encodeHexWithLengthBeyondArrayThrowsArrayIndexOutOfBounds() throws Throwable {
        byte[] source = new byte[4];
        int startOffset = 0;
        int lengthToEncode = 678; // far larger than source.length (4)
        boolean toLowerCase = false;

        try {
            Hex.encodeHex(source, startOffset, lengthToEncode, toLowerCase);
            fail("Expecting exception: ArrayIndexOutOfBoundsException");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Thrown from within Hex when the encode loop reads past the array end.
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
