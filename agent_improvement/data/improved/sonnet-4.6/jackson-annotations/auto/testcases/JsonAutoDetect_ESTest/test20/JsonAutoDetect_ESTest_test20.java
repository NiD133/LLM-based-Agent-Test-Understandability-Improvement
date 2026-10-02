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
public class JsonAutoDetect_ESTest_test20 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // Build a Value with all accessors set to NONE so we can extract NONE as a variable
        JsonAutoDetect.Visibility noneVisibility = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value allNoneValue = JsonAutoDetect.Value.construct(
                noneVisibility, noneVisibility, noneVisibility,
                noneVisibility, noneVisibility, noneVisibility);
        JsonAutoDetect.Visibility retrievedFieldVisibility = allNoneValue.getFieldVisibility(); // NONE

        // Construct a Value that targets only the CREATOR accessor with NONE visibility;
        // all other accessors should remain at DEFAULT.
        JsonAutoDetect.Value creatorOnlyValue = JsonAutoDetect.Value.construct(
                PropertyAccessor.CREATOR, retrievedFieldVisibility);

        assertEquals(JsonAutoDetect.Visibility.NONE,    creatorOnlyValue.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyValue.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyValue.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyValue.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyValue.getIsGetterVisibility());
    }
}
