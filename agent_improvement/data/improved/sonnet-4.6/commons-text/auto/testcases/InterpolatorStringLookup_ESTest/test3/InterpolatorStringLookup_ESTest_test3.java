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

    /**
     * Verifies that when InterpolatorStringLookup is used as a Function via compose(),
     * and the pre-composed function returns null, the overall composed function also returns null.
     *
     * compose(f) builds: input -> interpolator.apply(f.apply(input))
     * Since f always returns null, interpolator.apply(null) is called, which returns null.
     */
    @Test(timeout = 4000)
    public void test3() throws Throwable {
        // Arrange: create an interpolator backed by the XmlDecoder lookup as its default
        XmlDecoderStringLookup xmlDecoderLookup = XmlDecoderStringLookup.INSTANCE;
        InterpolatorStringLookup interpolator = new InterpolatorStringLookup(xmlDecoderLookup);

        // Arrange: a mock pre-function that returns null for any input
        Function<Object, String> nullReturningFunction = (Function<Object, String>) mock(Function.class, new ViolatedAssumptionAnswer());
        doReturn((Object) null).when(nullReturningFunction).apply(any());

        // Act: compose the interpolator with the null-returning function, then apply to a key
        // compose(f) means: composedResult = interpolator.apply(nullReturningFunction.apply(input))
        Function<Object, String> composedFunction = interpolator.compose((Function<? super Object, ? extends String>) nullReturningFunction);
        String result = composedFunction.apply("XML_DECODER");

        // Assert: because nullReturningFunction returns null, interpolator.lookup(null) returns null
        assertNull(result);
    }
}
