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
     * Visibility.isVisible(Member) inspects the member's access modifiers.
     * When given a null member, it dereferences null and must throw a
     * NullPointerException (with no message).
     */
    @Test(timeout = 4000)
    public void isVisibleWithNullMemberThrowsNullPointerException() throws Throwable {
        JsonAutoDetect.Visibility publicOnly = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        try {
            publicOnly.isVisible((Member) null);
            fail("Expected a NullPointerException when the member is null");
        } catch (NullPointerException e) {
            // No message is provided by the exception.
            verifyException("com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility", e);
        }
    }
}
