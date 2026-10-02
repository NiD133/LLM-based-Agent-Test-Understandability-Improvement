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
public class JaroWinklerSimilarity_ESTest_test1 extends JaroWinklerSimilarity_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_similarityBetweenEmptyAndNonEmptySequenceIsZero() throws Throwable {
        JaroWinklerSimilarity similarity = JaroWinklerSimilarity.INSTANCE;

        // A 2-char buffer (filled with null chars '\0\0')
        CharBuffer twoCharBuffer = CharBuffer.allocate(2);

        // Wrapping at position 2..2 yields an empty subsequence
        CharBuffer emptySubsequence = CharBuffer.wrap((CharSequence) twoCharBuffer, 2, 2);

        // When one input is empty, Jaro-Winkler returns 0.0 (no matching characters)
        Double result = similarity.apply((CharSequence) emptySubsequence, (CharSequence) twoCharBuffer);

        assertEquals(0.0, (double) result, 0.01);
    }
}
