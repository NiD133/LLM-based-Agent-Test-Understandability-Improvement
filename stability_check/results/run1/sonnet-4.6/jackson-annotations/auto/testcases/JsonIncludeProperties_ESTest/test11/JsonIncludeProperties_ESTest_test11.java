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
public class JsonIncludeProperties_ESTest_test11 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Build an override Value containing one explicit property name
        LinkedHashSet<String> overridePropertySet = new LinkedHashSet<String>();
        overridePropertySet.add("$h]54GG#Ke5AZNb`7r");
        JsonIncludeProperties.Value overrideValue = new JsonIncludeProperties.Value(overridePropertySet, (Boolean) null);

        // Build a base Value from a mocked annotation whose value() returns a
        // single-element array (containing null) and order() returns DEFAULT
        OptBoolean defaultOrder = OptBoolean.DEFAULT;
        String[] singleNullProperty = new String[1];
        JsonIncludeProperties mockAnnotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(defaultOrder).when(mockAnnotation).order();
        doReturn(singleNullProperty).when(mockAnnotation).value();
        JsonIncludeProperties.Value baseValue = JsonIncludeProperties.Value.from(mockAnnotation);

        // withOverrides intersects the two included-property sets; since null is
        // not present in overridePropertySet the intersection is empty, producing
        // a new Value distinct from both inputs
        JsonIncludeProperties.Value mergedValue = baseValue.withOverrides(overrideValue);

        assertFalse(mergedValue.equals((Object) overrideValue));
        assertNotSame(mergedValue, overrideValue);
        assertFalse(mergedValue.equals((Object) baseValue));
        assertNotSame(mergedValue, baseValue);
    }
}
