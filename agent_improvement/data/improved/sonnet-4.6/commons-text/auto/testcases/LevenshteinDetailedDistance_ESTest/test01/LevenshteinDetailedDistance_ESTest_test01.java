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
public class LevenshteinDetailedDistance_ESTest_test01 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing a 26-character buffer (all null chars) against an empty
     * sub-sequence of that same buffer produces a distance of 26 with 26 deletions and
     * no insertions or substitutions.
     */
    @Test(timeout = 4000)
    public void test01_distanceFromNonEmptyBufferToEmptySubsequenceEqualsSourceLength() throws Throwable {
        // A CharBuffer of capacity 26, filled with null characters ('\0')
        CharBuffer sourceBuffer = CharBuffer.allocate(26);

        LevenshteinDetailedDistance calculator = LevenshteinDetailedDistance.getDefaultInstance();

        // Wrapping sourceBuffer from position 26 to 26 produces a zero-length CharSequence
        CharBuffer emptySubSequence = CharBuffer.wrap((CharSequence) sourceBuffer, 26, 26);

        // Comparing 26-char left against empty right: every char in left must be deleted
        LevenshteinResults results = calculator.apply((CharSequence) sourceBuffer, (CharSequence) emptySubSequence);

        assertEquals(0,  (int) results.getSubstituteCount());
        assertEquals(0,  (int) results.getInsertCount());
        assertEquals(26, (int) results.getDistance());
        assertEquals(26, (int) results.getDeleteCount());
    }
}
