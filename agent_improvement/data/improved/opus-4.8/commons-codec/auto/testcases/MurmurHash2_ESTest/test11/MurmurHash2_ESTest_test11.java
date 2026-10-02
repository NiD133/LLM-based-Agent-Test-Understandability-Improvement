package org.apache.commons.codec.digest;

import org.junit.Test;
import static org.junit.Assert.fail;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MurmurHash2_ESTest_test11 extends MurmurHash2_ESTest_scaffolding {

    /**
     * hash64(String, from, length) hashes the substring text.substring(from, from + length).
     * Here the text "sM" has only 2 characters, but {@code from} and {@code length} are both 367,
     * so the requested substring range [367, 734) lies far outside the string. The underlying
     * String.substring call therefore throws StringIndexOutOfBoundsException.
     */
    @Test(timeout = 4000)
    public void hash64WithRangeBeyondStringLengthThrows() throws Throwable {
        String shortText = "sM";
        int from = 367;
        int length = 367;

        try {
            MurmurHash2.hash64(shortText, from, length);
            fail("Expected StringIndexOutOfBoundsException because the substring range is outside \"sM\"");
        } catch (StringIndexOutOfBoundsException expected) {
            // Expected: substring(367, 734) is out of bounds for the 2-character string "sM".
        }
    }
}
