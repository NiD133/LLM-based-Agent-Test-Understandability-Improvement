package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test12 extends URLCodec_ESTest_scaffolding {

    /**
     * Verifies that {@link URLCodec#encode(String)} percent-escapes the characters
     * that are not URL-safe while leaving safe characters untouched. In the input
     * "*aAC%+", the letters, '*' are www-form-url safe and pass through unchanged,
     * whereas '%' becomes "%25" and '+' becomes "%2B".
     */
    @Test(timeout = 4000)
    public void encodeEscapesUnsafeCharacters() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        String encoded = urlCodec.encode("*aAC%+");

        assertNotNull(encoded);
        assertEquals("*aAC%25%2B", encoded);
    }
}
