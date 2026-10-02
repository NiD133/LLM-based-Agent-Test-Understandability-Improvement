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
public class JaroWinklerSimilarity_ESTest_test1 extends JaroWinklerSimilarity_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;
        CharBuffer twoCharacterBuffer = CharBuffer.allocate(2);
        CharBuffer emptySliceAtEnd = CharBuffer.wrap((CharSequence) twoCharacterBuffer, 2, 2);

        Double actualSimilarity = similarity.apply((CharSequence) emptySliceAtEnd, (CharSequence) twoCharacterBuffer);

        assertEquals(0.0, (double) actualSimilarity, 0.01);
    }
}
