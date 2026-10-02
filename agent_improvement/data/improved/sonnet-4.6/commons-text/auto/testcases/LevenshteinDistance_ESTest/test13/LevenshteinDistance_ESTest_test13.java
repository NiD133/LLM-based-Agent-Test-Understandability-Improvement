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
public class LevenshteinDistance_ESTest_test13 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Threshold 0 means only identical strings are within the edit-distance limit.
        LevenshteinDistance levenshteinDistance0 = new LevenshteinDistance(new Integer(0));

        // An empty CharBuffer (length 0) compared to an 18-character string has
        // edit distance 18, which exceeds the threshold of 0, so apply() returns -1.
        CharBuffer charBuffer0 = CharBuffer.allocate(0);
        Integer integer1 = levenshteinDistance0.apply((CharSequence) charBuffer0, (CharSequence) "&D/r2c/;WL~$DxE.m");

        assertEquals(-1, (int) integer1);
    }
}
