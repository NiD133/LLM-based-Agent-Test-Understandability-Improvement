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
     * Two clearly dissimilar names should not be considered homophonous by the
     * Match Rating Approach algorithm, so {@code isEncodeEquals} returns false.
     */
    @Test(timeout = 4000)
    public void isEncodeEquals_returnsFalse_forDissimilarNames() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean areHomophonous = encoder.isEncodeEquals("ZZ", "u96p[In]=-vK={K5");

        assertFalse(areHomophonous);
    }
}
