package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test19 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * getMinRating maps a combined name length of exactly 12 to a minimum
     * similarity rating of 2 (the dedicated {@code sumLength == 12} branch).
     */
    @Test(timeout = 4000)
    public void getMinRatingForSumLengthOf12ReturnsMinRating2() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        int minRating = encoder.getMinRating(12);

        assertEquals(2, minRating);
    }
}
