package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test16 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // sumLength > 12 falls into the lowest min-rating bucket (1) per the MRA spec
    @Test(timeout = 4000)
    public void test16_getMinRating_returnsOne_whenSumLengthExceedsTwelve() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        int minRating = encoder.getMinRating(21);
        assertEquals(1, minRating);
    }
}
