package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test19 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        PropertyAccessor accessorWithNoOverride = PropertyAccessor.NONE;
        JsonAutoDetect.Visibility requestedVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        JsonAutoDetect.Value visibilityOverrides = JsonAutoDetect.Value.construct(
                accessorWithNoOverride, requestedVisibility);

        JsonAutoDetect.Visibility defaultVisibility = JsonAutoDetect.Visibility.DEFAULT;
        assertEquals(defaultVisibility, visibilityOverrides.getGetterVisibility());
        assertEquals(defaultVisibility, visibilityOverrides.getIsGetterVisibility());
        assertEquals(defaultVisibility, visibilityOverrides.getCreatorVisibility());
        assertEquals(defaultVisibility, visibilityOverrides.getFieldVisibility());
        assertEquals(defaultVisibility, visibilityOverrides.getSetterVisibility());
        assertEquals(defaultVisibility, visibilityOverrides.getScalarConstructorVisibility());
    }
}
