package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test16 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * getMinRating returns the lowest rating value (1) for any combined name
     * length greater than 12. A sum length of 21 falls into that final bracket.
     */
    @Test(timeout = 4000)
    public void getMinRatingReturnsOneForLargeSumLength() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        int minRating = encoder.getMinRating(21);

        assertEquals(1, minRating);
    }
}
