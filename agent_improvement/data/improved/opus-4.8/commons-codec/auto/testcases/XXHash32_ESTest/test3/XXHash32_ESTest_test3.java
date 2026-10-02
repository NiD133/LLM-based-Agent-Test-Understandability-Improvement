package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test3 extends XXHash32_ESTest_scaffolding {

    /**
     * A freshly created XXHash32 (default seed of 0) that has not been fed any
     * data should report the hash of the empty input. For xxHash32 with seed 0
     * this well-known value is 46947589.
     */
    @Test(timeout = 4000)
    public void getValueOfEmptyInputReturnsHashOfEmptyData() throws Throwable {
        XXHash32 emptyHash = new XXHash32();

        long hashOfNoData = emptyHash.getValue();

        assertEquals(46947589L, hashOfNoData);
    }
}
