package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test02 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Two {@link JsonIncludeProperties.Value} instances are not equal when their
     * "included" sets differ. Here one Value has an explicit (empty) include set,
     * while {@code Value.from(null)} returns the shared ALL instance whose include
     * set is {@code null} ("all properties included"). An empty set is not equal
     * to {@code null}, so the two Values must not be equal.
     */
    @Test(timeout = 4000)
    public void emptyIncludedSetIsNotEqualToAllValue() throws Throwable {
        LinkedHashSet<String> emptyIncluded = new LinkedHashSet<String>();
        JsonIncludeProperties.Value valueWithEmptyIncluded =
                new JsonIncludeProperties.Value(emptyIncluded, (Boolean) null);

        JsonIncludeProperties.Value allValue =
                JsonIncludeProperties.Value.from((JsonIncludeProperties) null);

        boolean valuesAreEqual = allValue.equals(valueWithEmptyIncluded);

        assertFalse(valuesAreEqual);
    }
}
