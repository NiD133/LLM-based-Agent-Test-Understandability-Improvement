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
public class LevenshteinDetailedDistance_ESTest_test19 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        char[] charArray0 = new char[7];
        char[] charArray1 = new char[8];
        charArray1[2] = 'f';
        charArray0[3] = 'f';
        charArray1[6] = 'f';
        charArray1[7] = 'f';
        CharBuffer charBuffer0 = CharBuffer.wrap(charArray1);
        Integer integer0 = new Integer(7);
        LevenshteinDetailedDistance levenshteinDetailedDistance0 = new LevenshteinDetailedDistance(integer0);
        CharBuffer charBuffer1 = CharBuffer.wrap(charArray0);
        LevenshteinResults levenshteinResults0 = levenshteinDetailedDistance0.apply((CharSequence) charBuffer1, (CharSequence) charBuffer0);
        assertEquals(3, (int) levenshteinResults0.getDistance());
    }
}
