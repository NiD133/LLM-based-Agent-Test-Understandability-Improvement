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
public class JsonAutoDetect_ESTest_test29 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_protectedAndPublicVisibility_packagePrivateMemberIsNotVisible() throws Throwable {
        // A modifier value of 0 means package-private (no access modifier keyword).
        // PROTECTED_AND_PUBLIC requires at least protected, so a package-private member must be rejected.
        // getModifiers() is called twice inside isVisible: once to check isProtected, once to check isPublic.
        JsonAutoDetect.Visibility visibility = JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC;
        Member packagePrivateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(0, 0).when(packagePrivateMember).getModifiers();

        boolean isVisible = visibility.isVisible(packagePrivateMember);

        assertFalse(isVisible);
    }
}
