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

    @Test(timeout = 4000)
    public void test_decodeObject_throwsException_whenCharsetIsUnsupported() throws Throwable {
        // URLCodec initialized with a charset name that is not a valid/supported encoding
        String unsupportedCharset = "~zP+pe;V}>f#Rj";
        URLCodec codec = new URLCodec(unsupportedCharset);

        // Decoding a String object should fail because the codec's charset cannot be resolved
        try {
            codec.decode((Object) "~zP+pe;V}>f#Rj");
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // ~zP+pe;V}>f#Rj
            //
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
