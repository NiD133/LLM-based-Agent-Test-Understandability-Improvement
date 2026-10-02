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
        // Arrange: build a visibility config where is-getter and creator/scalar-ctor
        // require at least PROTECTED_AND_PUBLIC, everything else uses DEFAULT.
        JsonAutoDetect.Visibility defaultVisibility = JsonAutoDetect.Visibility.DEFAULT;
        JsonAutoDetect.Visibility protectedAndPublicVisibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;

        JsonAutoDetect.Value visibilityConfig = JsonAutoDetect.Value.construct(
                defaultVisibility,           // fields
                defaultVisibility,           // getters
                protectedAndPublicVisibility, // isGetters
                defaultVisibility,           // setters
                protectedAndPublicVisibility, // creators
                protectedAndPublicVisibility  // scalarCtors
        );
        assertNotNull(visibilityConfig);

        // Retrieve the is-getter visibility (PROTECTED_AND_PUBLIC) to check member access.
        JsonAutoDetect.Visibility isGetterVisibility = visibilityConfig.getIsGetterVisibility();

        // Mock a member whose modifiers (2327) include the PROTECTED bit (0x4),
        // so PROTECTED_AND_PUBLIC.isVisible() returns true.
        Member member = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(2327).when(member).getModifiers();

        // Act
        boolean isVisible = isGetterVisibility.isVisible(member);

        // Assert: the member is visible under PROTECTED_AND_PUBLIC rules
        assertTrue(isVisible);

        // Assert remaining visibility settings on the constructed value
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, visibilityConfig.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, visibilityConfig.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, visibilityConfig.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, visibilityConfig.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, visibilityConfig.getScalarConstructorVisibility());
    }
}
