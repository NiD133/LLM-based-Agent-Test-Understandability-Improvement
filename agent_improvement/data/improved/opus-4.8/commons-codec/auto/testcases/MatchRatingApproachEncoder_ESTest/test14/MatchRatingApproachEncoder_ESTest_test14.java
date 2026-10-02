package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test14 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * isEncodeEquals treats an empty string as trivial input ("NINO": Nothing In,
     * Nothing Out) and bails out with {@code false} before any phonetic comparison.
     * So comparing two empty strings is never considered a match.
     */
    @Test(timeout = 4000)
    public void comparingTwoEmptyStringsIsNotAMatch() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesAreHomophonous = encoder.isEncodeEquals("", "");

        assertFalse(namesAreHomophonous);
    }
}
