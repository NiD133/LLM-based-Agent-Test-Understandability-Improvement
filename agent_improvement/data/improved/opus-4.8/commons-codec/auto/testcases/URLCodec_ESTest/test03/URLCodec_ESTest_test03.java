package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test03 extends URLCodec_ESTest_scaffolding {

    /**
     * Encoding a null string returns null, regardless of the charset argument
     * (which is itself null here). See {@link URLCodec#encode(String, String)},
     * which short-circuits and returns null when the input string is null.
     */
    @Test(timeout = 4000)
    public void encodeNullStringWithNullCharsetReturnsNull() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        String encoded = urlCodec.encode((String) null, (String) null);

        assertNull(encoded);
    }
}
