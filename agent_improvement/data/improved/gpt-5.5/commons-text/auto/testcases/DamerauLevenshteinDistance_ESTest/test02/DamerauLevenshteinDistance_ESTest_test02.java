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
        final CharBuffer fullBuffer = CharBuffer.allocate(116);
        final DamerauLevenshteinDistance distance = new DamerauLevenshteinDistance();
        final CharBuffer emptySliceAtBufferEnd = CharBuffer.wrap((CharSequence) fullBuffer, 116, 116);

        final Integer actualDistance = distance.apply((CharSequence) fullBuffer, (CharSequence) emptySliceAtBufferEnd);

        assertEquals(116, (int) actualDistance);
    }
}
