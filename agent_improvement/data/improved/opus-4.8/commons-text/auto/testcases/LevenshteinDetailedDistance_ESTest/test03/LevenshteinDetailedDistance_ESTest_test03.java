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
public class LevenshteinDetailedDistance_ESTest_test03 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that {@link LevenshteinDetailedDistance#apply(SimilarityInput, SimilarityInput)}
     * rejects a null input by throwing an {@link IllegalArgumentException}.
     *
     * <p>The default (unlimited) instance is used, and a non-null {@code SimilarityInput}
     * is paired with a {@code null} second input. The CUT validates its arguments before
     * doing any work, so it must fail fast with the "CharSequences must not be null" message.</p>
     */
    @Test(timeout = 4000)
    public void apply_withNullRightInput_throwsIllegalArgumentException() throws Throwable {
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();
        SimilarityInput<Object> nonNullLeftInput =
                (SimilarityInput<Object>) mock(SimilarityInput.class, new ViolatedAssumptionAnswer());
        SimilarityInput<Object> nullRightInput = null;

        try {
            distance.apply(nonNullLeftInput, nullRightInput);
            fail("Expecting exception: IllegalArgumentException because the right input is null");
        } catch (IllegalArgumentException e) {
            // Expected: the CUT reports "CharSequences must not be null".
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}
