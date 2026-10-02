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
public class CharRange_ESTest_test14 extends CharRange_ESTest_scaffolding {

    /**
     * Verifies that a negated single-character range does not contain a negated multi-character range
     * when the single character ('1') falls outside the multi-character range ('J'-'T').
     *
     * Also verifies that isNotIn normalizes out-of-order arguments: isNotIn('T', 'J') stores
     * start='J' and end='T' because 'J' < 'T'.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // "everything except '1'"
        CharRange notDigitOne = CharRange.isNot('1');

        // "everything except 'J' through 'T'" — arguments are deliberately out of order to
        // exercise normalization; CharRange swaps them so start='J', end='T'
        CharRange notLettersJtoT = CharRange.isNotIn('T', 'J');

        // A negated range A contains negated range B when A.start >= B.start && A.end <= B.end.
        // '1'(49) is not >= 'J'(74), so notDigitOne does NOT contain notLettersJtoT.
        boolean notDigitOneContainsNotJtoT = notDigitOne.contains(notLettersJtoT);

        // isNotIn normalizes the order: start is the smaller of the two arguments
        assertEquals('J', notLettersJtoT.getStart());
        assertEquals('T', notLettersJtoT.getEnd());

        // isNot creates a single-character range (start == end)
        assertEquals('1', notDigitOne.getStart());
        assertEquals('1', notDigitOne.getEnd());
        assertTrue(notDigitOne.isNegated());

        // '1' falls below the 'J'-'T' range, so the containment check fails
        assertFalse(notDigitOneContainsNotJtoT);
    }
}
