package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test01 extends MurmurHash2_ESTest_scaffolding {

    /**
     * Verifies that the 64-bit string overload of {@code hash64} produces the
     * documented, deterministic hash value for a fixed input string. The
     * overload uses UTF-8 encoding and the default seed (0xe17a1465), so the
     * same input must always yield the same hash.
     */
    @Test(timeout = 4000)
    public void hash64OfStringReturnsExpectedValue() throws Throwable {
        final String input = "BG7{/@,";
        final long expectedHash = 8897355786490055066L;

        final long actualHash = MurmurHash2.hash64(input);

        assertEquals(expectedHash, actualHash);
    }
}
