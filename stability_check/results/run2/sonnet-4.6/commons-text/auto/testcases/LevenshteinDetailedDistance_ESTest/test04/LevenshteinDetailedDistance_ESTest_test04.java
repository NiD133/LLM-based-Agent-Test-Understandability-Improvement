package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test04 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that applying the default Levenshtein distance calculator with
     * both null SimilarityInput arguments throws an IllegalArgumentException.
     * The error message should indicate that CharSequences must not be null.
     */
    @Test(timeout = 4000)
    public void test_applyWithBothNullSimilarityInputs_throwsIllegalArgumentException() throws Throwable {
        LevenshteinDetailedDistance defaultInstance = LevenshteinDetailedDistance.getDefaultInstance();
        SimilarityInput<Object> nullLeft = null;
        SimilarityInput<Object> nullRight = null;

        try {
            defaultInstance.apply(nullLeft, nullRight);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected: both null inputs are rejected with "CharSequences must not be null"
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}
