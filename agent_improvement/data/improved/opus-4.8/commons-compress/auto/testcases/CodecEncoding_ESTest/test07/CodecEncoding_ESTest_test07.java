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
public class CodecEncoding_ESTest_test07 extends CodecEncoding_ESTest_scaffolding {

    /**
     * The encoding value 142 selects a population codec whose token codec must be
     * read from the input stream (see CodecEncoding.getCodec, value range 141-188).
     * Passing a null input stream therefore triggers a NullPointerException when
     * getCodec attempts to read the next header byte.
     */
    @Test(timeout = 4000)
    public void getCodecWithNullStreamForPopulationEncodingThrowsNPE() throws Throwable {
        int populationEncodingValue = 142;
        InputStream nullHeaderStream = null;
        BHSDCodec defaultCodec = Codec.CHAR3;

        try {
            CodecEncoding.getCodec(populationEncodingValue, nullHeaderStream, defaultCodec);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // getCodec dereferences the null input stream; no message is set on the exception.
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
