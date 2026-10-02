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

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Charset charset0 = Charset.defaultCharset();
        QCodec qCodec0 = new QCodec(charset0);
        boolean boolean0 = qCodec0.isEncodeBlanks();
        assertFalse(boolean0);
    }
}
