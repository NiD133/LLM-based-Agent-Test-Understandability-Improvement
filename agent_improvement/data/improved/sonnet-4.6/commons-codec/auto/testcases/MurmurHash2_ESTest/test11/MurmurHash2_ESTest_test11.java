package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test11 extends MurmurHash2_ESTest_scaffolding {

    /**
     * hash64(String, int from, int length) internally calls text.substring(from, from + length).
     * When 'from' exceeds the string's length, substring throws StringIndexOutOfBoundsException.
     * Here "sM" has length 2, but from=367 is far out of bounds.
     */
    @Test(timeout = 4000)
    public void test_hash64WithFromIndexBeyondStringLength_throwsStringIndexOutOfBoundsException() throws Throwable {
        String input = "sM";        // length 2
        int from = 367;             // start index far beyond string length
        int length = 367;

        try {
            MurmurHash2.hash64(input, from, length);
            fail("Expecting exception: StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {
            // expected: substring(367, 734) on a 2-char string is out of bounds
        }
    }
}
