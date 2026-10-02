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
public class JsonAutoDetect_ESTest_test23 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonAutoDetect.Visibility defaultVisibility = JsonAutoDetect.Visibility.DEFAULT;
        JsonAutoDetect.Visibility protectedAndPublicVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;

        JsonAutoDetect.Value visibilityOverrides = JsonAutoDetect.Value.construct(
                defaultVisibility,
                defaultVisibility,
                protectedAndPublicVisibility,
                defaultVisibility,
                protectedAndPublicVisibility,
                protectedAndPublicVisibility);
        assertNotNull(visibilityOverrides);

        JsonAutoDetect.Visibility isGetterVisibility = visibilityOverrides.getIsGetterVisibility();
        Member member = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(2327).when(member).getModifiers();

        boolean isVisible = isGetterVisibility.isVisible(member);

        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, visibilityOverrides.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, visibilityOverrides.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, visibilityOverrides.getFieldVisibility());
        assertTrue(isVisible);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, visibilityOverrides.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, visibilityOverrides.getCreatorVisibility());
    }
}
