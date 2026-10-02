package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test04 extends JsonFormat_ESTest_scaffolding {

    private static final String NON_DEFAULT_PATTERN = "*h,2D`=nR6aV]Mg'.";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        JsonFormat.Value defaultFormat = new JsonFormat.Value();
        JsonFormat.Value patternOnlyFormat = JsonFormat.Value.forPattern(NON_DEFAULT_PATTERN);

        boolean matchesDefaultFormat = patternOnlyFormat.equals(defaultFormat);

        assertFalse(patternOnlyFormat.hasShape());
        assertFalse(matchesDefaultFormat);
        assertFalse(patternOnlyFormat.hasNonDefaultRadix());
        assertFalse(patternOnlyFormat.hasTimeZone());
    }
}
