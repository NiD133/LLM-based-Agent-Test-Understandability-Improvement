package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.BitSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test18 extends URLCodec_ESTest_scaffolding {

    private static final String DEFAULT_URL_CODEC_ENCODING = "UTF-8";

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        String actualEncoding = urlCodec.getEncoding();

        assertEquals(DEFAULT_URL_CODEC_ENCODING, actualEncoding);
    }
}
