package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test27 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that a Value whose inclusionType is As.NOTHING is not considered
     * "enabled" for polymorphic handling, even when its id type is not NONE.
     *
     * EMPTY starts with Id.NONE and As.NOTHING. After applying MINIMAL_CLASS as
     * the id type, isEnabled() still returns false because isEnabled() also
     * requires inclusionType != As.NOTHING, and that remains unchanged from EMPTY.
     */
    @Test(timeout = 4000)
    public void test27() throws Throwable {
        // EMPTY has Id.NONE and As.NOTHING; withIdType produces a new Value
        // with Id.MINIMAL_CLASS but keeps As.NOTHING from EMPTY
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value valueWithMinimalClass = emptyValue.withIdType(JsonTypeInfo.Id.MINIMAL_CLASS);

        // isEnabled requires both idType != NONE and inclusionType != NOTHING;
        // since inclusionType is still As.NOTHING (inherited from EMPTY), this returns false
        boolean enabled = JsonTypeInfo.Value.isEnabled(valueWithMinimalClass);
        assertFalse(enabled);

        // Confirm the id type was actually changed
        assertEquals(JsonTypeInfo.Id.MINIMAL_CLASS, valueWithMinimalClass.getIdType());

        // Confirm idVisible remains false (default from EMPTY)
        assertFalse(valueWithMinimalClass.getIdVisible());
    }
}
