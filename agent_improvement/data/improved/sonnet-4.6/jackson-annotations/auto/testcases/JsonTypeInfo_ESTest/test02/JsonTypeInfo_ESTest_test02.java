package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test02 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that withPropertyName() produces a new Value that is not equal to the original
     * EMPTY Value (inequality is symmetric), and that the id-visible flag remains false.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Arrange: start from the default empty Value and derive one with a custom property name
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value valueWithCustomPropertyName =
                emptyValue.withPropertyName("com.fasterxml.jackson.annotation.JsonTypeInfo$Id");

        // Act: check equality from emptyValue's perspective
        boolean emptyEqualsCustom = emptyValue.equals(valueWithCustomPropertyName);

        // Assert: inequality is symmetric — neither direction returns true
        assertFalse(valueWithCustomPropertyName.equals((Object) emptyValue));
        assertFalse(emptyEqualsCustom);

        // Assert: the derived value inherits idVisible=false from EMPTY
        assertFalse(valueWithCustomPropertyName.getIdVisible());
    }
}
