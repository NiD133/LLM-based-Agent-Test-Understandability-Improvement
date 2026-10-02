package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

/**
 * Tests that calling withoutFeature() on a default JsonFormat.Value produces a new Value
 * that is not equal to the original, because one explicitly disables the feature while
 * the other leaves it unset.
 */
@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test45 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_withoutFeature_producesValueNotEqualToOriginal() throws Throwable {
        // A default Value has no features explicitly enabled or disabled
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // withoutFeature() explicitly marks WRITE_SORTED_MAP_ENTRIES as disabled,
        // making the result differ from the original (which has it unset)
        JsonFormat.Value valueWithFeatureDisabled =
                defaultValue.withoutFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES);

        // The two values are not equal: one has an explicit "disabled" flag, the other does not
        assertFalse(valueWithFeatureDisabled.equals(defaultValue));
        assertFalse(defaultValue.equals(valueWithFeatureDisabled));

        // Neither value carries a non-default radix
        assertFalse(defaultValue.hasNonDefaultRadix());
        assertFalse(valueWithFeatureDisabled.hasNonDefaultRadix());
    }
}
