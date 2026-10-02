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
public class LevenshteinDetailedDistance_ESTest_test20 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        LevenshteinDetailedDistance distanceCalculator = new LevenshteinDetailedDistance();
        SimilarityInput<Object> mockedInput = (SimilarityInput<Object>) mock(SimilarityInput.class, new ViolatedAssumptionAnswer());

        // These length values are consumed by the algorithm while comparing the same mocked input to itself.
        doReturn((-1), (-1), 1718, (-1)).when(mockedInput).length();

        LevenshteinResults result = distanceCalculator.apply(mockedInput, mockedInput);

        assertEquals(0, (int) result.getDistance());
    }
}
