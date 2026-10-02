package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test46 extends JsonFormat_ESTest_scaffolding {

    /**
     * Verifies that an empty {@link JsonFormat.Features} instance is never considered
     * equal to a {@link JsonFormat.Shape} value, since they are unrelated types.
     */
    @Test(timeout = 4000)
    public void emptyFeaturesIsNotEqualToShape() throws Throwable {
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        JsonFormat.Shape stringShape = JsonFormat.Shape.STRING;

        boolean featuresEqualsShape = emptyFeatures.equals(stringShape);

        assertFalse(featuresEqualsShape);
    }
}
