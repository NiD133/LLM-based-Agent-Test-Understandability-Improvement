package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test04 extends URLCodec_ESTest_scaffolding {

    /**
     * Encoding a null string should return null rather than throwing,
     * regardless of the configured charset.
     */
    @Test(timeout = 4000)
    public void encodeNullStringReturnsNull() throws Throwable {
        URLCodec urlCodec = new URLCodec("org.apache.commons.codec.DecoderException");

        String encoded = urlCodec.encode((String) null);

        assertNull(encoded);
    }
}
