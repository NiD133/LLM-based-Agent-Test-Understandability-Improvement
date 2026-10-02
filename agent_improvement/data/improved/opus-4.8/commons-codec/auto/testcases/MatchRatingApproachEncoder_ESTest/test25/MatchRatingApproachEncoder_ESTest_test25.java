package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test25 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * Encoding an empty string via the Object-based {@link MatchRatingApproachEncoder#encode(Object)}
     * overload should short-circuit and return the empty string unchanged.
     */
    @Test(timeout = 4000)
    public void encodeEmptyStringReturnsEmptyString() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        Object encoded = encoder.encode((Object) "");

        assertEquals("", encoded);
    }
}
