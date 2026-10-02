package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test23 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Encoding a {@code null} name is treated as trivial input and yields an empty string.
     */
    @Test(timeout = 4000)
    public void encodeNullStringReturnsEmptyString() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode((String) null);

        assertEquals("", encoded);
    }
}
