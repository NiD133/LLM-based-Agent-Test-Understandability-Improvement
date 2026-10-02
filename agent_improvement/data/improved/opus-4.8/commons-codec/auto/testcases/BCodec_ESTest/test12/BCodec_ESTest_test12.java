package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.Charset;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BCodec_ESTest_test12 extends BCodec_ESTest_scaffolding {

    /**
     * Encoding an Object overload that wraps a String should produce an
     * RFC 1522 "B" encoded-word using the default UTF-8 charset.
     * For the input "}~" the Base64 of its UTF-8 bytes is "fX4=", so the
     * full encoded-word is "=?UTF-8?B?fX4=?=".
     */
    @Test(timeout = 4000)
    public void testEncodeObjectWrapsStringAsBase64EncodedWord() throws Throwable {
        BCodec bCodec = new BCodec();

        Object encoded = bCodec.encode((Object) "}~");

        assertNotNull(encoded);
        assertEquals("=?UTF-8?B?fX4=?=", encoded);
    }
}
