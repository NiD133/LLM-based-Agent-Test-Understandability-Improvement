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

    private static final int BUFFER_CAPACITY_AND_THRESHOLD = 858;
    private static final String TARGET_TEXT = "y{Ux1x:{|e";
    private static final int EXPECTED_DISTANCE = 858;
    private static final int EXPECTED_DELETE_COUNT = 848;

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        CharBuffer sourceBuffer = CharBuffer.allocate(BUFFER_CAPACITY_AND_THRESHOLD);
        Integer threshold = new Integer(BUFFER_CAPACITY_AND_THRESHOLD);
        LevenshteinDetailedDistance distanceCalculator = new LevenshteinDetailedDistance(threshold);

        LevenshteinResults result = distanceCalculator.apply((CharSequence) sourceBuffer, (CharSequence) TARGET_TEXT);

        assertEquals(EXPECTED_DISTANCE, (int) result.getDistance());
        assertEquals(EXPECTED_DELETE_COUNT, (int) result.getDeleteCount());
    }
}
