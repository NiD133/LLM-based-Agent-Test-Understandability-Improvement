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
public class LevenshteinDetailedDistance_ESTest_test16 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        char[] charArray0 = new char[7];
        charArray0[3] = 'S';
        char[] charArray1 = new char[8];
        charArray1[0] = 'S';
        CharBuffer charBuffer0 = CharBuffer.wrap(charArray1);
        CharBuffer charBuffer1 = CharBuffer.wrap(charArray0);
        LevenshteinDetailedDistance levenshteinDetailedDistance0 = new LevenshteinDetailedDistance();
        LevenshteinResults levenshteinResults0 = levenshteinDetailedDistance0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer1);
        assertEquals(2, (int) levenshteinResults0.getDistance());
    }
}
