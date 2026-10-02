package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class DamerauLevenshteinDistance_ESTest_test10 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * Verifies that comparing a CharSequence to itself returns a distance of zero,
     * even when a threshold is set. A sequence is always identical to itself,
     * so no edits are required.
     */
    @Test(timeout = 4000)
    public void test_applyWithThreshold_sameCharSequence_returnsZero() throws Throwable {
        // Threshold of 4 means distances greater than 4 would return -1
        Integer threshold = new Integer(4);
        DamerauLevenshteinDistance distanceWithThreshold = new DamerauLevenshteinDistance(threshold);

        // A CharBuffer of 4 null characters; comparing it to itself should cost 0 edits
        CharBuffer charBuffer = CharBuffer.allocate(4);
        Integer distance = distanceWithThreshold.apply((CharSequence) charBuffer, (CharSequence) charBuffer);

        assertEquals(0, (int) distance);
    }
}
