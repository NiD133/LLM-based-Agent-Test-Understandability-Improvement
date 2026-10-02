package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test01 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * leftToRightThenRightToLeftProcessing cancels out identical characters in matching
     * positions, then returns {@code |6 - longestRemainingLength|}. When both names are the
     * same 16-character string, every character cancels, leaving empty remainders, so the
     * result is {@code |6 - 0| = 6}.
     */
    @Test(timeout = 4000)
    public void identicalNamesYieldSimilarityOfSix() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        String name = "u96p[In]=-vK={K5";

        int similarity = encoder.leftToRightThenRightToLeftProcessing(name, name);

        assertEquals(6, similarity);
    }
}
