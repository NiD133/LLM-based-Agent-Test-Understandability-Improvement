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

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        final String textToHash = "y\"1B>9T<!hw2,E^U'vm";
        final long expectedHash = -1450341502340637772L;

        final long actualHash = MurmurHash2.hash64(textToHash);

        assertEquals(expectedHash, actualHash);
    }
}
