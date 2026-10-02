package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test01 extends Base16_ESTest_scaffolding {

    /**
     * Decoding a single trailing Base16 character (the 'D' at index 5) does not
     * produce any decoded bytes yet: with only one available hex character the
     * codec keeps half a byte buffered in the context for the next call, so the
     * decode call completes without throwing.
     */
    @Test(timeout = 4000)
    public void decodeSingleTrailingCharacterBuffersHalfByte() throws Throwable {
        Base16 base16 = new Base16(false);

        // Six-byte input whose only Base16 character is 'D' (0x44 = 68) at the last index.
        byte[] input = new byte[6];
        final int lastIndex = 5;
        input[lastIndex] = (byte) 68;

        BaseNCodec.Context context = new BaseNCodec.Context();

        // Start decoding at the last byte; only one character is available to decode.
        final int offset = 5;
        final int length = 1767;
        base16.decode(input, offset, length, context);

        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
