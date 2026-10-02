package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test20 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Exercises {@link LevenshteinDetailedDistance#apply(SimilarityInput, SimilarityInput)}
     * with a mocked {@link SimilarityInput} whose reported length is negative.
     *
     * <p>The default (no-threshold) instance runs the unbounded algorithm. With a reported
     * length below zero, none of the matrix-filling loops ever execute, and the back-tracking
     * pass in {@code findDetailedResults} stops immediately because its column index starts
     * negative. The resulting edit distance is therefore zero.</p>
     */
    @Test(timeout = 4000)
    public void apply_whenInputLengthIsNegative_returnsZeroDistance() throws Throwable {
        // Default instance uses the unlimited (no-threshold) variant of the algorithm.
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        // Mock an input; ViolatedAssumptionAnswer makes any unstubbed call abort the test.
        @SuppressWarnings("unchecked")
        SimilarityInput<Object> input = (SimilarityInput<Object>) mock(SimilarityInput.class, new ViolatedAssumptionAnswer());

        // length() is queried several times during apply(); feed the exact sequence the
        // algorithm consumes: negative lengths short-circuit every loop in the computation.
        doReturn(-1, -1, 1718, -1).when(input).length();

        // Compare the input against itself.
        LevenshteinResults results = distance.apply(input, input);

        assertEquals(0, (int) results.getDistance());
    }
}
