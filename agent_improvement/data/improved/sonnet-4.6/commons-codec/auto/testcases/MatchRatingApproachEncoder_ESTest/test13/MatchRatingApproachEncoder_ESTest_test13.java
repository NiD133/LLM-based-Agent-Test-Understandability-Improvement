package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test13 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Verifies that isEncodeEquals returns false when both inputs are a single space.
     * The MRA algorithm treats whitespace-only strings as trivial/invalid input and
     * rejects them immediately without performing phonetic comparison.
     */
    @Test(timeout = 4000)
    public void test_isEncodeEquals_bothInputsSingleSpace_returnsFalse() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        boolean result = encoder.isEncodeEquals(" ", " ");
        assertFalse(result);
    }
}
