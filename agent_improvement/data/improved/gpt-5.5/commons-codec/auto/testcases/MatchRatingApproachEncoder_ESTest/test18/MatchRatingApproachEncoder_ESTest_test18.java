package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test18 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    private static final String PUNCTUATION_HEAVY_NAME = "-}\u007F_";
    private static final String MIXED_CASE_SYMBOL_NAME = "Jj~(";

    @Test(timeout = 4000)
    public void test18() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesMatch = encoder.isEncodeEquals(PUNCTUATION_HEAVY_NAME, MIXED_CASE_SYMBOL_NAME);

        assertFalse(namesMatch);
    }
}
