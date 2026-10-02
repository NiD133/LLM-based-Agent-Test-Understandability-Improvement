package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.BitSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test13 extends URLCodec_ESTest_scaffolding {

    /**
     * When {@code encodeUrl} receives a {@code null} BitSet, it falls back to the
     * default set of URL-safe characters. None of the bytes below are URL-safe
     * (one negative byte plus seven zero bytes), so every byte is escaped as a
     * three-character sequence: the '%' escape char followed by two hex digits.
     * Hence 8 input bytes produce 8 * 3 = 24 encoded bytes.
     */
    @Test(timeout = 4000)
    public void encodeUrlWithNullBitSetEscapesEveryUnsafeByte() throws Throwable {
        byte[] bytesToEncode = new byte[8];
        bytesToEncode[0] = (byte) -38;

        byte[] encoded = URLCodec.encodeUrl((BitSet) null, bytesToEncode);

        int escapedSequenceLength = 3; // '%' + two hex digits
        assertEquals(bytesToEncode.length * escapedSequenceLength, encoded.length);
        assertEquals(24, encoded.length);
    }
}
