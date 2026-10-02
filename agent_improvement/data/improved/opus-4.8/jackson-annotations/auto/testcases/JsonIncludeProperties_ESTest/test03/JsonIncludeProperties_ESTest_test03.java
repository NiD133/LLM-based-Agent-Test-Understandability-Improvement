package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test03 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Two Value instances that share the same included-properties set but differ
     * on the "ordered" flag must not be considered equal.
     */
    @Test(timeout = 4000)
    public void valuesWithDifferentOrderedFlagAreNotEqual() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();

        // "j:w.BxrN!bO}" is not "true", so Boolean parses it to false.
        Boolean orderedFalse = new Boolean("j:w.BxrN!bO}");
        JsonIncludeProperties.Value valueOrderedFalse =
                new JsonIncludeProperties.Value(includedProperties, orderedFalse);

        Boolean orderedTrue = Boolean.TRUE;
        JsonIncludeProperties.Value valueOrderedTrue =
                new JsonIncludeProperties.Value(includedProperties, orderedTrue);

        boolean areEqual = valueOrderedTrue.equals(valueOrderedFalse);

        assertFalse(areEqual);
    }
}
