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
public class JaroWinklerSimilarity_ESTest_test4 extends JaroWinklerSimilarity_ESTest_scaffolding {

    private static final int LONGER_SEQUENCE_LENGTH = 8;
    private static final int SHORTER_SEQUENCE_LENGTH = 6;
    private static final int MATCH_INDEX_IN_LONGER_SEQUENCE = 1;
    private static final int MATCH_INDEX_IN_SHORTER_SEQUENCE = 3;
    private static final char MATCHING_CHARACTER = 'f';

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        final char[] longerSequence = new char[LONGER_SEQUENCE_LENGTH];
        longerSequence[MATCH_INDEX_IN_LONGER_SEQUENCE] = MATCHING_CHARACTER;
        final CharBuffer longerBuffer = CharBuffer.wrap(longerSequence);

        final char[] shorterSequence = new char[SHORTER_SEQUENCE_LENGTH];
        shorterSequence[MATCH_INDEX_IN_SHORTER_SEQUENCE] = MATCHING_CHARACTER;
        final CharBuffer shorterBuffer = CharBuffer.wrap(shorterSequence);

        final int[] matchesHalfTranspositionsAndPrefix = JaroWinklerSimilarity.matches(shorterBuffer, longerBuffer);

        assertArrayEquals(new int[] { 6, 2, 1 }, matchesHalfTranspositionsAndPrefix);
    }
}
