package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test02 extends URLCodec_ESTest_scaffolding {

    /**
     * Decoding a www-form-urlencoded string should leave URL-safe characters
     * untouched and turn each "%HH" escape back into the character it represents.
     * Here "%25" decodes to '%' and "%2B" decodes to '+', so "*aAC%25%2B"
     * becomes "*aAC%+".
     */
    @Test(timeout = 4000)
    public void decodeRestoresPercentEscapedCharacters() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        String decoded = urlCodec.decode("*aAC%25%2B");

        assertNotNull(decoded);
        assertEquals("*aAC%+", decoded);
    }
}
