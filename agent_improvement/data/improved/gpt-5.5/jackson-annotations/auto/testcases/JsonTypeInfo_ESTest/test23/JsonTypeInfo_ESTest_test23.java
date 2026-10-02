package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test23 extends JsonTypeInfo_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test23() throws Throwable {
        JsonTypeInfo.Value emptyTypeInfo = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value visibleTypeIdInfo = emptyTypeInfo.withIdVisible(true);

        boolean emptyEqualsVisibleTypeIdInfo = emptyTypeInfo.equals(visibleTypeIdInfo);

        assertFalse(visibleTypeIdInfo.equals((Object) emptyTypeInfo));
        assertFalse(emptyEqualsVisibleTypeIdInfo);
    }
}
