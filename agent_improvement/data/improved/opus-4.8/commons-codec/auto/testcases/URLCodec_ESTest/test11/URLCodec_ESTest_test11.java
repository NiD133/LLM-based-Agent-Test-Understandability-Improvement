package org.apache.commons.codec.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test11 extends URLCodec_ESTest_scaffolding {

    /**
     * Verifies that {@link URLCodec#encode(String)} converts a string with spaces
     * into its www-form-urlencoded form: spaces become '+' while the unreserved
     * alphabetic characters are left unchanged.
     */
    @Test(timeout = 4000)
    public void encodeStringWithSpaces_replacesSpacesWithPlus() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        String encoded = urlCodec.encode(" cannot be URL decoded");

        assertNotNull(encoded);
        assertEquals("+cannot+be+URL+decoded", encoded);
    }
}
