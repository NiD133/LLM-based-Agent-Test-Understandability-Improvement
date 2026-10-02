package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test09 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    private static final String SINGLE_CHARACTER_NAME = "O";
    private static final String COMPARISON_NAME = ")^[N]\"*0f'jGO`";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesMatch = encoder.isEncodeEquals(SINGLE_CHARACTER_NAME, COMPARISON_NAME);

        assertFalse(namesMatch);
    }
}
