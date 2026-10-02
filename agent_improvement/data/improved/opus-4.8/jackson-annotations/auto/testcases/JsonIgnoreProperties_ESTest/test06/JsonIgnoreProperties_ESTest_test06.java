package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test06 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * A Value is never equal to null, and equals(null) must not disturb the
     * configuration flags it was constructed with.
     */
    @Test(timeout = 4000)
    public void equalsNullReturnsFalseAndPreservesFlags() throws Throwable {
        // Construct with: ignored=null, ignoreUnknown=true, allowGetters=true,
        // allowSetters=false, merge=false
        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                (Set<String>) null, true, true, false, false);

        boolean equalsNull = value.equals((Object) null);

        assertFalse("a Value must never equal null", equalsNull);

        // The constructed flags remain unchanged.
        assertTrue(value.getIgnoreUnknown());
        assertTrue(value.getAllowGetters());
        assertFalse(value.getAllowSetters());
        assertFalse(value.getMerge());
    }
}
