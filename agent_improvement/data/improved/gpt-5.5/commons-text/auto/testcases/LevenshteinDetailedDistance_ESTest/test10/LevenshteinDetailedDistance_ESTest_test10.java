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
public class LevenshteinDetailedDistance_ESTest_test10 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    private static final int BUFFER_LENGTH = 1470;

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Integer threshold = new Integer(BUFFER_LENGTH);
        LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance(threshold);

        CharBuffer fullBuffer = CharBuffer.allocate(BUFFER_LENGTH);
        CharBuffer emptySliceAtEnd = CharBuffer.wrap((CharSequence) fullBuffer, BUFFER_LENGTH, BUFFER_LENGTH);

        LevenshteinResults results = distance.apply((CharSequence) fullBuffer, (CharSequence) emptySliceAtEnd);

        assertDeletionOnlyDistance(results, BUFFER_LENGTH);
    }

    private void assertDeletionOnlyDistance(LevenshteinResults results, int deletedCharacters) {
        assertEquals(deletedCharacters, (int) results.getDistance());
        assertEquals(0, (int) results.getInsertCount());
        assertEquals(deletedCharacters, (int) results.getDeleteCount());
        assertEquals(0, (int) results.getSubstituteCount());
    }
}
