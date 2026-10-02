package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test06 extends QCodec_ESTest_scaffolding {

    /**
     * Decoding a null byte array should return null rather than throwing,
     * as specified by {@link QCodec#doDecoding(byte[])}.
     */
    @Test(timeout = 4000)
    public void doDecodingWithNullInputReturnsNull() throws Throwable {
        QCodec qCodec = new QCodec();

        byte[] decoded = qCodec.doDecoding((byte[]) null);

        assertNull(decoded);
    }
}
