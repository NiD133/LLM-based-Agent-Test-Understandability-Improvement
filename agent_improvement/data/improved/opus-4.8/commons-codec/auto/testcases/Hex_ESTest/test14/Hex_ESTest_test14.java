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
public class Hex_ESTest_test14 extends Hex_ESTest_scaffolding {

    /**
     * A Hex codec created with the no-argument constructor should use the default
     * charset (UTF-8), so getCharsetName() returns "UTF-8".
     */
    @Test(timeout = 4000)
    public void defaultConstructorUsesUtf8CharsetName() throws Throwable {
        Hex hexWithDefaultCharset = new Hex();

        String charsetName = hexWithDefaultCharset.getCharsetName();

        assertEquals("UTF-8", charsetName);
    }
}
