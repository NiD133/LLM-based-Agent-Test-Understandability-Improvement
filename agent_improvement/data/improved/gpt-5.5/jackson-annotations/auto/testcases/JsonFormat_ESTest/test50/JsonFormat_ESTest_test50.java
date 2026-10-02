package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test50 extends JsonFormat_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test50() throws Throwable {
        JsonFormat.Value defaultFormat = new JsonFormat.Value();
        JsonFormat.Feature sortedMapEntries = JsonFormat.Feature.WRITE_SORTED_MAP_ENTRIES;

        JsonFormat.Value formatWithSortedEntriesDisabled = defaultFormat.withoutFeature(sortedMapEntries);
        String actualDescription = formatWithSortedEntriesDisabled.toString();

        String expectedDescription = "JsonFormat.Value(pattern=,shape=ANY,lenient=null,locale=null,timezone=null,features=(enabled=0x0,disabled=0x200),radix=-1)";
        assertEquals(expectedDescription, actualDescription);
    }
}
