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
public class QCodec_ESTest_test09 extends QCodec_ESTest_scaffolding {

    /**
     * Verifies that a QCodec constructed with the system default Charset
     * does not encode blank (space) characters by default.
     */
    @Test(timeout = 4000)
    public void test_newQCodecWithDefaultCharset_encodeBlanksIsFalseByDefault() throws Throwable {
        Charset defaultCharset = Charset.defaultCharset();
        QCodec qCodec = new QCodec(defaultCharset);

        boolean encodeBlanks = qCodec.isEncodeBlanks();

        assertFalse("QCodec should not encode blanks unless explicitly enabled", encodeBlanks);
    }
}
