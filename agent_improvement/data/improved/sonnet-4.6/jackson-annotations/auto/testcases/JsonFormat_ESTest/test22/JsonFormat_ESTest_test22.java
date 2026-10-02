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
public class JsonFormat_ESTest_test22 extends JsonFormat_ESTest_scaffolding {

    /**
     * A default JsonFormat.Value (no arguments) should not have a shape set
     * (hasShape returns false, because the default shape is Shape.ANY) and
     * should not have a non-default radix (hasNonDefaultRadix returns false,
     * because the default radix sentinel DEFAULT_RADIX is used).
     */
    @Test(timeout = 4000)
    public void test22() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        boolean hasShapeSet = defaultValue.hasShape();

        assertFalse("Default JsonFormat.Value should not have a non-default radix", defaultValue.hasNonDefaultRadix());
        assertFalse("Default JsonFormat.Value should report no shape set (shape is ANY)", hasShapeSet);
    }
}
