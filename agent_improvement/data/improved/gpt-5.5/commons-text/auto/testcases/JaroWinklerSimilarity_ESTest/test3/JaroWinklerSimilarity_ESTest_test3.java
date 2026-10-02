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
public class JaroWinklerSimilarity_ESTest_test3 extends JaroWinklerSimilarity_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        final JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;

        try {
            similarity.apply((SimilarityInput<Object>) null, (SimilarityInput<Object>) null);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The SimilarityInput overload rejects null inputs before comparing values.
            verifyException("org.apache.commons.text.similarity.JaroWinklerSimilarity", e);
        }
    }
}
