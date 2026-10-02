package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test10 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // isEncodeEquals returns false immediately when name2 is a single space,
    // because a blank/whitespace-only name is treated as trivial (no valid phonetic content).
    @Test(timeout = 4000)
    public void test10_isEncodeEquals_returnsFalse_whenSecondNameIsBlankSpace() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        String nameWithSpecialChars = "M:]!J~";
        String blankSpace = " ";

        boolean result = encoder.isEncodeEquals(nameWithSpecialChars, blankSpace);

        assertFalse(result);
    }
}
