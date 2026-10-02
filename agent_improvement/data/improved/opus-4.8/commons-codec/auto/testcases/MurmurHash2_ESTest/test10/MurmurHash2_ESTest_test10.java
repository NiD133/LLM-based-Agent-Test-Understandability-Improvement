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

    /**
     * Hashing a zero-length substring is equivalent to hashing the empty
     * string. Here the substring of "" starting at index 0 with length 0 is
     * itself "", so hash32 returns the fixed hash of the empty input.
     */
    @Test(timeout = 4000)
    public void hash32OfEmptySubstringReturnsEmptyStringHash() throws Throwable {
        final String text = "";
        final int from = 0;
        final int length = 0;
        final int expectedHashOfEmptyString = 275646681;

        int actualHash = MurmurHash2.hash32(text, from, length);

        assertEquals(expectedHashOfEmptyString, actualHash);
    }
}
