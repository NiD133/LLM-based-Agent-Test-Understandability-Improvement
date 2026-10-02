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
     * Encoding a null name should be handled gracefully by returning an empty
     * string rather than throwing (the "NINO" / trivial-input guard in encode).
     */
    @Test(timeout = 4000)
    public void encodeNullNameReturnsEmptyString() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String encoded = encoder.encode((String) null);

        assertEquals("", encoded);
    }
}
