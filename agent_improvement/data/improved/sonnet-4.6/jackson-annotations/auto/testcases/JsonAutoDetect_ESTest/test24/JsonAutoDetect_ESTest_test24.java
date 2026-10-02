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

    // Modifier value 0 represents package-private (no explicit access modifier)
    private static final int PACKAGE_PRIVATE_MODIFIER = 0;

    @Test(timeout = 4000)
    public void test_nonPrivateVisibility_acceptsPackagePrivateMember() throws Throwable {
        // NON_PRIVATE visibility allows any member that is not explicitly private,
        // including package-private members (modifier = 0).
        JsonAutoDetect.Visibility nonPrivateVisibility = JsonAutoDetect.Visibility.NON_PRIVATE;

        Member packagePrivateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(PACKAGE_PRIVATE_MODIFIER).when(packagePrivateMember).getModifiers();

        boolean isVisible = nonPrivateVisibility.isVisible(packagePrivateMember);

        assertTrue(isVisible);
    }
}
