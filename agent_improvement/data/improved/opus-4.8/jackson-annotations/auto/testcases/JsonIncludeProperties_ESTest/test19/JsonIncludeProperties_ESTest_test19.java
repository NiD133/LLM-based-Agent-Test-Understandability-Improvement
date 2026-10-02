package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test19 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Verifies that an empty set of included property names does not report a
     * {@link JsonIncludeProperties.Value} (built from that same set) as one of its
     * elements. Constructing the Value does not add it to the backing set, so the
     * set remains empty and {@code contains(...)} returns {@code false}.
     */
    @Test(timeout = 4000)
    public void emptyIncludedSetDoesNotContainTheValueBuiltFromIt() throws Throwable {
        LinkedHashSet<String> includedProperties = new LinkedHashSet<String>();
        Boolean ordered = Boolean.TRUE;

        JsonIncludeProperties.Value value =
                new JsonIncludeProperties.Value(includedProperties, ordered);

        boolean setContainsValue = includedProperties.contains(value);

        assertFalse(setContainsValue);
    }
}
