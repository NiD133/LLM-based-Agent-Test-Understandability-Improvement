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

    private static final String PLAIN_TEXT = "}~";
    private static final String RFC_1522_BASE64_TEXT = "=?UTF-8?B?fX4=?=";

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        BCodec codec = new BCodec();

        Object encodedText = codec.encode((Object) PLAIN_TEXT);

        assertNotNull(encodedText);
        assertEquals(RFC_1522_BASE64_TEXT, encodedText);
    }
}
