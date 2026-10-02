package org.apache.commons.codec.net;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test14 extends URLCodec_ESTest_scaffolding {

    /**
     * Encoding a null byte array should return null rather than throwing,
     * mirroring the null-safety contract of {@link URLCodec#encode(byte[])}.
     */
    @Test(timeout = 4000)
    public void encodeNullByteArrayReturnsNull() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        byte[] encoded = urlCodec.encode((byte[]) null);

        assertNull("Encoding a null byte array must return null", encoded);
    }
}
