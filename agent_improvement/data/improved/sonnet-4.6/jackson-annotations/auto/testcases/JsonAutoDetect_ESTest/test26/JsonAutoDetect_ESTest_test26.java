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
public class JsonAutoDetect_ESTest_test26 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Visibility.DEFAULT represents "use inherited/contextual default" and its
     * isVisible() implementation always returns false (the switch default branch),
     * so it should report any member — including null — as not visible.
     */
    @Test(timeout = 4000)
    public void test_defaultVisibility_isNotVisibleForNullMember() throws Throwable {
        JsonAutoDetect.Visibility defaultVisibility = JsonAutoDetect.Visibility.DEFAULT;

        boolean isVisible = defaultVisibility.isVisible((Member) null);

        assertFalse(isVisible);
    }
}
