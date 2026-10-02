package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonSetter_ESTest_test06 extends JsonSetter_ESTest_scaffolding {

    /**
     * Verifies that a Value constructed with AS_EMPTY for both valueNulls and contentNulls
     * retains those settings and that nonDefaultContentNulls() does not throw
     * (AS_EMPTY is a non-default value so the getter returns it, not null).
     */
    @Test(timeout = 4000)
    public void test_constructWithAsEmpty_retainsBothNullsSettings() throws Throwable {
        Nulls asEmpty = Nulls.AS_EMPTY;
        JsonSetter.Value value = JsonSetter.Value.construct(asEmpty, asEmpty);

        // AS_EMPTY is not DEFAULT, so nonDefaultContentNulls() should return AS_EMPTY (not null)
        value.nonDefaultContentNulls();

        assertEquals(Nulls.AS_EMPTY, value.getContentNulls());
        assertEquals(Nulls.AS_EMPTY, value.getValueNulls());
    }
}
