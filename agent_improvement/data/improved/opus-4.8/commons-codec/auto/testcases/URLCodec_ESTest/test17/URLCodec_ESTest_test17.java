package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.assertNull;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test17 extends URLCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding a {@code null} byte array returns {@code null}
     * rather than throwing or returning an empty array.
     */
    @Test(timeout = 4000)
    public void decodeUrl_withNullInput_returnsNull() throws Throwable {
        byte[] decoded = URLCodec.decodeUrl((byte[]) null);

        assertNull("Decoding a null byte array should return null", decoded);
    }
}
