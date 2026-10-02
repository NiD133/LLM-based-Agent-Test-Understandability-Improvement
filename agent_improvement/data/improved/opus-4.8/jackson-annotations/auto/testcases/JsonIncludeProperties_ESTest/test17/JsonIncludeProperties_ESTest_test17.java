package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test17 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that getOrdered() returns the Boolean supplied to the constructor.
     * The "ordered" flag is built from a non-"true" String, so Boolean parsing
     * yields Boolean.FALSE, which getOrdered() must return unchanged.
     */
    @Test(timeout = 4000)
    public void getOrdered_returnsConstructorSuppliedFalseFlag() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        Boolean orderedFlag = new Boolean("not-the-word-true"); // any non-"true" String parses to false

        JsonIncludeProperties.Value value =
                new JsonIncludeProperties.Value(includedProperties, orderedFlag);

        Boolean ordered = value.getOrdered();

        assertFalse(ordered);
    }
}
