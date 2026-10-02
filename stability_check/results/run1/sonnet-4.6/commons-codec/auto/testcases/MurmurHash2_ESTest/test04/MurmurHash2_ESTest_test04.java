package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test04 extends MurmurHash2_ESTest_scaffolding {

    // A mixed-character string containing letters, digits, punctuation, and special chars,
    // used to verify the 64-bit hash produces a stable, deterministic result.
    private static final String MIXED_CHARS_INPUT = "y\"1B>9T<!hw2,E^U'vm";
    private static final long EXPECTED_HASH_FOR_MIXED_CHARS_INPUT = -1450341502340637772L;

    @Test(timeout = 4000)
    public void test04_hash64_mixedCharacterString_returnsExpectedHash() throws Throwable {
        long actualHash = MurmurHash2.hash64(MIXED_CHARS_INPUT);
        assertEquals(EXPECTED_HASH_FOR_MIXED_CHARS_INPUT, actualHash);
    }
}
