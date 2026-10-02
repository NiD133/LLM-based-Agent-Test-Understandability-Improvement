package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test08 extends URLCodec_ESTest_scaffolding {

    /**
     * Decoding a null string should return null rather than throwing,
     * as specified by {@link URLCodec#decode(String)}.
     */
    @Test(timeout = 4000)
    public void decodeNullStringReturnsNull() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        String decoded = urlCodec.decode((String) null);

        assertNull(decoded);
    }
}
