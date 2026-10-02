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

    /**
     * When {@code PropertyAccessor.NONE} is used with {@code Value.construct},
     * the switch-case falls through to the NONE branch (a no-op), so every
     * visibility slot stays at its initial DEFAULT value regardless of the
     * supplied visibility argument.
     */
    @Test(timeout = 4000)
    public void test19_constructWithAccessorNone_allVisibilitiesRemainDefault() throws Throwable {
        // NONE means no accessor category is targeted, so supplied visibility is ignored
        PropertyAccessor noAccessor = PropertyAccessor.NONE;
        JsonAutoDetect.Visibility publicOnly = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(noAccessor, publicOnly);

        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getScalarConstructorVisibility());
    }
}
