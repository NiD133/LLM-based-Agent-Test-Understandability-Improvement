package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test12 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Verifies that {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)}
     * returns {@code false} when the second name is {@code null}, since a null input
     * cannot be a homophone of any other name.
     */
    @Test(timeout = 4000)
    public void isEncodeEqualsReturnsFalseWhenSecondNameIsNull() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesAreHomophones = encoder.isEncodeEquals(")-", (String) null);

        assertFalse("A null second name should never match", namesAreHomophones);
    }
}
