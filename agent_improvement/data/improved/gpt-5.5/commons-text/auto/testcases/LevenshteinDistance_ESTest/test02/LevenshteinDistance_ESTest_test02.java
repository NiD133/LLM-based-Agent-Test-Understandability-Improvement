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

    private static final int BUFFER_LENGTH = 305;

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        CharBuffer fullBuffer = CharBuffer.allocate(BUFFER_LENGTH);
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        CharBuffer emptySliceAtBufferEnd = CharBuffer.wrap((CharSequence) fullBuffer, BUFFER_LENGTH, BUFFER_LENGTH);

        Integer distanceFromFullBufferToEmptySlice = levenshteinDistance.apply(
                (CharSequence) fullBuffer,
                (CharSequence) emptySliceAtBufferEnd);

        assertEquals(BUFFER_LENGTH, (int) distanceFromFullBufferToEmptySlice);
    }
}
