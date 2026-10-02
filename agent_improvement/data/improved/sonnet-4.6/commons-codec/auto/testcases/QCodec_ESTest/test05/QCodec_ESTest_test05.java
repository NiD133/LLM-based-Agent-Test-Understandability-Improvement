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

    // RFC 1522 encoded word format: =?charset?encoding?encoded_text?=
    // "=3D" is the Q-encoded form of '=' (hex 3D), and '_' decodes to a space character.
    private static final String RFC1522_ENCODED_INPUT  = "=?UTF-8?Q?=3Drcy4cI]MK]_]-?=";
    private static final String EXPECTED_DECODED_OUTPUT = "=rcy4cI]MK] ]-";

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        QCodec qCodec = new QCodec();

        String decoded = qCodec.decode(RFC1522_ENCODED_INPUT);

        assertEquals(EXPECTED_DECODED_OUTPUT, decoded);
    }
}
