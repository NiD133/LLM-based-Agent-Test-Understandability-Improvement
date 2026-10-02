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
public class JsonAutoDetect_ESTest_test28 extends JsonAutoDetect_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test28() throws Throwable {
        JsonAutoDetect.Visibility publicOnlyVisibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        try {
            publicOnlyVisibility.isVisible((Member) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException expectedException) {
            verifyException("com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility", expectedException);
        }
    }
}
