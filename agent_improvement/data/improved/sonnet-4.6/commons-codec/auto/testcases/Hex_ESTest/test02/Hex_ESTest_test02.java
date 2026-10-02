package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test02 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_constructorWithCharsetName_storesAndReturnsCharsetName() throws Throwable {
        // Constructing Hex with a charset name should make getCharsetName() return that same name.
        Hex hex = new Hex("UTF-8");
        assertEquals("UTF-8", hex.getCharsetName());
    }
}
