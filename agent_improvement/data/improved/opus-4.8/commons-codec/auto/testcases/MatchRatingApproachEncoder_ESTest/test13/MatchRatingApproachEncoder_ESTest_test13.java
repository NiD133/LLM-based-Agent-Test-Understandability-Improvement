package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test13 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * isEncodeEquals treats a single blank space as trivial input and short-circuits
     * to {@code false} before any phonetic comparison is performed, even when both
     * names are the identical blank string.
     */
    @Test(timeout = 4000)
    public void isEncodeEqualsReturnsFalseForBlankNames() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesAreHomophonous = encoder.isEncodeEquals(" ", " ");

        assertFalse("Blank space names are rejected as trivial input", namesAreHomophonous);
    }
}
