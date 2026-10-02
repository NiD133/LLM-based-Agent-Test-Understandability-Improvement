package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test15 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-char range (everything except '1') must contain
     * a single-char range whose character ('J') is outside the excluded set.
     * After the containment check, both ranges' start and end boundaries
     * are verified to confirm neither range was mutated.
     */
    @Test(timeout = 4000)
    public void test_negatedRangeContainsSingleCharOutsideExclusion() throws Throwable {
        // "everything except '1'"
        CharRange everythingExcept1 = CharRange.isNot('1');
        // a range containing only 'J'
        CharRange onlyJ = CharRange.is('J');

        // 'J' is not '1', so the negated range must contain the 'J' range
        boolean contained = everythingExcept1.contains(onlyJ);
        assertTrue(contained);

        // Both ranges are immutable single-char ranges, so start == end for each
        assertEquals('1', everythingExcept1.getStart());
        assertEquals('1', everythingExcept1.getEnd());
        assertEquals('J', onlyJ.getStart());
        assertEquals('J', onlyJ.getEnd());
    }
}
