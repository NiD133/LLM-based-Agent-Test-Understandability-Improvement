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
public class JsonAutoDetect_ESTest_test07 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.defaultVisibility();
        PropertyAccessor creatorAccessor = PropertyAccessor.CREATOR;
        JsonAutoDetect.Visibility creatorVisibility = JsonAutoDetect.Visibility.NONE;

        JsonAutoDetect.Value creatorOverride = JsonAutoDetect.Value.construct(creatorAccessor, creatorVisibility);

        defaultVisibility.equals(creatorOverride);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOverride.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOverride.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOverride.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOverride.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOverride.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, creatorOverride.getCreatorVisibility());
    }
}
