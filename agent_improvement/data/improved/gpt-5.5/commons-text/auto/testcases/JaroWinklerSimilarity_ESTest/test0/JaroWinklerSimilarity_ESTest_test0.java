package org.apache.commons.text.similarity;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JaroWinklerSimilarity_ESTest_test0 extends JaroWinklerSimilarity_ESTest_scaffolding {

    private static final double EXPECTED_SIMILARITY = 0.8222222222222222;
    private static final double ASSERTION_DELTA = 0.01;

    @Test(timeout = 4000)
    public void testSimilarityBetweenEmptyAllocatedBufferAndZeroFilledBuffer() throws Throwable {
        char[] zeroFilledCharacters = new char[6];
        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;
        CharBuffer allocatedBuffer = CharBuffer.allocate(2);
        CharBuffer wrappedZeroFilledBuffer = CharBuffer.wrap(zeroFilledCharacters);

        Double actualSimilarity = similarity.apply((CharSequence) allocatedBuffer, (CharSequence) wrappedZeroFilledBuffer);

        assertEquals(EXPECTED_SIMILARITY, (double) actualSimilarity, ASSERTION_DELTA);
    }
}
