package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test21 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * A single-character input is treated as trivial ("NINO") by the encoder
     * and is short-circuited to an empty string before any phonetic encoding.
     */
    @Test(timeout = 4000)
    public void encodeSingleCharacterReturnsEmptyString() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode("4");

        assertEquals("", encoded);
    }
}
