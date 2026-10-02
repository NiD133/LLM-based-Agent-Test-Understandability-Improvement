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

    // BCodec wraps Base64-encoded bytes in RFC 1522 MIME header format: =?charset?B?<base64>?=
    @Test(timeout = 4000)
    public void test_encodeObjectString_producesRfc1522MimeHeader() throws Throwable {
        BCodec bCodec = new BCodec(); // defaults to UTF-8 charset
        String input = "}~";         // two ASCII printable characters

        Object encoded = bCodec.encode((Object) input);

        assertNotNull(encoded);
        // "}~" encodes to "fX4=" in Base64; wrapped in RFC 1522 header gives "=?UTF-8?B?fX4=?="
        assertEquals("=?UTF-8?B?fX4=?=", encoded);
    }
}
