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
public class JsonFormat_ESTest_test84 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that a default JsonFormat.Value instance has no non-default radix set
     * and returns null (unconfigured) for the WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED feature.
     */
    @Test(timeout = 4000)
    public void test84() throws Throwable {
        JsonFormat.Value defaultFormatValue = new JsonFormat.Value();

        Boolean writeSingleElemArraysUnwrappedSetting =
                defaultFormatValue.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);

        assertFalse("Default Value should not have a non-default radix", defaultFormatValue.hasNonDefaultRadix());
        assertNull("Default Value should have no explicit setting for WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED",
                writeSingleElemArraysUnwrappedSetting);
    }
}
