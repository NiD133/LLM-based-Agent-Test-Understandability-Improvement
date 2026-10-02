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
public class JsonAutoDetect_ESTest_test13 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        JsonAutoDetect.Visibility noneVisibility = JsonAutoDetect.Visibility.NONE;

        JsonAutoDetect.Value allAccessorsDisabled = JsonAutoDetect.Value.construct(
                noneVisibility,
                noneVisibility,
                noneVisibility,
                noneVisibility,
                noneVisibility,
                noneVisibility);

        PropertyAccessor scalarConstructorAccessor = PropertyAccessor.SCALAR_CONSTRUCTOR;
        JsonAutoDetect.Value scalarConstructorOverride =
                JsonAutoDetect.Value.construct(scalarConstructorAccessor, noneVisibility);

        JsonAutoDetect.Value mergedVisibility =
                allAccessorsDisabled.withOverrides(scalarConstructorOverride);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT,
                scalarConstructorOverride.getGetterVisibility());
        assertSame(mergedVisibility, allAccessorsDisabled);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT,
                scalarConstructorOverride.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT,
                scalarConstructorOverride.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT,
                scalarConstructorOverride.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NONE,
                scalarConstructorOverride.getScalarConstructorVisibility());
    }
}
