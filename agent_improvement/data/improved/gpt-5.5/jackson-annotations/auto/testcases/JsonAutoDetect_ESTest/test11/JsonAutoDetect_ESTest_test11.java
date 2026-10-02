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
public class JsonAutoDetect_ESTest_test11 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        PropertyAccessor creatorAccessor = PropertyAccessor.CREATOR;
        JsonAutoDetect.Visibility disabledVisibility = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value creatorOverride = JsonAutoDetect.Value.construct(creatorAccessor, disabledVisibility);

        JsonAutoDetect.Value resolvedOverride = (JsonAutoDetect.Value) creatorOverride.readResolve();

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedOverride.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, resolvedOverride.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedOverride.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedOverride.getSetterVisibility());
        assertNotNull(resolvedOverride);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedOverride.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedOverride.getGetterVisibility());
    }
}
