package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test07 extends MurmurHash2_ESTest_scaffolding {

    private static final String INPUT_WITH_DELETE_CHARACTER = "G,5 2lXZ\u007F1083";
    private static final long EXPECTED_64_BIT_HASH = -199748896782609694L;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        long actualHash = MurmurHash2.hash64(INPUT_WITH_DELETE_CHARACTER);

        assertEquals(EXPECTED_64_BIT_HASH, actualHash);
    }
}
