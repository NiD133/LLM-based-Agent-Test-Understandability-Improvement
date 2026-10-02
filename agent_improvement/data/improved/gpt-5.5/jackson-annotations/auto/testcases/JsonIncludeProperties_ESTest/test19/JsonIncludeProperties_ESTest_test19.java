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
public class JsonIncludeProperties_ESTest_test19 extends JsonIncludeProperties_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test19() throws Throwable {
        LinkedHashSet<String> includedPropertyNames = new LinkedHashSet<String>();
        Boolean orderProperties = new Boolean("TRUE");
        JsonIncludeProperties.Value includePropertiesValue =
                new JsonIncludeProperties.Value(includedPropertyNames, orderProperties);

        boolean setContainsValueObject = includedPropertyNames.contains(includePropertiesValue);

        assertFalse(setContainsValueObject);
    }
}
