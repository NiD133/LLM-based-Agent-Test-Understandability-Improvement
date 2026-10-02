package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test07 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Two identical names are always considered homophonous: isEncodeEquals short-circuits
     * to {@code true} when the inputs match (case-insensitively), without running the full
     * Match Rating Approach comparison.
     */
    @Test(timeout = 4000)
    public void identicalNamesAreEncodeEqual() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesAreEqual = encoder.isEncodeEquals("AYYNYC", "AYYNYC");

        assertTrue("Identical names should be reported as encode-equal", namesAreEqual);
    }
}
