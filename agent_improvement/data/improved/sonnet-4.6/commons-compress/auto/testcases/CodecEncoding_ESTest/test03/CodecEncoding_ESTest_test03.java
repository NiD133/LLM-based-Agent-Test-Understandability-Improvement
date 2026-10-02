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
public class CodecEncoding_ESTest_test03 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that getSpecifier encodes a RunCodec(k=13, aCodec=codec13, bCodec=codec13)
     * with codec13 as the band default as the byte sequence [129, 12, 13].
     *
     * canonical codec index 13 is BHSDCodec(4, 256).
     * Because aCodec equals the default, the specifier marks aCodec as "use default" (abDef=1),
     * yielding first-byte=129, kx-byte=12 (k-1), and bCodec specifier=13.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        BHSDCodec canonicalCodec13 = CodecEncoding.getCanonicalCodec(13);
        RunCodec runCodec = new RunCodec(13, canonicalCodec13, canonicalCodec13);

        int[] specifier = CodecEncoding.getSpecifier(runCodec, canonicalCodec13);

        assertNotNull(specifier);
        assertArrayEquals(new int[] { 129, 12, 13 }, specifier);
    }
}
