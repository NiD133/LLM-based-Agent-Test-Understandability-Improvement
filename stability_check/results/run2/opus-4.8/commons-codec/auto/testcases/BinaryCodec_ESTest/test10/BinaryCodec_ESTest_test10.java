package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test10 extends BinaryCodec_ESTest_scaffolding {

    /**
     * decode() treats each of the 8 input bytes as one ASCII bit and packs them
     * into a single output byte. The first input byte maps to the most significant
     * bit, so an ASCII '1' (49) at index 0 followed by NUL bytes decodes to 0x80,
     * which as a signed byte is -128.
     */
    @Test(timeout = 4000)
    public void decodeEightAsciiBitsPacksIntoSingleByte() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();

        byte[] asciiBits = new byte[8];
        asciiBits[0] = (byte) '1';

        byte[] decoded = binaryCodec.decode(asciiBits);

        assertArrayEquals(new byte[] { (byte) -128 }, decoded);
    }
}
