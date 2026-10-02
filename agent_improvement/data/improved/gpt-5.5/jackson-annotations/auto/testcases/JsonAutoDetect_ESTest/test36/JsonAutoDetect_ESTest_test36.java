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
public class JsonAutoDetect_ESTest_test36 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test36() throws Throwable {
        JsonAutoDetect.Value defaultVisibility = JsonAutoDetect.Value.DEFAULT;
        JsonAutoDetect.Visibility creatorVisibilityOverride = JsonAutoDetect.Visibility.NON_PRIVATE;

        JsonAutoDetect.Value visibilityWithNonPrivateCreators =
                defaultVisibility.withCreatorVisibility(creatorVisibilityOverride);
        assertNotNull(visibilityWithNonPrivateCreators);

        JsonAutoDetect.Value mergedWithDefaultVisibility =
                visibilityWithNonPrivateCreators.withOverrides(defaultVisibility);

        assertSame(defaultVisibility, mergedWithDefaultVisibility);
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY,
                visibilityWithNonPrivateCreators.getFieldVisibility());
        assertEquals(JsonAutoDetect.Visibility.ANY,
                visibilityWithNonPrivateCreators.getSetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE,
                visibilityWithNonPrivateCreators.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY,
                visibilityWithNonPrivateCreators.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY,
                visibilityWithNonPrivateCreators.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.NON_PRIVATE,
                visibilityWithNonPrivateCreators.getScalarConstructorVisibility());
    }
}
