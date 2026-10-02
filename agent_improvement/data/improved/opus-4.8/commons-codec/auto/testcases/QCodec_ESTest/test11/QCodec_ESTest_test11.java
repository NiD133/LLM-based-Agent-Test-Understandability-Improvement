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
public class QCodec_ESTest_test11 extends QCodec_ESTest_scaffolding {

    /**
     * A QCodec created with a named charset should not transform blanks (spaces)
     * by default; isEncodeBlanks() must return false until setEncodeBlanks(true)
     * is called.
     */
    @Test(timeout = 4000)
    public void encodeBlanksIsDisabledByDefault() throws Throwable {
        QCodec qCodec = new QCodec("l2");

        assertFalse(qCodec.isEncodeBlanks());
    }
}
