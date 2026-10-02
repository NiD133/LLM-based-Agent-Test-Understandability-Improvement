package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test22 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Value.construct(accessor, visibility) should apply the given visibility to
     * only the requested accessor and leave every other accessor at DEFAULT.
     * Here FIELD is configured with ANY, so the field visibility becomes ANY
     * while all remaining accessors stay DEFAULT.
     */
    @Test(timeout = 4000)
    public void constructForFieldSetsOnlyFieldVisibility() throws Throwable {
        JsonAutoDetect.Value value =
                JsonAutoDetect.Value.construct(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        assertEquals(JsonAutoDetect.Visibility.ANY, value.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, value.getGetterVisibility());
    }
}
