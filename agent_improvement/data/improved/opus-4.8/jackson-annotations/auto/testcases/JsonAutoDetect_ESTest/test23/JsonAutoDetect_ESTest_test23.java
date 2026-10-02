package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test23 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Builds a Value whose is-getter visibility is PROTECTED_AND_PUBLIC, then verifies that
     * Visibility.isVisible(Member) returns true for a member whose modifiers include "protected".
     * Also checks that every accessor's visibility was stored exactly as supplied to construct().
     */
    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonAutoDetect.Visibility defaultVisibility = JsonAutoDetect.Visibility.DEFAULT;
        JsonAutoDetect.Visibility protectedAndPublic = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;

        // construct(fields, getters, isGetters, setters, creators, scalarConstructors)
        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(
                defaultVisibility,   // fields
                defaultVisibility,   // getters
                protectedAndPublic,  // isGetters
                defaultVisibility,   // setters
                protectedAndPublic,  // creators
                protectedAndPublic); // scalarConstructors
        assertNotNull(value);

        // Each accessor should report back exactly the visibility it was constructed with.
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, value.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, value.getScalarConstructorVisibility());

        // A member whose modifier bits include "protected" (bit 0x4 is set in 2327).
        Member protectedMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(2327).when(protectedMember).getModifiers();

        // PROTECTED_AND_PUBLIC accepts protected members, so isVisible should be true.
        JsonAutoDetect.Visibility isGetterVisibility = value.getIsGetterVisibility();
        boolean isVisible = isGetterVisibility.isVisible(protectedMember);
        assertTrue(isVisible);
    }
}
