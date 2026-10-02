package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test11 extends CodecEncoding_ESTest_scaffolding {

    /**
     * For canonical encoding values in the range 1..115, getCodec resolves directly
     * to the corresponding canonical codec without consuming any bytes from the input
     * stream. Here the default codec argument is ignored for such values.
     */
    @Test(timeout = 4000)
    public void getCodecResolvesCanonicalCodecForSmallValue() throws Throwable {
        // The default codec passed to getCodec; canonical codec at index 13 is BHSDCodec(4, 256).
        BHSDCodec defaultCodec = CodecEncoding.getCanonicalCodec(13);
        assertEquals(4294967293L, defaultCodec.largest());

        // Encoding value 2 (<= 115) maps to the canonical codec at index 2: BHSDCodec(1, 256, 1).
        // The stream is not read for such values, so its contents are irrelevant.
        ByteArrayInputStream unusedStream = new ByteArrayInputStream(new byte[2]);
        BHSDCodec resolvedCodec = (BHSDCodec) CodecEncoding.getCodec(2, unusedStream, defaultCodec);

        assertEquals((-128L), resolvedCodec.smallest());
    }
}
