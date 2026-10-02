package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test06 extends QCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding a null byte array returns null,
     * as {@link QCodec#doDecoding(byte[])} treats null input as a no-op.
     */
    @Test(timeout = 4000)
    public void doDecodingWithNullInputReturnsNull() throws Throwable {
        QCodec qCodec = new QCodec();

        byte[] decoded = qCodec.doDecoding((byte[]) null);

        assertNull(decoded);
    }
}
