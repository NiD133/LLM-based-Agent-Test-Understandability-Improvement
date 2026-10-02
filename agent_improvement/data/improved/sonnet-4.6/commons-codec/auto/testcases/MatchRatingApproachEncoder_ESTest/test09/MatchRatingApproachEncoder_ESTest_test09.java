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

    /**
     * A single-character first name is too short for the MRA algorithm to compare,
     * so isEncodeEquals must return false regardless of the second name.
     */
    @Test(timeout = 4000)
    public void test_isEncodeEquals_returnsFalse_whenFirstNameIsSingleCharacter() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String singleCharName = "O";
        String multiCharName = ")^[N]\"*0f'jGO`";

        boolean result = encoder.isEncodeEquals(singleCharName, multiCharName);

        assertFalse(result);
    }
}
