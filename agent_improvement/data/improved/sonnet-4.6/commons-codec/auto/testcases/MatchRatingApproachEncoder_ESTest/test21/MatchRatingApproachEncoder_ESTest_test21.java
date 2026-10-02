package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test21 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // The MRA algorithm requires at least 2 characters to produce a code;
    // a single-character input (including digits) must return an empty string.
    @Test(timeout = 4000)
    public void test_encodeSingleCharacterInput_returnsEmpty() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String result = encoder.encode("4");

        assertEquals("", result);
    }
}
