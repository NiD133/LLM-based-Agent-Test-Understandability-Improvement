package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class LevenshteinDistance_ESTest_test08 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        LevenshteinDistance levenshteinDistance0 = LevenshteinDistance.getDefaultInstance();
        char[] charArray0 = new char[5];
        CharBuffer charBuffer0 = CharBuffer.wrap(charArray0);
        Integer integer0 = levenshteinDistance0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer0);
        assertEquals(0, (int) integer0);
        LevenshteinDistance levenshteinDistance1 = new LevenshteinDistance(integer0);
        CharBuffer charBuffer1 = CharBuffer.allocate(3767);
        Integer integer1 = levenshteinDistance1.apply((CharSequence) charBuffer0, (CharSequence) charBuffer1);
        assertEquals((-1), (int) integer1);
    }
}
