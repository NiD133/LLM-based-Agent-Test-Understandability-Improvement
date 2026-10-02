package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test14 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that {@code apply} rejects a null input: when the second
     * {@link SimilarityInput} is null, the distance calculation must fail fast
     * with an {@link IllegalArgumentException} stating that the sequences must
     * not be null.
     */
    @Test(timeout = 4000)
    public void applyWithNullRightInputThrowsIllegalArgumentException() throws Throwable {
        // A non-negative threshold selects the limited-comparison code path.
        Integer threshold = 4293;
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(threshold);

        // A valid (mocked) left input paired with a null right input.
        SimilarityInput<Object> leftInput = mock(SimilarityInput.class, new ViolatedAssumptionAnswer());
        SimilarityInput<Object> nullRightInput = null;

        try {
            distance.apply(leftInput, nullRightInput);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message thrown by LevenshteinDetailedDistance: "CharSequences must not be null"
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}
