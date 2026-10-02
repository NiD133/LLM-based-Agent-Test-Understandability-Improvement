package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test3 extends XXHash32_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testGetValueReturnsDefaultHashForEmptyInputWithZeroSeed() throws Throwable {
        // XXHash32() uses seed=0; getValue() on empty input produces a known constant
        XXHash32 hasher = new XXHash32();
        long hashOfEmptyInput = hasher.getValue();
        assertEquals(46947589L, hashOfEmptyInput);
    }
}
