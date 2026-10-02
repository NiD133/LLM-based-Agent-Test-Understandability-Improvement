package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test07 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * A JsonIncludeProperties.Value is never considered equal to a plain Object,
     * because equals() first checks that the other object's class matches.
     */
    @Test(timeout = 4000)
    public void valueIsNotEqualToPlainObject() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        Boolean ordered = Boolean.FALSE;
        JsonIncludeProperties.Value value =
                new JsonIncludeProperties.Value(includedProperties, ordered);

        boolean isEqual = value.equals(new Object());

        assertFalse(isEqual);
    }
}
