package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link PosixParser}'s handling of ambiguous partial long options (test case 4).
 *
 * <p>{@link PosixParser} does not support partial long option matching with ambiguity
 * detection; that capability is only available in {@link DefaultParser}. Consequently,
 * the inherited {@code testAmbiguousPartialLongOption4} from {@link AbstractParserTestCase}
 * is explicitly disabled for this parser implementation, serving as a documented placeholder
 * for the known limitation.</p>
 */
public class PosixParserTest_testAmbiguousPartialLongOption4 extends AbstractParserTestCase {

    /**
     * Initialises the parser under test with a {@link PosixParser} instance.
     *
     * <p>{@code @SuppressWarnings("deprecation")} is required because {@link PosixParser}
     * is marked deprecated since commons-cli 1.3 in favour of {@link DefaultParser}.</p>
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Placeholder that documents {@link PosixParser}'s inability to detect ambiguity when
     * a partial long-option prefix matches more than one defined option.
     *
     * <p>The test is disabled because {@link PosixParser} does not implement the partial
     * long-option resolution logic required by this scenario; the base-class body is
     * intentionally left empty here.</p>
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testAmbiguousPartialLongOption4() throws Exception {
    }
}
