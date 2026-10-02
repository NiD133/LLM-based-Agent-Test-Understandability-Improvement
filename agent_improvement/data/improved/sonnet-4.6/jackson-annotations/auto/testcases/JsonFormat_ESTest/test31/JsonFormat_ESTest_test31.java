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
public class JsonFormat_ESTest_test31 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that calling withShape() with the same shape the Value already has
     * returns the identical instance (no new object created), while preserving
     * all original properties such as radix and pattern.
     */
    @Test(timeout = 4000)
    public void test31() throws Throwable {
        JsonFormat.Shape scalarShape = JsonFormat.Shape.SCALAR;
        Locale frenchLocale = Locale.FRENCH;
        TimeZone falseTimeZone = TimeZone.getTimeZone("FALSE");
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();

        JsonFormat.Value original = new JsonFormat.Value(
                "FALSE", scalarShape, frenchLocale, falseTimeZone, emptyFeatures, Boolean.TRUE, 10);

        // withShape with the same shape should return the same instance unchanged
        JsonFormat.Value afterWithShape = original.withShape(scalarShape);

        assertSame(afterWithShape, original);
        assertEquals(10, afterWithShape.getRadix());
        assertTrue(afterWithShape.hasPattern());
    }
}
