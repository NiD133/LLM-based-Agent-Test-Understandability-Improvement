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
    public void test_applyWithSameCharBufferReturnsPerfectSimilarity() throws Throwable {
        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;
        // A CharBuffer of capacity 2 holds two null characters ('\0', '\0')
        CharBuffer twoNullChars = CharBuffer.allocate(2);

        // Comparing a sequence with itself must yield 1.0 (perfect similarity)
        Double result = similarity.apply((CharSequence) twoNullChars, (CharSequence) twoNullChars);

        assertEquals(1.0, (double) result, 0.01);
    }
}
