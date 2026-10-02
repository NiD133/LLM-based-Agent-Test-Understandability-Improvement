package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIgnoreProperties_ESTest_test15 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that readResolve() preserves a non-empty Value instance and retains
     * all its property flags after JDK deserialization resolution.
     *
     * The Value is constructed with:
     *   - no explicitly ignored property names
     *   - ignoreUnknown = true   (unknown JSON fields are silently skipped)
     *   - allowGetters  = true   (getters are NOT suppressed by the ignore list)
     *   - allowSetters  = false  (setters ARE suppressed by the ignore list)
     *   - merge         = true   (settings are merged rather than replaced on override)
     *
     * Because at least one flag differs from the EMPTY singleton, readResolve()
     * must return this same instance instead of substituting EMPTY.
     */
    @Test(timeout = 4000)
    public void test15() throws Throwable {
        Set<String> noIgnoredProperties = new LinkedHashSet<String>();

        JsonIgnoreProperties.Value value = JsonIgnoreProperties.Value.construct(
                noIgnoredProperties,
                /* ignoreUnknown */ true,
                /* allowGetters  */ true,
                /* allowSetters  */ false,
                /* merge         */ true);

        // readResolve() is called during JDK deserialization; for a non-empty
        // Value it must return the instance itself with all flags intact.
        JsonIgnoreProperties.Value resolvedValue = (JsonIgnoreProperties.Value) value.readResolve();

        assertTrue("ignoreUnknown should be true as constructed",  resolvedValue.getIgnoreUnknown());
        assertTrue("allowGetters should be true as constructed",   resolvedValue.getAllowGetters());
        assertFalse("allowSetters should be false as constructed", resolvedValue.getAllowSetters());
        assertTrue("merge should be true as constructed",          resolvedValue.getMerge());
    }
}
