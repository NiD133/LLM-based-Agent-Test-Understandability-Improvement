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
public class LevenshteinDetailedDistance_ESTest_test20 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that applying the unlimited Levenshtein distance on a mocked
     * SimilarityInput whose length() reports negative values yields a distance of 0.
     *
     * The mock returns length values in this order across all calls:
     *   Call 1 (left.length()  in unlimitedCompare)      → -1  (n = -1)
     *   Call 2 (right.length() in unlimitedCompare)      → -1  (m = -1)
     *   Call 3 (right.length() in findDetailedResults)   → 1718 (rowIndex)
     *   Call 4 (left.length()  in findDetailedResults)   → -1  (columnIndex)
     *
     * Because n and m are both negative, neither the "empty string" early-return
     * branches nor the main DP loops execute. findDetailedResults is reached, but
     * its while-loop guard (columnIndex >= 0) is immediately false (columnIndex = -1),
     * so no edit operations are counted and the returned distance is 0.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Use unlimited mode (no threshold)
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        // Single mock used as both left and right inputs so length() calls are shared
        SimilarityInput<Object> mockedInput = (SimilarityInput<Object>) mock(SimilarityInput.class, new ViolatedAssumptionAnswer());

        // Stub four consecutive length() calls with the values that drive the specific code path
        doReturn((-1), (-1), 1718, (-1)).when(mockedInput).length();

        LevenshteinResults result = distance.apply(mockedInput, mockedInput);

        // No edit operations were counted because the DP loop was bypassed
        assertEquals(0, (int) result.getDistance());
    }
}
