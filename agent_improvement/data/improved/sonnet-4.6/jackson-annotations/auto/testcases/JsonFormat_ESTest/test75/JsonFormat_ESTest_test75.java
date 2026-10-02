package com.fasterxml.jackson.annotation;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.TimeZone;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class JsonFormat_ESTest_test75 extends JsonFormat_ESTest_scaffolding {

    /**
     * withTimeZone(TimeZone) stores the TimeZone object but clears _timezoneStr.
     * Value.equals() compares _timezoneStr (not the TimeZone object), so a Value
     * built via withTimeZone() from an empty Value compares equal to the original
     * empty Value — both have _timezoneStr == null.
     */
    @Test(timeout = 4000)
    public void test75_withTimeZoneDoesNotAffectValueEquality() throws Throwable {
        JsonFormat.Value emptyValue = JsonFormat.Value.empty();
        TimeZone defaultTimeZone = TimeZone.getDefault();

        JsonFormat.Value valueWithTimeZone = emptyValue.withTimeZone(defaultTimeZone);

        assertTrue(valueWithTimeZone.equals((Object) emptyValue));
    }
}
