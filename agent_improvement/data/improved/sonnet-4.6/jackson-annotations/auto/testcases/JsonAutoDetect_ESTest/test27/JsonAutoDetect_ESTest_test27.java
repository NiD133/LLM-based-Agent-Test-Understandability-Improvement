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
public class JsonAutoDetect_ESTest_test27 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Visibility.NONE must always report a member as not visible, even when the
     * member reference is null. The implementation short-circuits to {@code false}
     * before inspecting the member, so passing null must not throw and must return false.
     */
    @Test(timeout = 4000)
    public void test_visibilityNone_isNotVisibleForNullMember() throws Throwable {
        JsonAutoDetect.Visibility noneVisibility = JsonAutoDetect.Visibility.NONE;

        boolean isVisible = noneVisibility.isVisible((Member) null);

        assertFalse(isVisible);
    }
}
