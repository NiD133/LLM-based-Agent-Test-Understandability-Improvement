package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.InputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test08 extends CodecEncoding_ESTest_scaffolding {

    /**
     * The encoding byte 179 selects a population codec whose sub-codecs must be read
     * from the supplied input stream. When that stream is {@code null}, decoding the
     * favoured sub-codec dereferences it and a NullPointerException is thrown.
     */
    @Test(timeout = 4000)
    public void getCodecWithNullStreamForStreamReadingEncodingThrowsNPE() throws Throwable {
        int streamReadingEncodingByte = 179;
        Codec defaultCodec = Codec.MDELTA5;

        try {
            CodecEncoding.getCodec(streamReadingEncodingByte, (InputStream) null, defaultCodec);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // The null input stream is dereferenced inside CodecEncoding while decoding the sub-codec.
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
