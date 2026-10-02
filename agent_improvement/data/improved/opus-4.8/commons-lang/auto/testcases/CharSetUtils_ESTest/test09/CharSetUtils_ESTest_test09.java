package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test09 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link CharSetUtils#delete(String, String...)} returns the
     * input string unchanged when none of its characters appear in the delete set.
     *
     * <p>The set "Iyc@#m)6#tX" shares no characters with "YhY[" (note the casing:
     * 'Y' and '[' are not in the set), so the original string is returned as-is.</p>
     */
    @Test(timeout = 4000)
    public void test09() throws Throwable {
        // The delete set: a single non-empty entry, the rest of the array left null.
        String[] deleteSet = new String[9];
        deleteSet[7] = "Iyc@#m)6#tX";

        String result = CharSetUtils.delete("YhY[", deleteSet);

        // No characters of "YhY[" occur in the set, so nothing is deleted.
        assertEquals("YhY[", result);
    }
}
