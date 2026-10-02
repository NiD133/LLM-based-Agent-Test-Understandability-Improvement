package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test18 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-character range (everything except '2') should contain
     * any other character, while still reporting '2' as both its start and end.
     */
    @Test(timeout = 4000)
    public void testNegatedRangeContainsOtherCharacterAndKeepsBounds() throws Throwable {
        CharRange everythingExcept2 = CharRange.isNot('2');

        assertTrue("'~' is not '2', so the negated range should contain it",
                everythingExcept2.contains('~'));
        assertEquals("start of isNot('2') should be '2'", '2', everythingExcept2.getStart());
        assertEquals("end of isNot('2') should be '2'", '2', everythingExcept2.getEnd());
    }
}
