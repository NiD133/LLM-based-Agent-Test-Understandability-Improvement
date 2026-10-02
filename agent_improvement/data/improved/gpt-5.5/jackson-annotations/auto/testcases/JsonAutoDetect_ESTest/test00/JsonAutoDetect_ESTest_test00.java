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
public class JsonAutoDetect_ESTest_test00 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        JsonAutoDetect.Visibility disabledVisibility = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Visibility publicOnlyVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        JsonAutoDetect.Value baseVisibility = JsonAutoDetect.Value.construct(
                disabledVisibility,
                disabledVisibility,
                disabledVisibility,
                disabledVisibility,
                publicOnlyVisibility,
                disabledVisibility);
        PropertyAccessor scalarConstructorAccessor = PropertyAccessor.SCALAR_CONSTRUCTOR;
        JsonAutoDetect.Value scalarConstructorOverride = JsonAutoDetect.Value.construct(
                scalarConstructorAccessor,
                publicOnlyVisibility);

        JsonAutoDetect.Value mergedVisibility = baseVisibility.withOverrides(scalarConstructorOverride);

        assertEquals(JsonAutoDetect.Visibility.NONE, mergedVisibility.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, mergedVisibility.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, baseVisibility.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, mergedVisibility.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, scalarConstructorOverride.getIsGetterVisibility());
        assertFalse(mergedVisibility.equals((Object) baseVisibility));
        assertEquals(JsonAutoDetect.Visibility.NONE, mergedVisibility.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE, mergedVisibility.getFieldVisibility());
    }
}
