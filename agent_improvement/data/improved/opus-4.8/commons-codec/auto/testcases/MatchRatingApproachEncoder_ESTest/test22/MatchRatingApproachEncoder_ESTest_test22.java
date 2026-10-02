package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test22 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * A single space is treated as trivial input ("NINO" - nothing in, nothing out),
     * so encoding it yields an empty string.
     */
    @Test(timeout = 4000)
    public void encodeSingleSpaceReturnsEmptyString() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode(" ");

        assertEquals("", encoded);
    }
}
