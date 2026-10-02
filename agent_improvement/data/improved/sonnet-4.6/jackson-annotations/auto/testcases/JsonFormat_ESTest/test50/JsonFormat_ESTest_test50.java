package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test50 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test50() throws Throwable {
        // Start with a default Value (no pattern, ANY shape, no locale/timezone, empty features)
        JsonFormat.Value defaultValue = new JsonFormat.Value();

        // Disabling WRITE_SORTED_MAP_ENTRIES (ordinal 9 → bitmask 0x200) moves it to the disabled set
        JsonFormat.Feature writeSortedMapEntries = JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES;
        JsonFormat.Value valueWithFeatureDisabled = defaultValue.withoutFeature(writeSortedMapEntries);

        String representation = valueWithFeatureDisabled.toString();

        assertEquals(
            "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=(enabled=0x0,disabled=0x200),radix=-1)",
            representation
        );
    }
}
