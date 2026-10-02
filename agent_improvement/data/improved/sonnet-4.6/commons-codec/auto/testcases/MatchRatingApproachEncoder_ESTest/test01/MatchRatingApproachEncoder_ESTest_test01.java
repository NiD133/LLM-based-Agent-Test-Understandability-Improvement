package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test01 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * When both name arguments are identical, every character matches in both the
     * left-to-right and right-to-left passes, so the surviving (unmatched) portion
     * of each string is empty (length 0).  The method returns {@code 6 - 0 = 6}.
     */
    @Test(timeout = 4000)
    public void test_leftToRightThenRightToLeftProcessing_identicalStrings_returnsSix() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        String name = "u96p[In]=-vK={K5";

        int similarityScore = encoder.leftToRightThenRightToLeftProcessing(name, name);

        assertEquals(6, similarityScore);
    }
}
