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
        char[] sharedBackingArray = new char[12];

        CharBuffer writtenBuffer = CharBuffer.wrap(sharedBackingArray);
        writtenBuffer.put("X19l5(mgJ");

        CharBuffer unreadBuffer = CharBuffer.wrap(sharedBackingArray);
        int[] matchCounts = JaroWinklerSimilarity.matches(unreadBuffer, writtenBuffer);

        assertArrayEquals(new int[] { 0, 0, 0 }, matchCounts);
    }
}
