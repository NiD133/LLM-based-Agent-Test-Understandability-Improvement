package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test02 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that a Value with an explicit empty include-set (meaning "include no properties")
     * is NOT equal to the ALL Value produced by passing null to Value.from() (meaning
     * "include all properties / no filter applied").
     *
     * The distinction matters: null _included == unrestricted, empty _included == exclude all.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // A Value whose _included is an empty set — "include nothing"
        LinkedHashSet<String> emptyIncludeSet = new LinkedHashSet<String>();
        JsonIncludeProperties.Value emptyIncludeValue = new JsonIncludeProperties.Value(emptyIncludeSet, (Boolean) null);

        // Value.from(null) returns the ALL constant whose _included is null — "include everything"
        JsonIncludeProperties.Value allIncludeValue = JsonIncludeProperties.Value.from((JsonIncludeProperties) null);

        // "include everything" must not be equal to "include nothing"
        boolean areEqual = allIncludeValue.equals(emptyIncludeValue);
        assertFalse(areEqual);
    }
}
