package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test05 extends MurmurHash2_ESTest_scaffolding {

    private static final String INPUT_TEXT = "M78]F%@JR*";
    private static final long EXPECTED_HASH = 8542654808481837325L;

    /**
     * Verifies that hash64(String) produces a stable, deterministic hash
     * for a known 10-character ASCII input using the default seed.
     */
    @Test(timeout = 4000)
    public void test_hash64_string_returnsKnownHashForFixedInput() throws Throwable {
        long actualHash = MurmurHash2.hash64(INPUT_TEXT);
        assertEquals(EXPECTED_HASH, actualHash);
    }
}
