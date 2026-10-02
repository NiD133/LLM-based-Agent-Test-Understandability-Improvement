package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test00 extends Nysiis_ESTest_scaffolding {

    /**
     * Verifies that the NYSIIS encoder in non-strict mode (no 6-character length cap)
     * correctly encodes an input containing special characters, mixed case, and digits.
     * The encoder strips non-alphabetic characters, uppercases the result, and applies
     * phonetic substitution rules to produce the NYSIIS key.
     */
    @Test(timeout = 4000)
    public void test00_nonStrictMode_encodesSpecialCharInputToExpectedNysiisKey() throws Throwable {
        // strict=false allows the encoded key to exceed 6 characters
        Nysiis nonStrictEncoder = new Nysiis(false);

        // Input has special chars and mixed case; after cleaning and phonetic
        // substitution the expected NYSIIS key is "ZNSDCXDC"
        String nysiisKey = nonStrictEncoder.nysiis("&:ZN(sDK;@X'DhCe");

        assertEquals("ZNSDCXDC", nysiisKey);
    }
}
