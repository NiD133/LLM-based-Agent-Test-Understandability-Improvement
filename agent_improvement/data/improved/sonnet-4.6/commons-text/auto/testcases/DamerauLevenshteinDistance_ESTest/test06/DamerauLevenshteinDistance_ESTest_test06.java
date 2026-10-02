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
public class DamerauLevenshteinDistance_ESTest_test06 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    private static final int THRESHOLD = 15;
    private static final int SHORT_SEQUENCE_LENGTH = 15;
    private static final int LONG_SEQUENCE_LENGTH = 332;

    /**
     * When the length difference between inputs (332 - 15 = 317) exceeds the threshold (15),
     * the algorithm short-circuits and returns -1 instead of computing the full distance.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        CharBuffer shortSequence = CharBuffer.allocate(SHORT_SEQUENCE_LENGTH);
        CharBuffer longSequence = CharBuffer.allocate(LONG_SEQUENCE_LENGTH);

        DamerauLevenshteinDistance distanceWithThreshold = new DamerauLevenshteinDistance(THRESHOLD);

        Integer result = distanceWithThreshold.apply((CharSequence) shortSequence, (CharSequence) longSequence);

        assertEquals(-1, (int) result);
    }
}
