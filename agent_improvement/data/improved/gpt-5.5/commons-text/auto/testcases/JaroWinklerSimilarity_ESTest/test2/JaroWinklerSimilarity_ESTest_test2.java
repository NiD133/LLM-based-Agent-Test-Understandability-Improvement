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
public class JaroWinklerSimilarity_ESTest_test2 extends JaroWinklerSimilarity_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        char[] shortZeroFilledArray = new char[8];
        CharBuffer shortZeroFilledBuffer = CharBuffer.wrap(shortZeroFilledArray);
        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;
        CharBuffer longZeroFilledBuffer = CharBuffer.allocate(1772);

        Double similarityScore = similarity.apply((CharSequence) longZeroFilledBuffer, (CharSequence) shortZeroFilledBuffer);

        assertEquals(0.6681715575620767, (double) similarityScore, 0.01);
    }
}
