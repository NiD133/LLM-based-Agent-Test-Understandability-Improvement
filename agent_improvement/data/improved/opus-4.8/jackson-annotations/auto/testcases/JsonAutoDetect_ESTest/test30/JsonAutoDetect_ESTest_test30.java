package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Member;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonAutoDetect_ESTest_test30 extends JsonAutoDetect_ESTest_scaffolding {

    /**
     * Visibility.ANY accepts every access modifier, so isVisible() should report
     * "visible" unconditionally. The ANY branch returns true without inspecting
     * the Member, which means even a null Member is treated as visible.
     */
    @Test(timeout = 4000)
    public void anyVisibilityTreatsNullMemberAsVisible() throws Throwable {
        JsonAutoDetect.Visibility anyVisibility = JsonAutoDetect.Visibility.ANY;

        boolean visible = anyVisibility.isVisible((Member) null);

        assertTrue(visible);
    }
}
