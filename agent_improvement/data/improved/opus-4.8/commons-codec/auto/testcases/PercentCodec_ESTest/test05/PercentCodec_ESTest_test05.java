package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test05 extends PercentCodec_ESTest_scaffolding {

    /**
     * Encoding a null byte array should return null rather than throwing.
     */
    @Test(timeout = 4000)
    public void encodeNullByteArrayReturnsNull() throws Throwable {
        PercentCodec percentCodec = new PercentCodec();

        byte[] encoded = percentCodec.encode((byte[]) null);

        assertNull(encoded);
    }
}
