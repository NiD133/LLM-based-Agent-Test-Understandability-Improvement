package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test08 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // The MRA algorithm rejects any comparison where either name is a single character.
    // This test confirms that a single-character second argument causes isEncodeEquals to return false,
    // regardless of how long or complex the first argument is.
    @Test(timeout = 4000)
    public void test_isEncodeEquals_returnsFalse_whenSecondNameIsSingleCharacter() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        boolean result = encoder.isEncodeEquals("T7pioZh#;op/_0`\".?", "z");
        assertFalse(result);
    }
}
