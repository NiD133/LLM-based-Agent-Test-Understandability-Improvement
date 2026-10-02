package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test25 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Visibility.NON_PRIVATE accepts any member that is NOT declared private.
     * Here the mocked member reports modifier flags whose "private" bit is set
     * (the value -1458 has bit 0x2 / Modifier.PRIVATE set), so the member is
     * private and therefore must be reported as not visible.
     */
    @Test(timeout = 4000)
    public void nonPrivateVisibility_rejectsPrivateMember() throws Throwable {
        JsonAutoDetect.Visibility nonPrivateVisibility = JsonAutoDetect.Visibility.NON_PRIVATE;

        Member privateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        int modifiersWithPrivateBitSet = -1458;
        doReturn(modifiersWithPrivateBitSet).when(privateMember).getModifiers();

        boolean isVisible = nonPrivateVisibility.isVisible(privateMember);

        assertFalse(isVisible);
    }
}
