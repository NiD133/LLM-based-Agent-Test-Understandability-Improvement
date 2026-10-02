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
        char[] sourceCharacters = new char[8];
        sourceCharacters[3] = ';';
        CharBuffer source = CharBuffer.wrap(sourceCharacters);

        Integer threshold = new Integer(13);
        LevenshteinDistance distance = new LevenshteinDistance(threshold);

        Integer result = distance.apply((CharSequence) source, (CharSequence) "d0tT~H04{j;2W=");

        assertEquals((-1), (int) result);
    }
}
