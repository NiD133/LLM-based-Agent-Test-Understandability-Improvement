package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.Locale;
import java.util.function.ToIntFunction;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class EnumUtils_ESTest_test04 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link EnumUtils#getFirstEnum} returns the first enum constant whose
     * int value (computed by the supplied {@code ToIntFunction}) matches the requested value.
     *
     * <p>The function is stubbed to return {@code 3} for every {@code FilteringMode}, so every
     * constant matches the requested value of {@code 3}. {@code getFirstEnum} therefore returns
     * the first declared constant ({@code AUTOSELECT_FILTERING}) rather than the supplied default.</p>
     */
    @Test(timeout = 4000)
    public void getFirstEnumReturnsFirstMatchingConstant() throws Throwable {
        Class<Locale.FilteringMode> enumClass = Locale.FilteringMode.class;

        // Every constant maps to the int value 3, so the very first constant matches.
        int matchingValue = 3;
        ToIntFunction<Locale.FilteringMode> toIntFunction =
                (ToIntFunction<Locale.FilteringMode>) mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        doReturn(matchingValue).when(toIntFunction).applyAsInt(any(Locale.FilteringMode.class));

        // Default that should NOT be returned because a match exists.
        Locale.FilteringMode defaultEnum = Locale.FilteringMode.IGNORE_EXTENDED_RANGES;

        Locale.FilteringMode result =
                EnumUtils.getFirstEnum(enumClass, matchingValue, toIntFunction, defaultEnum);

        assertEquals(Locale.FilteringMode.AUTOSELECT_FILTERING, result);
    }
}
