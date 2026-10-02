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
public class JsonAutoDetect_ESTest_test05 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that withIsGetterVisibility() only changes the is-getter visibility
     * while leaving all other visibility settings unchanged from the DEFAULT value.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.DEFAULT;
        JsonAutoDetect.Visibility protectedAndPublic = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;

        JsonAutoDetect.Value updatedVisibility = defaultVisibility.withIsGetterVisibility(protectedAndPublic);

        // is-getter visibility should reflect the new value
        assertEquals(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC, updatedVisibility.getIsGetterVisibility());

        // all other visibility settings should remain unchanged from DEFAULT
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedVisibility.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.ANY,         updatedVisibility.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedVisibility.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE, updatedVisibility.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, updatedVisibility.getFieldVisibility());
    }
}
