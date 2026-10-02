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
        Charset defaultCharset = Charset.defaultCharset();
        QCodec codec = new QCodec(defaultCharset);

        // A new QCodec does not transform spaces unless explicitly configured.
        boolean encodeBlanksByDefault = codec.isEncodeBlanks();

        assertFalse(encodeBlanksByDefault);
    }
}
