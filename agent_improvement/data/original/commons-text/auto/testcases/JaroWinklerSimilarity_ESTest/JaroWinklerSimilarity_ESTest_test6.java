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
        JaroWinklerSimilarity jaroWinklerSimilarity0 = JaroWinklerSimilarity.INSTANCE;
        CharBuffer charBuffer0 = CharBuffer.allocate(2);
        Double double0 = jaroWinklerSimilarity0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer0);
        assertEquals(1.0, (double) double0, 0.01);
    }
}
