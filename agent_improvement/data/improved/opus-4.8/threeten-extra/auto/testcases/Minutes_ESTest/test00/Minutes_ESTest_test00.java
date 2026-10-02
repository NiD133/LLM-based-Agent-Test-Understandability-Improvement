package org.threeten.extra;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Minutes_ESTest_test00 extends Minutes_ESTest_scaffolding {

    /**
     * Subtracting then adding the same number of minutes returns to the
     * original amount, and the intermediate (reduced) amount is distinct.
     */
    @Test(timeout = 4000)
    public void subtractThenAddSameAmountRestoresOriginal() throws Throwable {
        // 327 hours == 327 * 60 == 19620 minutes.
        Minutes original = Minutes.ofHours(327);
        assertEquals(19620, original.getAmount());

        // Subtract 327 minutes to get an intermediate, reduced amount.
        Minutes reduced = original.minus(327);

        // Add the same 327 minutes back, which should restore the original amount.
        Minutes restored = reduced.plus(327);

        // The restored amount equals the original, but the reduced one does not.
        assertEquals(19620, restored.getAmount());
        assertTrue(restored.equals(original));
        assertFalse(restored.equals((Object) reduced));
        assertFalse(reduced.equals((Object) original));
    }
}
