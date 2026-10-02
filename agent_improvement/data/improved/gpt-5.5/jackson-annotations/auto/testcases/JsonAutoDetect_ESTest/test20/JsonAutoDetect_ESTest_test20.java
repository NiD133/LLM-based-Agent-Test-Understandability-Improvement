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
        JsonAutoDetect.Visibility noVisibility = JsonAutoDetect.Visibility.NONE;
        JsonAutoDetect.Value allAccessorsHidden = JsonAutoDetect.Value.construct(
                noVisibility, noVisibility, noVisibility,
                noVisibility, noVisibility, noVisibility);

        JsonAutoDetect.Visibility fieldVisibility = allAccessorsHidden.getFieldVisibility();
        PropertyAccessor creatorAccessor = PropertyAccessor.CREATOR;
        JsonAutoDetect.Value creatorOnlyVisibility = JsonAutoDetect.Value.construct(
                creatorAccessor, fieldVisibility);

        assertEquals(JsonAutoDetect.Visibility.NONE, creatorOnlyVisibility.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyVisibility.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyVisibility.getScalarConstructorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyVisibility.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorOnlyVisibility.getIsGetterVisibility());
    }
}
