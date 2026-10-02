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
public class JsonFormat_ESTest_test43 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test43() throws Throwable {
        JsonFormat.Shape requestedShape = JsonFormat.Shape.BOOLEAN;
        JsonFormat.Features noFeatureOverrides = JsonFormat.Features.empty();
        Boolean lenientSetting = Boolean.valueOf((String) null);

        JsonFormat.Value formatValue = new JsonFormat.Value(
                (String) null,
                requestedShape,
                (String) null,
                (String) null,
                noFeatureOverrides,
                lenientSetting,
                1695);

        assertEquals(JsonFormat.Shape.BOOLEAN, formatValue.getShape());
        assertEquals(1695, formatValue.getRadix());
    }
}
