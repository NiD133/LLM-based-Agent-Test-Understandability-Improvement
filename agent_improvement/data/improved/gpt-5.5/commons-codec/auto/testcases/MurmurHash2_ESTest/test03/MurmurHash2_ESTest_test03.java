package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test03 extends MurmurHash2_ESTest_scaffolding {

    private static final String INPUT_TEXT = "'J-f";
    private static final long EXPECTED_HASH64 = 5768382861705900380L;

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        final long actualHash = MurmurHash2.hash64(INPUT_TEXT);

        assertEquals(EXPECTED_HASH64, actualHash);
    }
}
