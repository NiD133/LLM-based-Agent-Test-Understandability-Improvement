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
public class DamerauLevenshteinDistance_ESTest_test05 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    /**
     * When the threshold is 0, any two single-character strings that differ must return -1
     * because the edit distance (1 substitution) exceeds the allowed threshold.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Arrange: distance calculator with a strict threshold of 0
        Integer threshold = new Integer(0);
        DamerauLevenshteinDistance distanceWithZeroThreshold = new DamerauLevenshteinDistance(threshold);

        // A CharBuffer of capacity 1 contains the null character '\0' by default
        CharBuffer nullCharBuffer = CharBuffer.allocate(1);

        // A CharBuffer wrapping a single '%' character
        char[] percentCharArray = new char[1];
        percentCharArray[0] = '%';
        CharBuffer percentCharBuffer = CharBuffer.wrap(percentCharArray);

        // Act: compute distance between '\0' and '%' — they differ, so distance is 1
        Integer distance = distanceWithZeroThreshold.apply((CharSequence) nullCharBuffer, (CharSequence) percentCharBuffer);

        // Assert: distance (1) exceeds threshold (0), so the result must be -1
        assertEquals((-1), (int) distance);
    }
}
