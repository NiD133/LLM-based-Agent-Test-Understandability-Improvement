package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test07 extends PercentCodec_ESTest_scaffolding {

    /**
     * Decoding a null Object should return null rather than throwing,
     * regardless of how the codec is configured.
     */
    @Test(timeout = 4000)
    public void decodeNullObjectReturnsNull() throws Throwable {
        byte[] alwaysEncodeChars = new byte[1];
        PercentCodec percentCodec = new PercentCodec(alwaysEncodeChars, false);

        Object decoded = percentCodec.decode((Object) null);

        assertNull(decoded);
    }
}
