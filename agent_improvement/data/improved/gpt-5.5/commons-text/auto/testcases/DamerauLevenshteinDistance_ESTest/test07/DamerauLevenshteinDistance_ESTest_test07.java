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
public class DamerauLevenshteinDistance_ESTest_test07 extends DamerauLevenshteinDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Integer threshold = new Integer(582);
        DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance(threshold);

        CharBuffer sourceBuffer = CharBuffer.allocate(582);
        CharBuffer emptySliceAtEnd = CharBuffer.wrap((CharSequence) sourceBuffer, 582, 582);

        Integer actualDistance = distance.apply((CharSequence) sourceBuffer, (CharSequence) emptySliceAtEnd);

        assertEquals(582, (int) actualDistance);
    }
}
