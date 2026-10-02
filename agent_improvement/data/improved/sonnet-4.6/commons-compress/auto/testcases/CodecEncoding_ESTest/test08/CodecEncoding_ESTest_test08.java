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
public class CodecEncoding_ESTest_test08 extends CodecEncoding_ESTest_scaffolding {

    /**
     * Verifies that getCodec throws NullPointerException when the value requires
     * reading additional bytes from the InputStream (value >= 116), but the
     * supplied InputStream is null.
     *
     * Value 179 falls in the PopulationCodec encoding range (141-188), so
     * getCodec must read sub-codec specifiers from the stream. Passing null
     * instead of a real stream causes an immediate NullPointerException inside
     * CodecEncoding.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        BHSDCodec defaultCodec = Codec.MDELTA5;
        int populationCodecValue = 179; // in range [141,188], requires stream reads
        InputStream nullInputStream = null;

        try {
            CodecEncoding.getCodec(populationCodecValue, nullInputStream, defaultCodec);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
