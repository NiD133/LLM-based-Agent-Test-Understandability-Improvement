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

    // When the edit distance between an 8-char buffer and a 14-char string exceeds the threshold of 13, apply() returns -1.
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        char[] sourceChars = new char[8];
        sourceChars[3] = ';';
        CharBuffer sourceBuffer = CharBuffer.wrap(sourceChars);

        LevenshteinDistance distanceWithThreshold = new LevenshteinDistance(13);

        Integer distance = distanceWithThreshold.apply((CharSequence) sourceBuffer, (CharSequence) "d0tT~H04{j;2W=");

        assertEquals(-1, (int) distance);
    }
}
