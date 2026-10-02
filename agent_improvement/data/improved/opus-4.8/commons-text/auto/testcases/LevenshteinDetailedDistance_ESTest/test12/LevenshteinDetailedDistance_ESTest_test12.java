package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test12 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * When the two input sequences are identical (here, two empty sequences),
     * the distance is zero and no insert, delete, or substitute operations are needed.
     * A threshold of 0 is used, which still permits a zero-cost match.
     */
    @Test(timeout = 4000)
    public void applyToTwoEmptySequencesReturnsZeroForAllCounts() throws Throwable {
        Integer threshold = 0;
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(threshold);

        CharBuffer emptySequence = CharBuffer.allocate(0);
        LevenshteinResults results = distance.apply((CharSequence) emptySequence, (CharSequence) emptySequence);

        assertEquals(0, (int) results.getInsertCount());
        assertEquals(0, (int) results.getDeleteCount());
        assertEquals(0, (int) results.getSubstituteCount());
        assertEquals(0, (int) results.getDistance());
    }
}
