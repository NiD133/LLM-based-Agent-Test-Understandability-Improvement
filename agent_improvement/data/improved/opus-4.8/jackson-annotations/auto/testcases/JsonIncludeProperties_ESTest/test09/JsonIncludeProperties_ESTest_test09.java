package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test09 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Removing an element that was never added to an empty set returns false.
     * Here a JsonIncludeProperties.Value is constructed and then used as the
     * removal target against the same (empty) set that backs its included names.
     */
    @Test(timeout = 4000)
    public void removeUnaddedValueFromEmptySetReturnsFalse() throws Throwable {
        LinkedHashSet<String> includedNames = new LinkedHashSet<String>();
        Boolean orderingFlag = new Boolean("j:w.BxrN!bO}"); // non-"true" text parses to false

        JsonIncludeProperties.Value value =
                new JsonIncludeProperties.Value(includedNames, orderingFlag);

        boolean wasRemoved = includedNames.remove(value);

        assertFalse(wasRemoved);
    }
}
