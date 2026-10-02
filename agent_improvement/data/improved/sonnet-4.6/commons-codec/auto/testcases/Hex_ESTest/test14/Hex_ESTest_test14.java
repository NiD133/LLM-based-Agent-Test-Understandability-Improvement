package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test14 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void defaultHexInstanceUsesUtf8Charset() throws Throwable {
        Hex hex = new Hex();
        String charsetName = hex.getCharsetName();
        assertEquals("UTF-8", charsetName);
    }
}
