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
public class JsonTypeInfo_ESTest_test21 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that withRequireTypeIdForSubtypes uses reference equality:
     * the first call with Boolean.FALSE produces a new Value instance,
     * but the second call with the same Boolean.FALSE reference returns the
     * existing instance unchanged (no-op copy), because Boolean.FALSE is a
     * cached singleton and the internal check uses ==.
     */
    @Test(timeout = 4000)
    public void test21() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;

        // Boolean.FALSE is a cached singleton, so both calls share the same reference
        Boolean requireTypeIdFalse = Boolean.valueOf(false);

        // First call: EMPTY has null for requireTypeIdForSubtypes, so a new Value is created
        JsonTypeInfo.Value valueWithFalse = emptyValue.withRequireTypeIdForSubtypes(requireTypeIdFalse);

        // Second call: requireTypeIdForSubtypes is already Boolean.FALSE (same reference),
        // so withRequireTypeIdForSubtypes returns 'this' — no new object is allocated
        JsonTypeInfo.Value valueAfterRedundantCall = valueWithFalse.withRequireTypeIdForSubtypes(requireTypeIdFalse);

        // valueWithFalse differs from emptyValue (different requireTypeIdForSubtypes)
        assertNotSame(valueAfterRedundantCall, emptyValue);
        // The redundant call returned the same instance (reference equality short-circuit)
        assertSame(valueAfterRedundantCall, valueWithFalse);
        // idVisible was not modified; EMPTY defaults to false
        assertFalse(valueAfterRedundantCall.getIdVisible());
        // writeTypeIdForDefaultImpl is null in EMPTY, which shouldWriteTypeIdForDefaultImpl treats as true
        assertTrue(valueAfterRedundantCall.shouldWriteTypeIdForDefaultImpl());
    }
}
