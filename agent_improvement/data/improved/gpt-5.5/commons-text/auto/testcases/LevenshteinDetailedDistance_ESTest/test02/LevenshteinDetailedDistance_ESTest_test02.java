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
public class LevenshteinDetailedDistance_ESTest_test02 extends LevenshteinDetailedDistance_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        final LevenshteinDetailedDistance distance = new LevenshteinDetailedDistance();

        // Comparing two empty sequences requires no edit operations.
        final LevenshteinResults results = distance.apply((CharSequence) "", (CharSequence) "");

        assertEquals(0, (int) results.getInsertCount());
        assertEquals(0, (int) results.getSubstituteCount());
        assertEquals(0, (int) results.getDeleteCount());
    }
}
