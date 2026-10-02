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
public class LevenshteinDetailedDistance_ESTest_test08 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        char[] inputCharacters = new char[2];
        CharBuffer identicalInput = CharBuffer.wrap(inputCharacters);
        Integer maximumThreshold = new Integer(Integer.MAX_VALUE);
        LevenshteinDetailedDistance distanceWithMaximumThreshold = new LevenshteinDetailedDistance(maximumThreshold);

        LevenshteinResults result = distanceWithMaximumThreshold.apply((CharSequence) identicalInput, (CharSequence) identicalInput);

        assertEquals(0, (int) result.getDistance());
    }
}
