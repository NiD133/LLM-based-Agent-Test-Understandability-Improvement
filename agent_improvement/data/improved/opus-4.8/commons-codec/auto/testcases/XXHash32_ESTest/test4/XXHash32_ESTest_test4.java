package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class XXHash32_ESTest_test4 extends XXHash32_ESTest_scaffolding {

    /**
     * Verifies that after calling {@link XXHash32#reset()} on a freshly created
     * instance (seed = 0, no data fed in), the checksum reports the well-known
     * xxHash32 value for an empty input.
     */
    @Test(timeout = 4000)
    public void resetOnEmptyHashYieldsKnownValue() throws Throwable {
        XXHash32 hash = new XXHash32(0);

        hash.reset();

        long expectedEmptyHash = 46947589L;
        assertEquals(expectedEmptyHash, hash.getValue());
    }
}
