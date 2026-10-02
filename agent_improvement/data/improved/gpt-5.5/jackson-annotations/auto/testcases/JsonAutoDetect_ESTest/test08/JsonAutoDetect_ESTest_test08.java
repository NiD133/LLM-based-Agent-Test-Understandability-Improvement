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
public class JsonAutoDetect_ESTest_test08 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        PropertyAccessor creatorAccessor = PropertyAccessor.CREATOR;
        JsonAutoDetect.Visibility publicOnly = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        JsonAutoDetect.Value creatorVisibility = JsonAutoDetect.Value.construct(
                creatorAccessor, publicOnly);

        boolean equalsNull = creatorVisibility.equals((Object) null);
        assertFalse(equalsNull);
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorVisibility.getGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorVisibility.getIsGetterVisibility());
        assertEquals(JsonAutoDetect.Visibility.PUBLIC_ONLY, creatorVisibility.getCreatorVisibility());
        assertEquals(JsonAutoDetect.Visibility.DEFAULT, creatorVisibility.getScalarConstructorVisibility());
    }
}
