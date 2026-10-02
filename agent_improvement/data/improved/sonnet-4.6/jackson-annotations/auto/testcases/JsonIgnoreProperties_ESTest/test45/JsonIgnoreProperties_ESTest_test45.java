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
public class JsonIgnoreProperties_ESTest_test45 extends JsonIgnoreProperties_ESTest_scaffolding {

    /**
     * Verifies that calling withoutIgnored() on a Value that already has no ignored
     * properties returns the exact same instance, confirming the identity-preserving
     * optimization (no unnecessary object allocation when nothing changes).
     */
    @Test(timeout = 4000)
    public void test45() throws Throwable {
        JsonIgnoreProperties.Value emptyValue = JsonIgnoreProperties.Value.empty();
        JsonIgnoreProperties.Value valueAfterClearingIgnored = emptyValue.withoutIgnored();

        // When the Value already has no ignored properties, withoutIgnored() should
        // return the same instance rather than creating a new equivalent object.
        assertSame(valueAfterClearingIgnored, emptyValue);
    }
}
