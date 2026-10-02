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
     * Verifies that encoding a string with special characters does not reset the
     * encodeBlanks flag: after enabling blank encoding and calling encode(), the
     * flag must still report true.
     */
    @Test(timeout = 4000)
    public void test_encodeBlanks_flagPersistsAfterEncoding() throws Throwable {
        QCodec qCodec = new QCodec();

        // Enable the option that maps space characters to underscores during encoding
        qCodec.setEncodeBlanks(true);

        // Encode a string that contains a space (and other special chars such as '=' and ']')
        // to exercise the encode path while blanks encoding is active
        qCodec.encode("=rcy4cI]MK] ]-");

        // The encodeBlanks flag must remain true after encoding — it is a persistent setting
        assertTrue(qCodec.isEncodeBlanks());
    }
}
