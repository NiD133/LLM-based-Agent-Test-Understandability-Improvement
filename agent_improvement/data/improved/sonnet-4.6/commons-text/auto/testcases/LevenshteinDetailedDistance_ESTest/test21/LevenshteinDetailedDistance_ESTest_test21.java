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
public class LevenshteinDetailedDistance_ESTest_test21 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that applying the unlimited Levenshtein distance to a mock input whose
     * length() returns -1 on the first two calls (skipping the n==0/m==0 early exits
     * in unlimitedCompare) but returns 0 on the next two calls (inside findDetailedResults)
     * causes the traceback loop to break immediately and yields a total distance of 0.
     */
    @Test(timeout = 4000)
    public void testApplyWithNegativeThenZeroLengthInputReturnsZeroDistance() throws Throwable {
        // Unlimited-mode instance (no threshold)
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        // Mock whose length() returns -1, -1, 0, 0 on successive calls.
        // The first two -1 values reach unlimitedCompare: n=-1 and m=-1 bypass the
        // n==0 / m==0 early-exit branches. The following two 0 values are read by
        // findDetailedResults as rowIndex=0 and columnIndex=0, which makes all three
        // neighbour cells sentinel (-1) on the very first iteration, triggering the
        // immediate break and returning a distance of 0.
        SimilarityInput<Object> mockInput = (SimilarityInput<Object>) mock(SimilarityInput.class, new ViolatedAssumptionAnswer());
        doReturn((-1), (-1), 0, 0).when(mockInput).length();

        LevenshteinResults result = distance.apply(mockInput, mockInput);

        assertEquals(0, (int) result.getDistance());
    }
}
