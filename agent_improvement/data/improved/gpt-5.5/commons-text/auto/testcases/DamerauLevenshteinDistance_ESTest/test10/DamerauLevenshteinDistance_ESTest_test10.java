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
public class DamerauLevenshteinDistance_ESTest_test10 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Integer threshold = new Integer(4);
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(threshold);
        CharBuffer sharedBuffer = CharBuffer.allocate(4);

        Integer actualDistance = distance.apply((CharSequence) sharedBuffer, (CharSequence) sharedBuffer);

        assertEquals(0, (int) actualDistance);
    }
}
