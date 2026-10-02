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
public class JsonAutoDetect_ESTest_test25 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * NON_PRIVATE visibility only allows non-private members.
     * The modifier value -1458 has the PRIVATE bit set (Modifier.PRIVATE == 2),
     * so isVisible() must return false.
     */
    @Test(timeout = 4000)
    public void test25_nonPrivateVisibility_returnsFalse_whenMemberIsPrivate() throws Throwable {
        JsonAutoDetect.Visibility nonPrivateVisibility = JsonAutoDetect.Visibility.NON_PRIVATE;

        // Mock a Member whose modifiers include the private bit (-1458 & 2 != 0)
        Member privateMember = mock(Member.class, new ViolatedAssumptionAnswer());
        doReturn((-1458)).when(privateMember).getModifiers();

        boolean isVisible = nonPrivateVisibility.isVisible(privateMember);

        assertFalse(isVisible);
    }
}
