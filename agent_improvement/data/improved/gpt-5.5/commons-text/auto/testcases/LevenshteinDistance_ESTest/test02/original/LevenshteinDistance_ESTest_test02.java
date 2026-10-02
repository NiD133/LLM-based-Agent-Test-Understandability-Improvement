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
public class LevenshteinDistance_ESTest_test02 extends LevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        CharBuffer charBuffer0 = CharBuffer.allocate(305);
        LevenshteinDistance levenshteinDistance0 = new LevenshteinDistance();
        CharBuffer charBuffer1 = CharBuffer.wrap((CharSequence) charBuffer0, 305, 305);
        Integer integer0 = levenshteinDistance0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer1);
        assertEquals(305, (int) integer0);
    }
}
