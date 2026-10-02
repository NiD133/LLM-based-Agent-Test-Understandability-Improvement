package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test23 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that changing the "id visible" flag produces a Value that is
     * NOT equal to the original, and that the inequality holds in both
     * directions (symmetry of equals).
     */
    @Test(timeout = 4000)
    public void withIdVisible_changesEquality() throws Throwable {
        JsonTypeInfo.Value original = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value withVisibleId = original.withIdVisible(true);

        // The two values differ only in the idVisible flag, so they must not be equal.
        assertFalse("original should not equal value with idVisible=true",
                original.equals(withVisibleId));
        assertFalse("value with idVisible=true should not equal original",
                withVisibleId.equals((Object) original));
    }
}
