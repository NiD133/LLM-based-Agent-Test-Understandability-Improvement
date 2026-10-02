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

    /**
     * encode(Object) must reject inputs that are not Strings by throwing an
     * exception, since the Match Rating Approach algorithm only operates on text.
     */
    @Test(timeout = 4000)
    public void encodeRejectsNonStringInput() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        Object nonStringInput = new Object();

        try {
            encoder.encode(nonStringInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            // Parameter supplied to Match Rating Approach encoder is not of type java.lang.String
            verifyException("org.apache.commons.codec.language.MatchRatingApproachEncoder", e);
        }
    }
}
