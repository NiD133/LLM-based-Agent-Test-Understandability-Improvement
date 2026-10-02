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
public class JsonAutoDetect_ESTest_test32 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that a Value constructed with all visibilities set to NONE
     * correctly reports NONE for isGetter visibility, and that valueFor()
     * can be called without error.
     */
    @Test(timeout = 4000)
    public void test32() throws Throwable {
        JsonAutoDetect.Visibility noneVisibility = JsonAutoDetect.Visibility.NONE;

        JsonAutoDetect.Value allNoneValue = JsonAutoDetect.Value.construct(
                noneVisibility,  // fields
                noneVisibility,  // getters
                noneVisibility,  // isGetters
                noneVisibility,  // setters
                noneVisibility,  // creators
                noneVisibility   // scalarConstructors
        );

        allNoneValue.valueFor();

        assertEquals(JsonAutoDetect.Visibility.NONE, allNoneValue.getIsGetterVisibility());
    }
}
