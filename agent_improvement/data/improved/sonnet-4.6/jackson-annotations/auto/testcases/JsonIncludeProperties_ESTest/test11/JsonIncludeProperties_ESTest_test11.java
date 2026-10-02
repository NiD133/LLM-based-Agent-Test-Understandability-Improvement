package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.LinkedHashSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonIncludeProperties_ESTest_test11 extends JsonIncludeProperties_ESTest_scaffolding {

    /**
     * Tests that {@code withOverrides()} returns a new, distinct {@code Value} whose
     * included-property set is the intersection of the base and the override sets.
     *
     * <p>Setup:
     * <ul>
     *   <li>{@code overrideValue}  — includes {@code {"$h]54GG#Ke5AZNb`7r"}}, ordered=null</li>
     *   <li>{@code baseValue}      — built from a mock annotation whose {@code value()} returns a
     *       one-element null array (yielding included={null}) and {@code order()} returns
     *       {@code OptBoolean.DEFAULT} (yielding ordered=null)</li>
     *   <li>{@code mergedValue}    — intersection of {null} and {"$h]54GG#Ke5AZNb`7r"} = {} (empty)</li>
     * </ul>
     *
     * <p>Because the two included sets are disjoint, the merged result is empty and therefore
     * unequal to both the base and the override {@code Value}.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Override Value: includes exactly one specific property name
        LinkedHashSet<String> propertiesInOverride = new LinkedHashSet<String>();
        propertiesInOverride.add("$h]54GG#Ke5AZNb`7r");
        JsonIncludeProperties.Value overrideValue =
                new JsonIncludeProperties.Value(propertiesInOverride, (Boolean) null);

        // Base Value built from a mock annotation:
        //   value()  → one-element null array  ⟹  included set = {null}
        //   order()  → OptBoolean.DEFAULT       ⟹  ordered = null
        OptBoolean defaultOrder = OptBoolean.DEFAULT;
        String[] valueWithOneNullEntry = new String[1];
        JsonIncludeProperties mockAnnotation = mock(JsonIncludeProperties.class, CALLS_REAL_METHODS);
        doReturn(defaultOrder).when(mockAnnotation).order();
        doReturn(valueWithOneNullEntry).when(mockAnnotation).value();
        JsonIncludeProperties.Value baseValue = JsonIncludeProperties.Value.from(mockAnnotation);

        // withOverrides() computes the intersection: {null} ∩ {"$h]54GG#Ke5AZNb`7r"} = {} (empty)
        JsonIncludeProperties.Value mergedValue = baseValue.withOverrides(overrideValue);

        // The merged result is a newly created, empty-included Value —
        // it must differ from both inputs by value and by reference
        assertFalse(mergedValue.equals((Object) overrideValue));
        assertNotSame(mergedValue, overrideValue);
        assertFalse(mergedValue.equals((Object) baseValue));
        assertNotSame(mergedValue, baseValue);
    }
}
