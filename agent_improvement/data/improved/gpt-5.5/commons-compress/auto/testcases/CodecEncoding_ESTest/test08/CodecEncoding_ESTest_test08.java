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

    private static final int POPULATION_CODEC_ENCODING_WITH_UNFAVOURED_DEFAULT = 179;

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        final BHSDCodec defaultCodec = Codec.MDELTA5;

        try {
            CodecEncoding.getCodec(POPULATION_CODEC_ENCODING_WITH_UNFAVOURED_DEFAULT, (InputStream) null, defaultCodec);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            verifyException("org.apache.commons.compress.harmony.pack200.CodecEncoding", e);
        }
    }
}
