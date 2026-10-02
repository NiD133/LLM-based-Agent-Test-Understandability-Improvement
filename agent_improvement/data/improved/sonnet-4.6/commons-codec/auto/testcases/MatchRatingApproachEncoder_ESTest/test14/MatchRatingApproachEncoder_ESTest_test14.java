package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test14 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // isEncodeEquals returns false immediately when either name is empty,
    // because an empty string cannot be a valid phonetic match.
    @Test(timeout = 4000)
    public void test14_isEncodeEquals_bothEmptyStrings_returnsFalse() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        boolean result = encoder.isEncodeEquals("", "");
        assertFalse(result);
    }
}
