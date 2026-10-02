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
public class LevenshteinDistance_ESTest_test05 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        char[] charArray0 = new char[8];
        charArray0[3] = ';';
        CharBuffer charBuffer0 = CharBuffer.wrap(charArray0);
        Integer integer0 = new Integer(13);
        LevenshteinDistance levenshteinDistance0 = new LevenshteinDistance(integer0);
        Integer integer1 = levenshteinDistance0.apply((CharSequence) charBuffer0, (CharSequence) "d0tT~H04{j;2W=");
        assertEquals((-1), (int) integer1);
    }
}
