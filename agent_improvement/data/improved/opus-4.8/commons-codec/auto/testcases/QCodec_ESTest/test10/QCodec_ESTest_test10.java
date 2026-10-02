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
     * Decoding a full RFC 1522 "encoded-word" via decode(Object) should strip the
     * "=?charset?Q?...?=" envelope and convert the quoted-printable "=3D" escape
     * back into a literal '=' character, leaving the remaining text untouched.
     */
    @Test(timeout = 4000)
    public void decodeObjectStripsEnvelopeAndUnescapesQuotedPrintable() throws Throwable {
        QCodec qCodec = new QCodec();

        String encodedWord = "=?UTF-8?Q?=3Drcy4cI]MK] ]-?=";
        Object decoded = qCodec.decode((Object) encodedWord);

        assertEquals("=rcy4cI]MK] ]-", decoded);
    }
}
