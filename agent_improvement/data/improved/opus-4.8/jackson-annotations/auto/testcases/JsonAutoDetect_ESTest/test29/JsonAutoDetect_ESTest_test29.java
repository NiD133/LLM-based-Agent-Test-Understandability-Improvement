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
public class JsonAutoDetect_ESTest_test29 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * PROTECTED_AND_PUBLIC visibility requires a member to be either protected
     * or public. A member with no access modifiers (package-private, modifier
     * bits = 0) is therefore NOT visible.
     */
    @Test(timeout = 4000)
    public void packagePrivateMemberIsNotVisibleForProtectedAndPublic() throws Throwable {
        JsonAutoDetect.Visibility protectedAndPublic = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;

        // Mock a Member whose modifier bits are 0 (no access modifiers set).
        Member packagePrivateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0).when(packagePrivateMember).getModifiers();

        boolean visible = protectedAndPublic.isVisible(packagePrivateMember);

        assertFalse(visible);
    }
}
