package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test46 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_featuresNotEqualToShapeOfDifferentType() throws Throwable {
        // Features.equals() must return false when compared against an object of a different class
        JsonFormat.Features emptyFeatures = JsonFormat.Features.empty();
        JsonFormat.Shape stringShape = JsonFormat.Shape.STRING;

        boolean isEqual = emptyFeatures.equals(stringShape);

        assertFalse(isEqual);
    }
}
