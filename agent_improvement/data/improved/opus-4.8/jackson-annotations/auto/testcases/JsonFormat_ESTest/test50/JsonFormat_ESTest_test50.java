package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test50 extends JsonFormat_ESTest_scaffolding {

    /**
     * Disabling a feature on a default (empty) Value should record that feature
     * in the "disabled" bitmask while leaving all other settings at their defaults.
     * WRITE_SORTED_MAP_ENTRIES has ordinal 9, so its mask is 1 << 9 = 0x200.
     */
    @Test(timeout = 4000)
    public void disablingFeatureMarksItInDisabledBitmaskOfToString() throws Throwable {
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        JsonFormat.Value valueWithFeatureDisabled =
                defaultValue.withoutFeature(JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES);

        String description = valueWithFeatureDisabled.toString();

        assertEquals(
                "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=(enabled=0x0,disabled=0x200),radix=-1)",
                description);
    }
}
