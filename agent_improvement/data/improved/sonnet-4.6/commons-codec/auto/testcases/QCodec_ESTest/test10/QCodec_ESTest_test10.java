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
public class QCodec_ESTest_test10 extends QCodec_ESTest_scaffolding {

    /**
     * Verifies that QCodec decodes an RFC 1522 Q-encoded word correctly.
     *
     * The input "=?UTF-8?Q?=3Drcy4cI]MK] ]-?=" follows the encoded-word format:
     *   =?<charset>?Q?<encoded-text>?=
     * The quoted-printable escape "=3D" decodes to "=" (ASCII 0x3D),
     * so the full decoded result is "=rcy4cI]MK] ]-".
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        QCodec qCodec = new QCodec();

        // Input: RFC 1522 Q-encoded word using UTF-8 charset; "=3D" is QP-escape for "="
        String encodedWord = "=?UTF-8?Q?=3Drcy4cI]MK] ]-?=";

        Object decodedResult = qCodec.decode((Object) encodedWord);

        assertEquals("=rcy4cI]MK] ]-", decodedResult);
    }
}
