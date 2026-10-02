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
public class LevenshteinDetailedDistance_ESTest_test09 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    // The CharBuffer of 858 null characters is much longer than the 10-character target string.
    // With a threshold equal to the buffer length (858), the distance equals the threshold,
    // and 848 deletions are needed (858 buffer chars minus the 10 chars that can be matched or substituted).
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        int bufferCapacity = 858;
        CharBuffer nullCharBuffer = CharBuffer.allocate(bufferCapacity);

        int distanceThreshold = 858;
        LevenshteinDetailedDistance distanceWithThreshold = new LevenshteinDetailedDistance(new Integer(distanceThreshold));

        String targetString = "y{Ux1x:{|e";
        LevenshteinResults results = distanceWithThreshold.apply((CharSequence) nullCharBuffer, (CharSequence) targetString);

        assertEquals(858, (int) results.getDistance());
        assertEquals(848, (int) results.getDeleteCount());
    }
}
