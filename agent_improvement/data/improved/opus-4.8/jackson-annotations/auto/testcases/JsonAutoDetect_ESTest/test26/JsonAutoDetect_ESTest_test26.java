package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test26 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * The DEFAULT visibility level defers to context-specific defaults rather than
     * making its own decision, so isVisible(...) always reports "not visible" and
     * never even dereferences the supplied Member. Passing null therefore returns
     * false without throwing a NullPointerException.
     */
    @Test(timeout = 4000)
    public void defaultVisibilityTreatsNullMemberAsNotVisible() throws Throwable {
        JsonAutoDetect.Visibility defaultVisibility = JsonAutoDetect.Visibility.DEFAULT;

        boolean visible = defaultVisibility.isVisible((Member) null);

        assertFalse(visible);
    }
}
