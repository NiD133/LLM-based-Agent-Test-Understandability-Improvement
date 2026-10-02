package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test04 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Two Value instances built from the same included-property set and the same
     * "ordered" flag should be considered equal by {@link JsonIncludeProperties.Value#equals(Object)}.
     */
    @Test(timeout = 4000)
    public void twoValuesWithSameIncludedSetAndOrderedFlagAreEqual() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        // Boolean(String) parses anything other than "true" (case-insensitive) as false.
        Boolean ordered = new Boolean("j:w.BxrN!bO}");

        JsonIncludeProperties.Value firstValue =
                new JsonIncludeProperties.Value(includedProperties, ordered);
        JsonIncludeProperties.Value secondValue =
                new JsonIncludeProperties.Value(includedProperties, ordered);

        assertTrue(secondValue.equals(firstValue));
    }
}
