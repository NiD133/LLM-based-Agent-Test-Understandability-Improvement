package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test18 extends URLCodec_ESTest_scaffolding {

    /**
     * The no-arg constructor should default the encoding to UTF-8,
     * which getEncoding() then reports.
     */
    @Test(timeout = 4000)
    public void defaultConstructorUsesUtf8Encoding() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        String encoding = urlCodec.getEncoding();

        assertEquals("UTF-8", encoding);
    }
}
