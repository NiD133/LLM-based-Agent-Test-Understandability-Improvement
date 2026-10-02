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
public class QCodec_ESTest_test05 extends QCodec_ESTest_scaffolding {

    /**
     * Decoding an RFC 1522 "Q" encoded-word should reverse the encoding:
     *   - the "=?charset?Q?...?=" wrapper is stripped,
     *   - the "=3D" escape sequence becomes the literal '=' character,
     *   - the underscore is converted back into a space.
     */
    @Test(timeout = 4000)
    public void decodeReversesQuotedPrintableEscapesAndUnderscore() throws Throwable {
        QCodec qCodec = new QCodec();

        String decoded = qCodec.decode("=?UTF-8?Q?=3Drcy4cI]MK]_]-?=");

        assertEquals("=rcy4cI]MK] ]-", decoded);
    }
}
