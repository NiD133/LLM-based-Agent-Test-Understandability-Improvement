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
public class CodecEncoding_ESTest_test07 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Value 142 encodes a PopulationCodec (range 141-188). Resolving it requires
     * reading additional bytes from the InputStream. Passing null must throw NPE.
     */
    @Test(timeout = 4000)
    public void test_getCodec_populationCodecValue_nullInputStream_throwsNullPointerException() throws Throwable {
        // Codec.CHAR3 is used as the default codec; it is not consulted before the NPE
        BHSDCodec defaultCodec = Codec.CHAR3;
        int populationCodecSpecifier = 142; // falls in the PopulationCodec range 141-188

        try {
            CodecEncoding.getCodec(populationCodecSpecifier, (InputStream) null, defaultCodec);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
