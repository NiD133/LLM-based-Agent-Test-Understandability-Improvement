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
public class JsonAutoDetect_ESTest_test24 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test24() throws Throwable {
        JsonAutoDetect.Visibility nonPrivateVisibility = JsonAutoDetect.Visibility.NON_PRIVATE;
        Member packagePrivateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(0).when(packagePrivateMember).getModifiers();

        boolean isVisible = nonPrivateVisibility.isVisible(packagePrivateMember);

        assertTrue(isVisible);
    }
}
