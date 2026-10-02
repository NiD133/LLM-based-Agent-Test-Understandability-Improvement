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

    /**
     * Constructing a Hex codec from a charset name should make that same
     * charset name retrievable via {@link Hex#getCharsetName()}.
     */
    @Test(timeout = 4000)
    public void constructorWithCharsetNameExposesSameCharsetName() throws Throwable {
        Hex hexWithUtf8 = new Hex("UTF-8");

        assertEquals("UTF-8", hexWithUtf8.getCharsetName());
    }
}
