package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test3 extends JaroWinklerSimilarity_ESTest_scaffolding {

    /**
     * Verifies that applying the similarity score to two null inputs is rejected:
     * the algorithm cannot compare missing character sequences, so it must throw
     * an IllegalArgumentException rather than return a score.
     */
    @Test(timeout = 4000)
    public void applyWithBothInputsNullThrowsIllegalArgumentException() throws Throwable {
        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;
        SimilarityInput<Object> nullLeft = null;
        SimilarityInput<Object> nullRight = null;

        try {
            similarity.apply(nullLeft, nullRight);
            fail("Expected an IllegalArgumentException because both inputs are null");
        } catch (IllegalArgumentException e) {
            // The CUT rejects null inputs with the message "CharSequences must not be null".
            verifyException("org.apache.commons.text.similarity.JaroWinklerSimilarity", e);
        }
    }
}
