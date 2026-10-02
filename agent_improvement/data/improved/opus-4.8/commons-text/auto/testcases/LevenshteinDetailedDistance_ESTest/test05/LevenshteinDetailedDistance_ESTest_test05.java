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
public class LevenshteinDetailedDistance_ESTest_test05 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When the Levenshtein distance between the two inputs cannot be guaranteed
     * to be within the configured threshold, {@code apply} reports a distance of -1.
     *
     * Here the threshold is 13, the left input is a 13-character buffer and the
     * right input is a 14-character string. Because the inputs differ in length by
     * more than the algorithm's bounded stripe can absorb at this threshold, the
     * detailed result carries the "distance unknown" sentinel value of -1.
     */
    @Test(timeout = 4000)
    public void distanceExceedingThresholdReportsMinusOne() throws Throwable {
        int threshold = 13;
        CharBuffer leftInput = CharBuffer.allocate(13);          // 13 characters
        String rightInput = "E6sZn1lY$kTP*\"";                   // 14 characters
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(threshold);

        LevenshteinResults results = distance.apply((CharSequence) leftInput, (CharSequence) rightInput);

        assertEquals(-1, (int) results.getDistance());
    }
}
