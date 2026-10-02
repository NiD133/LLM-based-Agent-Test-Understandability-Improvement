package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonTypeInfo_ESTest_test02 extends JsonTypeInfo_ESTest_scaffolding {

    /**
     * Verifies that {@link JsonTypeInfo.Value#withPropertyName(String)} produces a
     * distinct Value: the derived Value (with a different property name) is not equal
     * to the original EMPTY Value, in either direction. Also confirms that deriving a
     * new property name leaves the unrelated "idVisible" flag at its default of false.
     */
    @Test(timeout = 4000)
    public void withDifferentPropertyName_isNotEqualToOriginal_andKeepsIdVisibleFalse() throws Throwable {
        JsonTypeInfo.Value emptyValue = JsonTypeInfo.Value.EMPTY;
        JsonTypeInfo.Value renamedValue = emptyValue.withPropertyName(
                "com.fasterxml.jackson.annotation.JsonTypeInfo$Id");

        // Differing property names make the two Values unequal, symmetrically.
        assertFalse(emptyValue.equals(renamedValue));
        assertFalse(renamedValue.equals((Object) emptyValue));

        // Changing only the property name leaves idVisible at its default (false).
        assertFalse(renamedValue.getIdVisible());
    }
}
