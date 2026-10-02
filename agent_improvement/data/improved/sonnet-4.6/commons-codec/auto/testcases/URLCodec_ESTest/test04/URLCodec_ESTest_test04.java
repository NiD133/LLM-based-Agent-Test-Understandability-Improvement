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
public class URLCodec_ESTest_test04 extends URLCodec_ESTest_scaffolding {

    /**
     * Encoding a null string input should return null regardless of the charset configured on the codec.
     * The charset value here is arbitrary; URLCodec.encode(String) short-circuits and returns null
     * before ever consulting the charset when the input is null.
     */
    @Test(timeout = 4000)
    public void test_encodeNullString_returnsNull() throws Throwable {
        // Use an arbitrary charset string — the codec returns null before using it
        String arbitraryCharset = "org.apache.commons.codec.DecoderException";
        URLCodec codec = new URLCodec(arbitraryCharset);

        String result = codec.encode((String) null);

        assertNull(result);
    }
}
