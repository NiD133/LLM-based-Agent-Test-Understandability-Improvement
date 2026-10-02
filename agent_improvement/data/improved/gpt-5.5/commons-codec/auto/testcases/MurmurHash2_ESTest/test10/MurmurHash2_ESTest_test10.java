package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test10 extends MurmurHash2_ESTest_scaffolding {

    private static final String EMPTY_TEXT = "";
    private static final int SUBSTRING_START = 0;
    private static final int SUBSTRING_LENGTH = 0;
    private static final int EXPECTED_EMPTY_SUBSTRING_HASH = 275646681;

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        int hash = MurmurHash2.hash32(EMPTY_TEXT, SUBSTRING_START, SUBSTRING_LENGTH);

        assertEquals(EXPECTED_EMPTY_SUBSTRING_HASH, hash);
    }
}
