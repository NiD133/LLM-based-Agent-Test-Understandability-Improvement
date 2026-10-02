package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test12 extends CharRange_ESTest_scaffolding {

    /**
     * A negated range covering 'T'..'￿' represents every character EXCEPT
     * that span. Since 'J' falls outside 'T'..'￿', the single-character
     * range {'J'} is entirely contained within the negated range.
     */
    @Test(timeout = 4000)
    public void test12() throws Throwable {
        CharRange allExceptTtoMax = CharRange.isNotIn('T', '￿');
        CharRange singleJ = CharRange.is('J');

        boolean negatedRangeContainsJ = allExceptTtoMax.contains(singleJ);

        assertTrue(negatedRangeContainsJ);

        // The negated range keeps its original endpoints 'T'..'￿'.
        assertEquals('T', allExceptTtoMax.getStart());
        assertEquals('￿', allExceptTtoMax.getEnd());

        // The single-character range spans only 'J'.
        assertEquals('J', singleJ.getStart());
        assertEquals('J', singleJ.getEnd());
    }
}
