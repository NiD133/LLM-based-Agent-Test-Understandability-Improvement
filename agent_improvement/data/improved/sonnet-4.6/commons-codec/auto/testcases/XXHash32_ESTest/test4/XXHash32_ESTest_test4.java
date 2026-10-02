package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test4 extends XXHash32_ESTest_scaffolding {

    // XXHash32 checksum of empty input with seed 0
    private static final long XXHASH32_EMPTY_SEED0 = 46947589L;

    @Test(timeout = 4000)
    public void test_getValueAfterReset_returnsChecksumForEmptyInputWithSeedZero() throws Throwable {
        XXHash32 hasher = new XXHash32(0);
        hasher.reset();
        assertEquals(XXHASH32_EMPTY_SEED0, hasher.getValue());
    }
}
