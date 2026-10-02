package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test18 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_negatedSingleCharRange_containsCharOutsideExcludedChar() throws Throwable {
        // Create a negated range excluding only '2' (matches every char except '2')
        CharRange rangeExcluding2 = CharRange.isNot('2');

        // '~' is not '2', so it should be contained in the negated range
        boolean containsTilde = rangeExcluding2.contains('~');
        assertTrue(containsTilde);

        // The negated range was built from a single character, so start == end == '2'
        assertEquals('2', rangeExcluding2.getStart());
        assertEquals('2', rangeExcluding2.getEnd());
    }
}
