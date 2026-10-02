package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test24 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        MatchRatingApproachEncoder matchRatingApproachEncoder0 = new MatchRatingApproachEncoder();
        Object object0 = new Object();
        try {
            matchRatingApproachEncoder0.encode(object0);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Parameter supplied to Match Rating Approach encoder is not of type java.lang.String
            //
            verifyException("org.apache.commons.codec.language.MatchRatingApproachEncoder", e);
        }
    }
}
