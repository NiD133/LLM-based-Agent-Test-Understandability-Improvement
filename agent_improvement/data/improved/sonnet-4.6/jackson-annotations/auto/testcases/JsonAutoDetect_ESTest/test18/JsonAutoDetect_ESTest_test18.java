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
public class JsonAutoDetect_ESTest_test18 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Verifies that constructing a Value with PropertyAccessor.ALL sets the creator visibility
     * to the specified visibility level on the resulting Value object.
     */
    @Test(timeout = 4000)
    public void test_constructWithAllAccessors_setsCreatorVisibilityToSpecifiedLevel() throws Throwable {
        // When ALL accessors are targeted, every visibility slot—including creators—should be set
        JsonAutoDetect.Value valueWithAllPublic = JsonAutoDetect.Value.construct(
                PropertyAccessor.ALL, JsonAutoDetect.Visibility.PUBLIC_ONLY);

        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, valueWithAllPublic.getCreatorVisibility());
    }
}
