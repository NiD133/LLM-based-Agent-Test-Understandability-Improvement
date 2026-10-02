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
public class JsonFormat_ESTest_test59 extends JsonFormat_ESTest_scaffolding {

    // withOverrides(null) must return the same Features instance unchanged
    @Test(timeout = 4000)
    public void withOverrides_returnsOriginalInstance_whenOverridesIsNull() throws Throwable {
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        JsonFormat.Features result = emptyFeatures.withOverrides((JsonFormat.Features) null);
        assertSame(emptyFeatures, result);
    }
}
