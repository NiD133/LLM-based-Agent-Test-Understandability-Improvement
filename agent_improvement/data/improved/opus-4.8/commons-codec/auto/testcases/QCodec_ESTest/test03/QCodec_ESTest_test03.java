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
public class QCodec_ESTest_test03 extends QCodec_ESTest_scaffolding {

    /**
     * Verifies that enabling blank encoding via setEncodeBlanks(true) is
     * reflected by isEncodeBlanks(), and that encoding a string while the
     * flag is set completes without altering the flag.
     */
    @Test(timeout = 4000)
    public void encodeWithEncodeBlanksEnabledKeepsFlagTrue() throws Throwable {
        QCodec qCodec = new QCodec();

        qCodec.setEncodeBlanks(true);
        qCodec.encode("=rcy4cI]MK] ]-");

        assertTrue("blank encoding flag should remain enabled after encoding",
                qCodec.isEncodeBlanks());
    }
}
