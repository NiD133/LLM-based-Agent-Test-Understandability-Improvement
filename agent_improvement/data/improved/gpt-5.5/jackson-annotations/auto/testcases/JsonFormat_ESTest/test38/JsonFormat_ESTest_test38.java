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
public class JsonFormat_ESTest_test38 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test38() throws Throwable {
        JsonFormat.Shape objectShape = JsonFormat.Shape.OBJECT;
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        Boolean explicitLeniency = Boolean.valueOf(false);

        JsonFormat.Value configuredValue = new JsonFormat.Value(
                "`K22BIe$=Oc|vAr4!T",
                objectShape,
                "7",
                "7",
                emptyFeatures,
                explicitLeniency,
                4024);

        JsonFormat.Value[] valuesToMerge = new JsonFormat.Value[9];
        valuesToMerge[0] = configuredValue;
        valuesToMerge[1] = valuesToMerge[0];

        JsonFormat.Value mergedValue = JsonFormat.Value.mergeAll(valuesToMerge);

        assertEquals(4024, mergedValue.getRadix());
        assertEquals("7", mergedValue.timeZoneAsString());
        assertNotNull(mergedValue);
        assertSame(mergedValue, configuredValue);
        assertEquals(JsonFormat.Shape.OBJECT, mergedValue.getShape());
        assertEquals("`K22BIe$=Oc|vAr4!T", mergedValue.getPattern());
    }
}
