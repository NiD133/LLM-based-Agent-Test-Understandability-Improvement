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
public class JaroWinklerSimilarity_ESTest_test5 extends JaroWinklerSimilarity_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test5() throws Throwable {
        final char[] sharedCharacters = new char[12];
        final CharBuffer advancedBuffer = CharBuffer.wrap(sharedCharacters);
        advancedBuffer.put("X19l5(mgJ");

        final CharBuffer fullBufferView = CharBuffer.wrap(sharedCharacters);

        final int[] matches = JaroWinklerSimilarity.matches(fullBufferView, advancedBuffer);

        assertArrayEquals(new int[] { 0, 0, 0 }, matches);
    }
}
