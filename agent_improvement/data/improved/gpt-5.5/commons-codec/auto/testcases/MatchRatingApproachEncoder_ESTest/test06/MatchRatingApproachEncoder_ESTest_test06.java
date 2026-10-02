package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test06 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    private static final String PUNCTUATED_INPUT = ",-l";
    private static final String DOUBLE_R_INPUT = "RR";

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean encodedValuesMatch = encoder.isEncodeEquals(PUNCTUATED_INPUT, DOUBLE_R_INPUT);

        assertTrue(encodedValuesMatch);
    }
}
