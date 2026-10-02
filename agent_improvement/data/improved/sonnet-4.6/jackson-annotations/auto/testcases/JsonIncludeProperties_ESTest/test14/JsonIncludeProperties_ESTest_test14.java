package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test14 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that calling withOverrides() with an "include all" (null-included) Value
     * returns the original Value unchanged: the result is the same instance and is not
     * equal to the "include all" override.
     */
    @Test(timeout = 4000)
    public void test14() throws Throwable {
        // A Value with an explicit empty included-set (include no properties) and unset ordering
        LinkedHashSet<String> emptyProperties = new LinkedHashSet<String>();
        JsonIncludeProperties.Value valueWithEmptyIncluded =
                new JsonIncludeProperties.Value(emptyProperties, (Boolean) null);

        // from(null) returns the ALL sentinel: included=null meaning "include everything"
        JsonIncludeProperties.Value allValue =
                JsonIncludeProperties.Value.from((JsonIncludeProperties) null);

        // withOverrides returns 'this' when the override has null included (the ALL sentinel),
        // because overriding with an undefined set has no effect on the original value
        JsonIncludeProperties.Value result = valueWithEmptyIncluded.withOverrides(allValue);

        // The result must differ from the ALL override (empty set != null set)
        assertFalse(result.equals((Object) allValue));
        // The result must be the same instance as the original (no new object created)
        assertSame(result, valueWithEmptyIncluded);
    }
}
