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
public class CodecEncoding_ESTest_test16 extends CodecEncoding_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        final int bhsdEncodingSpecifier = 116;
        final BHSDCodec defaultCodec = Codec.DELTA5;
        final byte[] incompleteBhsdHeader = new byte[1];
        final ByteArrayInputStream bandHeaderInput = new ByteArrayInputStream(incompleteBhsdHeader);

        try {
            CodecEncoding.getCodec(bhsdEncodingSpecifier, bandHeaderInput, defaultCodec);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            //
            // End of buffer read whilst trying to decode codec
            //
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
