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
public class LevenshteinDetailedDistance_ESTest_test12 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test12() throws Throwable {
        Integer integer0 = new Integer(0);
        LevenshteinDetailedDistance levenshteinDetailedDistance0 = new LevenshteinDetailedDistance(integer0);
        CharBuffer charBuffer0 = CharBuffer.allocate(0);
        LevenshteinResults levenshteinResults0 = levenshteinDetailedDistance0.apply((CharSequence) charBuffer0, (CharSequence) charBuffer0);
        assertEquals(0, (int) levenshteinResults0.getInsertCount());
        assertEquals(0, (int) levenshteinResults0.getDeleteCount());
        assertEquals(0, (int) levenshteinResults0.getSubstituteCount());
        assertEquals(0, (int) levenshteinResults0.getDistance());
    }
}
