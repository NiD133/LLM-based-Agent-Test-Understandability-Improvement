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
public class LevenshteinDetailedDistance_ESTest_test07 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When the threshold is too small to bridge the gap in length between the two
     * sequences, the limited algorithm cannot guarantee a correct distance and
     * therefore reports -1 ("distance exceeds threshold").
     *
     * Here the left sequence has length 1 and the right sequence has length 10.
     * Turning one into the other requires far more than the configured threshold
     * of 1 edit, so the distance is reported as -1.
     */
    @Test(timeout = 4000)
    public void distanceIsMinusOneWhenLengthGapExceedsThreshold() throws Throwable {
        final int threshold = 1;
        LevenshteinDetailedDistance limitedDistance = new LevenshteinDetailedDistance(threshold);

        CharSequence shortSequence = CharBuffer.allocate(1);   // single-character sequence
        CharSequence longSequence = "y{Ux1x:{|e";              // ten-character sequence

        LevenshteinResults results = limitedDistance.apply(shortSequence, longSequence);

        assertEquals(-1, (int) results.getDistance());
    }
}
