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
public class DamerauLevenshteinDistance_ESTest_test02 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        CharBuffer charBuffer0 = CharBuffer.allocate(116);
        DamerauLevenshteinDistance damerauLevenshteinDistance0 = new DamerauLevenshteinDistance();
        CharBuffer charBuffer1 = CharBuffer.wrap((CharSequence) charBuffer0, 116, 116);
        Integer integer0 = damerauLevenshteinDistance0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer1);
        assertEquals(116, (int) integer0);
    }
}
