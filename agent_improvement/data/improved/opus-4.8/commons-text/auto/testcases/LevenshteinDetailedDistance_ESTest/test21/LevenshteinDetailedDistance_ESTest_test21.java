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
public class LevenshteinDetailedDistance_ESTest_test21 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Applies the unlimited (no-threshold) distance algorithm to a SimilarityInput
     * whose reported length changes on each call.
     *
     * The mock's length() is stubbed to return the sequence (-1, -1, 0, 0):
     *   - the first two calls (one per input) skip the "empty input" shortcuts in
     *     unlimitedCompare, since neither length is 0, and produce empty cost arrays;
     *   - the last two calls happen inside findDetailedResults, where both lengths
     *     report 0, so the back-tracking loop terminates immediately.
     *
     * The net effect is a LevenshteinResults with a distance of 0.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        @SuppressWarnings("unchecked")
        SimilarityInput<Object> input = (SimilarityInput<Object>) mock(SimilarityInput.class, new ViolatedAssumptionAnswer());
        doReturn(-1, -1, 0, 0).when(input).length();

        LevenshteinResults results = distance.apply(input, input);

        assertEquals(0, (int) results.getDistance());
    }
}
