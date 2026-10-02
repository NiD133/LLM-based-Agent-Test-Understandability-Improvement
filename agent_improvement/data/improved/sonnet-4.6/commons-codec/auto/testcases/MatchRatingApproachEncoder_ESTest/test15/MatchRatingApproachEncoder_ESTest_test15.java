package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test15 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    // isEncodeEquals returns false when the first name is null,
    // because a null input cannot be phonetically compared.
    @Test(timeout = 4000)
    public void test_isEncodeEquals_returnsFalse_whenFirstNameIsNull() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();
        boolean result = encoder.isEncodeEquals(null, "");
        assertFalse(result);
    }
}
