package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test11 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * isEncodeEquals returns false when either name is empty, since an empty
     * string is treated as trivial (NINO) input and never matches.
     */
    @Test(timeout = 4000)
    public void isEncodeEqualsReturnsFalseWhenSecondNameIsEmpty() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesAreHomophonous = encoder.isEncodeEquals("DK^/JQ", "");

        assertFalse(namesAreHomophonous);
    }
}
