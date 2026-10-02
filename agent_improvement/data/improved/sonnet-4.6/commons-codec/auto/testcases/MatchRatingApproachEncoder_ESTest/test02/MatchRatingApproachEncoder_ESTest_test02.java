package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test02 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Verifies that isEncodeEquals returns false when one name is a two-letter
     * double-consonant ("ZZ") and the other contains special characters and digits
     * that produce a completely different MRA encoding.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        // "ZZ" encodes to a single consonant "Z" after double-consonant removal.
        // "u96p[In]=-vK={K5" encodes to a mix of letters and digits after cleaning.
        // The two encodings are phonetically dissimilar, so isEncodeEquals must return false.
        boolean encodingsAreEqual = encoder.isEncodeEquals("ZZ", "u96p[In]=-vK={K5");

        assertFalse(encodingsAreEqual);
    }
}
