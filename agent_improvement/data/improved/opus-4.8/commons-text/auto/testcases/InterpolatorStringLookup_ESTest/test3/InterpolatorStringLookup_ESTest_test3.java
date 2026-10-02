package org.apache.commons.text.lookup;

import static org.junit.Assert.assertNull;
import static org.evosuite.shaded.org.mockito.Mockito.any;
import static org.evosuite.shaded.org.mockito.Mockito.doReturn;
import static org.evosuite.shaded.org.mockito.Mockito.mock;

import java.util.function.Function;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class InterpolatorStringLookup_ESTest_test3 extends InterpolatorStringLookup_ESTest_scaffolding {

    /**
     * Verifies the behaviour of {@link InterpolatorStringLookup} when used as a
     * {@link Function} via {@link Function#compose(Function)}.
     *
     * <p>The composed function first applies {@code upstreamLookup} (which is stubbed
     * to always return {@code null}) and then feeds that result into the interpolator.
     * Because the interpolator receives a {@code null} key, its lookup short-circuits
     * and also returns {@code null}, so the composed function yields {@code null}.</p>
     */
    @Test(timeout = 4000)
    public void composeWithNullReturningFunctionYieldsNull() throws Throwable {
        // An interpolator whose default lookup is the XML decoder lookup.
        InterpolatorStringLookup interpolatorLookup =
                new InterpolatorStringLookup(XmlDecoderStringLookup.INSTANCE);

        // Upstream function that ignores its input and always returns null.
        Function<Object, String> upstreamLookup = mockFunctionReturningNull();

        // compose => result.apply(x) == interpolatorLookup.apply(upstreamLookup.apply(x))
        Function<Object, String> composedLookup = interpolatorLookup.compose(upstreamLookup);

        String result = composedLookup.apply("XML_DECODER");

        assertNull(result);
    }

    /**
     * Creates a mocked {@link Function} that returns {@code null} for any argument.
     */
    @SuppressWarnings("unchecked")
    private static Function<Object, String> mockFunctionReturningNull() {
        Function<Object, String> function = mock(Function.class, new ViolatedAssumptionAnswer());
        doReturn((Object) null).when(function).apply(any());
        return function;
    }
}
