package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test12 extends PercentCodec_ESTest_scaffolding {

    /**
     * Decoding a {@code null} byte array should return {@code null} rather than
     * throwing, as documented by {@link PercentCodec#decode(byte[])}.
     */
    @Test(timeout = 4000)
    public void decodeNullByteArrayReturnsNull() throws Throwable {
        PercentCodec percentCodec = new PercentCodec();

        byte[] decoded = percentCodec.decode((byte[]) null);

        assertNull(decoded);
    }
}
