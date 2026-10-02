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
public class EnumUtils_ESTest_test05 extends EnumUtils_ESTest_scaffolding {

    /**
     * Verifies that {@link EnumUtils#getFirstEnum(Class, int, ToIntFunction, Enum)}
     * returns the supplied default value when the enum class is {@code null}.
     *
     * <p>With a {@code null} enum class there is nothing to search, so the lookup
     * short-circuits and the default enum is returned unchanged. The int value and
     * the {@code ToIntFunction} are never consulted in this case.</p>
     */
    @Test(timeout = 4000)
    public void getFirstEnumReturnsDefaultWhenEnumClassIsNull() throws Throwable {
        Class<Locale.FilteringMode> nullEnumClass = null;
        ToIntFunction<Locale.FilteringMode> unusedToIntFunction =
                mock(ToIntFunction.class, new ViolatedAssumptionAnswer());
        Locale.FilteringMode defaultEnum = Locale.FilteringMode.EXTENDED_FILTERING;

        Locale.FilteringMode result =
                EnumUtils.getFirstEnum(nullEnumClass, 649, unusedToIntFunction, defaultEnum);

        assertSame(defaultEnum, result);
    }
}
