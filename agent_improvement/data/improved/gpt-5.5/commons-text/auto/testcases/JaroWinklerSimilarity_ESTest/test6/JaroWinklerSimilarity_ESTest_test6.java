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
public class JaroWinklerSimilarity_ESTest_test6 extends JaroWinklerSimilarity_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;
        CharBuffer identicalBuffer = CharBuffer.allocate(2);

        Double score = similarity.apply((CharSequence) identicalBuffer, (CharSequence) identicalBuffer);

        assertEquals("A sequence compared with itself should have full similarity", 1.0, (double) score, 0.01);
    }
}
