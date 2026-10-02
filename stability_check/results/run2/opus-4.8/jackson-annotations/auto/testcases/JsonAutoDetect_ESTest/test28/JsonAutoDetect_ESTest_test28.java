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
     * Visibility.isVisible(Member) dereferences the member to read its access
     * modifiers, so passing a null member must fail with a NullPointerException.
     */
    @Test(timeout = 4000)
    public void isVisibleWithNullMemberThrowsNullPointerException() throws Throwable {
        JsonAutoDetect.Visibility visibility = JsonAutoDetect.Visibility.PUBLIC_ONLY;

        try {
            visibility.isVisible((Member) null);
            fail("Expecting exception: NullPointerException");
        } catch (NullPointerException e) {
            // Thrown from Visibility while reading modifiers of the null member;
            // the exception carries no message.
            verifyException("com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility", e);
        }
    }
}
