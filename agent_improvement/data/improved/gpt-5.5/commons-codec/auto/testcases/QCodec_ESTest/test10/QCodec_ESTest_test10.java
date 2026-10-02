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

    private static final String Q_ENCODED_TEXT = "=?UTF-8?Q?=3Drcy4cI]MK] ]-?=";
    private static final String DECODED_TEXT = "=rcy4cI]MK] ]-";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        QCodec codec = new QCodec();

        Object decodedText = codec.decode((Object) Q_ENCODED_TEXT);

        assertEquals(DECODED_TEXT, decodedText);
    }
}
