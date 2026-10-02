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
     * Verifies that constructing a Value with PropertyAccessor.NONE leaves all
     * accessor visibilities at DEFAULT, because NONE means no accessor is targeted.
     * The provided PUBLIC_ONLY visibility is effectively ignored for every accessor type.
     */
    @Test(timeout = 4000)
    public void test19() throws Throwable {
        // PropertyAccessor.NONE targets no accessor, so the supplied visibility is not applied
        PropertyAccessor targetedAccessor = PropertyAccessor.NONE;
        JsonAutoDetect.Visibility suppliedVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        JsonAutoDetect.Value value = JsonAutoDetect.Value.construct(targetedAccessor, suppliedVisibility);

        // All accessor visibilities should remain DEFAULT because no accessor was targeted
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getScalarConstructorVisibility());
    }
}
