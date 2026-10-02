package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test11 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that getCanonicalCodec(13) returns a BHSDCodec(4,256) with the expected
     * largest value, and that getCodec(2, ...) returns the canonical codec at index 2
     * (BHSDCodec(1,256,1)) with the expected smallest (most negative) value.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Canonical codec at index 13 is BHSDCodec(4, 256) — a 4-byte, base-256 unsigned codec
        BHSDCodec canonicalCodec13 = CodecEncoding.getCanonicalCodec(13);

        // A two-zero-byte stream is provided as band_headers input; it won't be consumed
        // because value=2 selects a canonical codec directly (no extra header bytes needed)
        byte[] emptyHeaderBytes = new byte[2];
        ByteArrayInputStream bandHeaderStream = new ByteArrayInputStream(emptyHeaderBytes);

        // value=2 is <= 115, so getCodec returns canonicalCodec[2] = BHSDCodec(1, 256, 1)
        BHSDCodec resolvedCodec = (BHSDCodec) CodecEncoding.getCodec(2, bandHeaderStream, canonicalCodec13);

        // BHSDCodec(4, 256) has largest = 256^4 - 3 = 4294967293
        assertEquals(4294967293L, canonicalCodec13.largest());

        // BHSDCodec(1, 256, 1) is signed (s=1), so smallest = -(256^1 / 2) = -128
        assertEquals((-128L), resolvedCodec.smallest());
    }
}
