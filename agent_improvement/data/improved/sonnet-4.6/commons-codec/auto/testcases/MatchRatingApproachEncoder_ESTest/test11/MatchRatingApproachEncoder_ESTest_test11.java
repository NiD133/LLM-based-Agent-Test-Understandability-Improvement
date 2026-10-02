package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test11 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * isEncodeEquals returns false when the second name is an empty string,
     * because the MRA algorithm treats empty input as trivial (no phonetic
     * comparison is possible).
     */
    @Test(timeout = 4000)
    public void test11_isEncodeEquals_returnsFalse_whenSecondNameIsEmpty() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        String nonEmptyName = "DK^/JQ";
        String emptyName = "";

        boolean result = encoder.isEncodeEquals(nonEmptyName, emptyName);

        assertFalse("isEncodeEquals should return false when the second name is empty", result);
    }
}
