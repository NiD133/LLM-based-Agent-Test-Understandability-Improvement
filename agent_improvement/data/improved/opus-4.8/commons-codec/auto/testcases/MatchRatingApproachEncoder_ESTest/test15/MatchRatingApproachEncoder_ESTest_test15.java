package org.apache.commons.codec.language;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test15 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * A null first name can never be homophonous with another name, so
     * {@link MatchRatingApproachEncoder#isEncodeEquals(String, String)} should
     * report the two names as not equal.
     */
    @Test(timeout = 4000)
    public void isEncodeEqualsReturnsFalseWhenFirstNameIsNull() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        boolean namesAreEqual = encoder.isEncodeEquals(null, "");

        assertFalse(namesAreEqual);
    }
}
