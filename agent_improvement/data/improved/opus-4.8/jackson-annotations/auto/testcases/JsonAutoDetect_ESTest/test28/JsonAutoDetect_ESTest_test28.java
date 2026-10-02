package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test28 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Visibility.PUBLIC_ONLY.isVisible(...) inspects the member's access
     * modifiers via Member.getModifiers(). Passing a null Member therefore
     * triggers a NullPointerException inside the Visibility enum.
     */
    @Test(timeout = 4000)
    public void isVisibleWithNullMemberThrowsNullPointerException() throws Throwable {
        JsonAutoDetect.Visibility publicOnly = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        try {
            publicOnly.isVisible((Member) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // Exception originates from JsonAutoDetect.Visibility (no message).
            verifyException("com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility", e);
        }
    }
}
