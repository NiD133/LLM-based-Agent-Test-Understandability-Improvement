package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test19 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // getMinRating maps the combined encoded-name length to a minimum similarity threshold.
    // A sumLength of exactly 12 is the boundary that produces the lowest passing threshold of 2
    // before falling through to the default value of 1 for anything longer.
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        int minRating = encoder.getMinRating(12);
        assertEquals(2, minRating);
    }
}
