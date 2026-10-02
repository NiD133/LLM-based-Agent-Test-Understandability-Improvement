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

    /**
     * Verifies that constructing a Value with only CREATOR visibility set to NONE
     * (all other accessors left at DEFAULT) and then calling readResolve() returns
     * a Value that preserves NONE for creators and DEFAULT for every other accessor.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Build a Value where only the CREATOR accessor is restricted to NONE
        JsonAutoDetect.Value valueWithCreatorNone =
                JsonAutoDetect.Value.construct(PropertyAccessor.CREATOR, JsonAutoDetect.Visibility.NONE);

        // readResolve() handles JDK deserialization; for a non-predefined Value it returns `this`
        JsonAutoDetect.Value resolvedValue = (JsonAutoDetect.Value) valueWithCreatorNone.readResolve();

        assertNotNull(resolvedValue);
        // The one explicitly set accessor must be NONE
        assertEquals(JsonAutoDetect.Visibility.NONE,    resolvedValue.getCreatorVisibility());
        // Every other accessor was not specified, so it remains at DEFAULT
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, resolvedValue.getScalarConstructorVisibility());
    }
}
