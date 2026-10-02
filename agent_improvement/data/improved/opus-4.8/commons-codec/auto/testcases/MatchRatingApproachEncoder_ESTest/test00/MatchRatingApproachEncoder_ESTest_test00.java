package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class MatchRatingApproachEncoder_ESTest_test00 extends MatchRatingApproachEncoder_ESTest_scaffolding {

    /**
     * removeAccents is documented to short-circuit on a {@code null} word and
     * return {@code null} rather than throwing a NullPointerException.
     */
    @Test(timeout = 4000)
    public void removeAccentsReturnsNullForNullInput() throws Throwable {
        MatchRatingApproachEncoder encoder = new MatchRatingApproachEncoder();

        String result = encoder.removeAccents((String) null);

        assertNull("removeAccents(null) should return null", result);
    }
}
