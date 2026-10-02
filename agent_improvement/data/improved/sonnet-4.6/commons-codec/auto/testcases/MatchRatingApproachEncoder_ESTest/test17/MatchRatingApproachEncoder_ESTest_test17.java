package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test17 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Verifies that two phonetically dissimilar strings containing special characters
     * and mixed case are not considered equal by the Match Rating Approach algorithm.
     */
    @Test(timeout = 4000)
    public void test_isEncodeEquals_returnsFalse_forPhoneticallydissimilarStringsWithSpecialChars() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        // "H7mK+_" and "k!zbM" differ phonetically after MRA preprocessing
        boolean encodingsMatch = encoder.isEncodeEquals("H7mK+_", "k!zbM");

        assertFalse(encodingsMatch);
    }
}
