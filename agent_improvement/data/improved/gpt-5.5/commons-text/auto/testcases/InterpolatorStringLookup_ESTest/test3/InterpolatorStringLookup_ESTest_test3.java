package org.apache.commons.text.lookup;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
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
        final XmlDecoderStringLookup xmlDecoderLookup = XmlDecoderStringLookup.INSTANCE;
        final InterpolatorStringLookup interpolatorLookup = new InterpolatorStringLookup(xmlDecoderLookup);

        @SuppressWarnings("unchecked")
        final Function<Object, String> nullReturningFunction =
                (Function<Object, String>) mock(Function.class, new ViolatedAssumptionAnswer());
        doReturn((Object) null).when(nullReturningFunction).apply(any());

        final Function<Object, String> composedLookup =
                interpolatorLookup.compose((Function<? super Object, ? extends String>) nullReturningFunction);
        final String resolvedValue = composedLookup.apply("XML_DECODER");

        assertNull(resolvedValue);
    }
}
