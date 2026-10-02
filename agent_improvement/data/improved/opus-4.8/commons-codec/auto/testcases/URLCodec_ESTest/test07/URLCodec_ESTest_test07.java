package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test07 extends URLCodec_ESTest_scaffolding {

    /**
     * Decoding a null string returns null, regardless of the charset argument
     * (which is also null here). The codec short-circuits on null input before
     * the charset is ever used.
     */
    @Test(timeout = 4000)
    public void decodeNullStringWithNullCharsetReturnsNull() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        String decoded = urlCodec.decode((String) null, (String) null);

        assertNull(decoded);
    }
}
