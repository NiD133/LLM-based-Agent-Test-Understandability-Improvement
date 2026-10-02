package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import java.lang.reflect.Member;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test27 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Visibility.NONE means nothing is auto-detectable, so isVisible should
     * always report false. It short-circuits on the enum value itself and never
     * inspects the member, so even a null Member argument returns false without
     * throwing.
     */
    @Test(timeout = 4000)
    public void noneVisibilityReportsNotVisibleEvenForNullMember() throws Throwable {
        JsonAutoDetect.Visibility noneVisibility = JsonAutoDetect.Visibility.NONE;

        boolean visible = noneVisibility.isVisible((Member) null);

        assertFalse(visible);
    }
}
