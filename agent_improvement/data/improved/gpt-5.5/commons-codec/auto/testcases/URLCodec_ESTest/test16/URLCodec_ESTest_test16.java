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
public class URLCodec_ESTest_test16 extends URLCodec_ESTest_scaffolding {

    private static final String UNSUPPORTED_CHARSET_AND_INPUT = "~zP+pe;V}>f#Rj";

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        URLCodec codec = new URLCodec(UNSUPPORTED_CHARSET_AND_INPUT);

        try {
            codec.decode((Object) UNSUPPORTED_CHARSET_AND_INPUT);
            fail("Expecting exception: Exception");
        } catch (Exception exception) {
            //
            // ~zP+pe;V}>f#Rj
            //
            verifyException("org.apache.commons.codec.net.URLCodec", exception);
        }
    }
}
