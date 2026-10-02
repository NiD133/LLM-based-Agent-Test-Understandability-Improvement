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
public class LevenshteinDetailedDistance_ESTest_test10 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        // Use a threshold equal to the source length so the distance is within the limit
        int threshold = 1470;
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(threshold);

        // sourceBuffer: 1470 null characters; emptyTarget: a zero-length slice (start == end == 1470)
        CharBuffer sourceBuffer = CharBuffer.allocate(1470);
        CharBuffer emptyTarget = CharBuffer.wrap((CharSequence) sourceBuffer, 1470, 1470);

        // Converting a 1470-char sequence to an empty sequence requires deleting every character
        LevenshteinResults results = distance.apply((CharSequence) sourceBuffer, (CharSequence) emptyTarget);

        assertEquals(1470, (int) results.getDistance());
        assertEquals(0,    (int) results.getInsertCount());
        assertEquals(1470, (int) results.getDeleteCount());
        assertEquals(0,    (int) results.getSubstituteCount());
    }
}
