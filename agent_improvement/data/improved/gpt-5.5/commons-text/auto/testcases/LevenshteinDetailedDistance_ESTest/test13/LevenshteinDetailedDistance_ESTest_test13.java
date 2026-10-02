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
public class LevenshteinDetailedDistance_ESTest_test13 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Integer zeroThreshold = new Integer(0);
        LevenshteinDetailedDistance distanceWithZeroThreshold = new LevenshteinDetailedDistance(zeroThreshold);

        CharBuffer targetWithCharactersBeyondThreshold = CharBuffer.allocate(1839);
        CharBuffer emptySource = CharBuffer.allocate(0);

        LevenshteinResults results = distanceWithZeroThreshold.apply(
                (CharSequence) emptySource,
                (CharSequence) targetWithCharactersBeyondThreshold);

        assertEquals((-1), (int) results.getDistance());
        assertEquals(0, (int) results.getSubstituteCount());
        assertEquals(0, (int) results.getInsertCount());
        assertEquals(0, (int) results.getDeleteCount());
    }
}
