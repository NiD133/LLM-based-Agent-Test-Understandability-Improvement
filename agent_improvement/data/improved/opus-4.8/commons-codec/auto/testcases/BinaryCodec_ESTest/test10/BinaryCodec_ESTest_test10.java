package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BinaryCodec_ESTest_test10 extends BinaryCodec_ESTest_scaffolding {

    /**
     * decode() treats each input byte as an ASCII '0' or '1' and packs the eight
     * bits of one input group into a single output byte. Only the first byte here
     * is ASCII '1' (49); since the most significant bit corresponds to the first
     * input byte, the eight bytes "1000 0000" pack into 0x80, i.e. (byte) -128.
     */
    @Test(timeout = 4000)
    public void decodeEightAsciiBitsWithOnlyTopBitSetReturnsMinus128() throws Throwable {
        BinaryCodec binaryCodec = new BinaryCodec();

        byte[] asciiBits = new byte[8];
        asciiBits[0] = (byte) '1';

        byte[] decoded = binaryCodec.decode(asciiBits);

        assertArrayEquals(new byte[] { (byte) -128 }, decoded);
    }
}
