package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test04 extends PercentCodec_ESTest_scaffolding {

    /**
     * Encoding a {@code null} Object should return {@code null}, regardless of how the codec is configured.
     */
    @Test(timeout = 4000)
    public void encodeNullObjectReturnsNull() throws Throwable {
        final byte[] alwaysEncodeChars = new byte[1];
        final boolean plusForSpace = true;
        final PercentCodec percentCodec = new PercentCodec(alwaysEncodeChars, plusForSpace);

        final Object result = percentCodec.encode((Object) null);

        assertNull(result);
    }
}
