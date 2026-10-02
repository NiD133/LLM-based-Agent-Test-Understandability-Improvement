package org.apache.commons.text.similarity;

import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import static org.evosuite.shaded.org.mockito.Mockito.mock;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test7 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Verifies that {@link JaroWinklerSimilarity#apply(SimilarityInput, SimilarityInput)}
     * rejects a {@code null} second input by throwing an {@link IllegalArgumentException}
     * with the message "CharSequences must not be null".
     */
    @Test(timeout = 4000)
    public void applyWithNullSecondInputThrowsIllegalArgumentException() throws Throwable {
        JaroWinklerSimilarity similarity = new JaroWinklerSimilarity();
        SimilarityInput<Object> nonNullInput =
                mock(SimilarityInput.class, new ViolatedAssumptionAnswer());

        try {
            similarity.apply(nonNullInput, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Message thrown: "CharSequences must not be null"
            verifyException("org.apache.commons.text.similarity.JaroWinklerSimilarity", e);
        }
    }
}
