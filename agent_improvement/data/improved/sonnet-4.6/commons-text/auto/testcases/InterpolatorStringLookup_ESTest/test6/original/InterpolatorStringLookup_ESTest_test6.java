package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test6 extends InterpolatorStringLookup_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test6() throws Throwable {
        HashMap<String, StringLookup> hashMap0 = new HashMap<String, StringLookup>();
        XmlEncoderStringLookup xmlEncoderStringLookup0 = new XmlEncoderStringLookup();
        InterpolatorStringLookup interpolatorStringLookup0 = new InterpolatorStringLookup(hashMap0, xmlEncoderStringLookup0, false);
        String string0 = interpolatorStringLookup0.toString();
        assertNotNull(string0);
    }
}
