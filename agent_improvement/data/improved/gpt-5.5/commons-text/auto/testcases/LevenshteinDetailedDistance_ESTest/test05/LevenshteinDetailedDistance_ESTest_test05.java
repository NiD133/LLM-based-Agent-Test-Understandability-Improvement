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

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        CharBuffer emptyBufferWithCapacityThirteen = CharBuffer.allocate(13);
        Integer threshold = new Integer(13);
        LevenshteinDetailedDistance distanceWithThreshold = new LevenshteinDetailedDistance(threshold);

        LevenshteinResults distanceResult = distanceWithThreshold.apply(
                (CharSequence) emptyBufferWithCapacityThirteen,
                (CharSequence) "E6sZn1lY$kTP*\"");

        assertEquals((-1), (int) distanceResult.getDistance());
    }
}
