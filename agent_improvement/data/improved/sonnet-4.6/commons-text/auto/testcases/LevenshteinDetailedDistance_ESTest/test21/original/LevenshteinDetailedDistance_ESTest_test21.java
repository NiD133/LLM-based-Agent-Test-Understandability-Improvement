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
public class LevenshteinDetailedDistance_ESTest_test21 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test21() throws Throwable {
        LevenshteinDetailedDistance levenshteinDetailedDistance0 = new LevenshteinDetailedDistance();
        SimilarityInput<Object> similarityInput0 = (SimilarityInput<Object>) mock(SimilarityInput.class, new ViolatedAssumptionAnswer());
        doReturn((-1), (-1), 0, 0).when(similarityInput0).length();
        LevenshteinResults levenshteinResults0 = levenshteinDetailedDistance0.apply(similarityInput0, similarityInput0);
        assertEquals(0, (int) levenshteinResults0.getDistance());
    }
}
