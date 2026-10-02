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
public class InterpolatorStringLookup_ESTest_test3 extends InterpolatorStringLookup_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        XmlDecoderStringLookup xmlDecoderStringLookup0 = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolatorStringLookup0 = new InterpolatorStringLookup(xmlDecoderStringLookup0);
        Function<Object, String> function0 = (Function<Object, String>) mock(Function.class, new ViolatedAssumptionAnswer());
        doReturn((Object) null).when(function0).apply(any());
        Function<Object, String> function1 = interpolatorStringLookup0.compose((Function<? super Object, ? extends String>) function0);
        String string0 = function1.apply("XML_DECODER");
        assertNull(string0);
    }
}
