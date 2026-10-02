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
public class JsonAutoDetect_ESTest_test24 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * NON_PRIVATE visibility accepts any member that is not declared {@code private}.
     * A member whose modifier bitmask is 0 (i.e. package-private, with no access
     * modifier) is therefore considered visible.
     */
    @Test(timeout = 4000)
    public void nonPrivateVisibility_acceptsPackagePrivateMember() throws Throwable {
        JsonAutoDetect.Visibility nonPrivateVisibility = JsonAutoDetect.Visibility.NON_PRIVATE;

        // A member with no access modifiers (modifiers == 0) -> package-private.
        Member packagePrivateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn(0).when(packagePrivateMember).getModifiers();

        boolean isVisible = nonPrivateVisibility.isVisible(packagePrivateMember);

        assertTrue("NON_PRIVATE should treat a package-private member as visible", isVisible);
    }
}
