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
public class JaroWinklerSimilarity_ESTest_test7 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Verifies that apply() throws IllegalArgumentException when the right (second)
     * SimilarityInput argument is null, even when the left argument is a valid mock.
     */
    @Test(timeout = 4000)
    public void test_apply_throwsIllegalArgumentException_whenRightInputIsNull() throws Throwable {
        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
        SimilarityInput<Object> leftInput = (SimilarityInput<Object>) mock(SimilarityInput.class, new ViolatedAssumptionAnswer());

        try {
            similarity.apply(leftInput, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.apache.commons.text.similarity.JaroWinklerSimilarity", e);
        }
    }
}
