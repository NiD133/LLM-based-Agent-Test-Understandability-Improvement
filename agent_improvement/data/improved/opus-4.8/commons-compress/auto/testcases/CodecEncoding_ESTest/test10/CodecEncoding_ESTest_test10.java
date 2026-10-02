package org.apache.commons.compress.harmony.pack200;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.IOException;
import java.io.PipedInputStream;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CodecEncoding_ESTest_test10 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Encoding values above the highest valid meta-encoding byte (188) are not
     * recognized, so {@link CodecEncoding#getCodec} must reject them by throwing
     * an IOException rather than returning a codec.
     */
    @Test(timeout = 4000)
    public void getCodec_withOutOfRangeEncodingValue_throwsIOException() throws Throwable {
        int invalidEncodingValue = 2756;
        PipedInputStream bandHeaders = new PipedInputStream();
        BHSDCodec defaultCodec = Codec.CHAR3;

        try {
            CodecEncoding.getCodec(invalidEncodingValue, bandHeaders, defaultCodec);
            fail("Expected an IOException for the invalid codec encoding byte (2756)");
        } catch (IOException e) {
            // Message reads: "Invalid codec encoding byte (2756) found"
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
