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
        // Value 116 signals a custom BHSD codec encoded in the stream (2 bytes needed).
        // Providing only 1 byte in the stream causes an EOFException on the second read.
        BHSDCodec defaultCodec = Codec.DELTA5;
        byte[] singleByte = new byte[1];
        ByteArrayInputStream incompleteStream = new ByteArrayInputStream(singleByte);
        try {
            CodecEncoding.getCodec(116, incompleteStream, defaultCodec);
            fail("Expecting exception: EOFException");
        } catch (EOFException e) {
            //
            // End of buffer read whilst trying to decode codec
            //
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
