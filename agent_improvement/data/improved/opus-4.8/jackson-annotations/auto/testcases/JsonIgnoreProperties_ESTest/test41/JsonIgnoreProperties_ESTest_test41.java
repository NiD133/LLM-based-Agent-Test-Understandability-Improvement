package com.fasterxml.jackson.annotation;

import static org.junit.Assert.assertEquals;

import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test41 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonIgnoreProperties.Value#toString()} renders every
     * configured flag. Here a Value is built with no ignored properties and only
     * {@code merge} enabled, so the rendered string should list an empty set of
     * ignored names and reflect each boolean flag.
     */
    @Test(timeout = 4000)
    public void toString_withEmptyIgnoredAndMergeEnabled_listsAllFlags() throws Throwable {
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ false,
                /* allowGetters  */ false,
                /* allowSetters  */ false,
                /* merge         */ true);

        String description = value.toString();

        assertEquals(
                "JsonIgnoreProperties.Value(ignored=[],ignoreUnknown=false,allowGetters=false,allowSetters=false,merge=true)",
                description);
    }
}
