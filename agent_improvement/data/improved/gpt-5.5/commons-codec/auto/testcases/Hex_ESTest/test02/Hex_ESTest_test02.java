package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test02 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Hex hexWithUtf8Charset = new Hex("UTF-8");

        assertEquals("UTF-8", hexWithUtf8Charset.getCharsetName());
    }
}
