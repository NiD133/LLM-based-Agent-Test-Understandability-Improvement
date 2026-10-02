package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDetailedDistance_ESTest_test03 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    /**
     * Verifies that apply() throws IllegalArgumentException when the right (second) input is null.
     * The unlimited-threshold instance delegates to unlimitedCompare(), which guards against null inputs.
     */
    @Test(timeout = 4000)
    public void test_applyThrowsIllegalArgumentException_whenRightInputIsNull() throws Throwable {
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();
        SimilarityInput<Object> leftInput = (SimilarityInput<Object>) mock(SimilarityInput.class, new ViolatedAssumptionAnswer());
        SimilarityInput<Object> rightInput = null;

        try {
            distance.apply(leftInput, rightInput);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.similarity.LevenshteinDetailedDistance", e);
        }
    }
}
