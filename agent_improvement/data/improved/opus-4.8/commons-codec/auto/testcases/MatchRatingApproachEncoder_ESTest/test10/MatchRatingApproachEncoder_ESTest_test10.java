package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test10 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * isEncodeEquals returns false when one of the names is a blank space,
     * since a single space is treated as trivial input and short-circuits to false.
     */
    @Test(timeout = 4000)
    public void isEncodeEqualsReturnsFalseWhenSecondNameIsBlank() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesAreHomophonous = encoder.isEncodeEquals("M:]!J~", " ");

        assertFalse(namesAreHomophonous);
    }
}
