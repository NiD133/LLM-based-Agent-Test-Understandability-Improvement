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
public class LevenshteinDetailedDistance_ESTest_test01 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        CharBuffer sourceBuffer = CharBuffer.allocate(26);
        LevenshteinDetailedDistance distance = LevenshteinDetailedDistance.getDefaultInstance();
        CharBuffer emptyTargetAtBufferEnd = CharBuffer.wrap((CharSequence) sourceBuffer, 26, 26);

        LevenshteinResults results = distance.apply((CharSequence) sourceBuffer, (CharSequence) emptyTargetAtBufferEnd);

        assertEquals(0, (int) results.getSubstituteCount());
        assertEquals(0, (int) results.getInsertCount());
        assertEquals(26, (int) results.getDistance());
        assertEquals(26, (int) results.getDeleteCount());
    }
}
