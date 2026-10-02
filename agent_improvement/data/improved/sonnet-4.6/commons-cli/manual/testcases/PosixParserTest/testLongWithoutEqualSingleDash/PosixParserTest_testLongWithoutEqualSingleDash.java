package org.apache.commons.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Verifies that the {@link PosixParser} variant of the {@code testLongWithoutEqualSingleDash}
 * test scenario is explicitly unsupported.
 *
 * <p>The parent class {@link AbstractParserTestCase} defines a test that exercises long-form
 * options specified with a single dash and no equals sign (e.g. {@code -foo value}).  The
 * {@link PosixParser} does not handle this syntax, so the inherited test is overridden and
 * disabled here to document that limitation and prevent a spurious failure.
 *
 * <p>{@link PosixParser} has been deprecated since commons-cli 1.3 in favour of
 * {@link DefaultParser}, which does support this syntax.
 */
public class PosixParserTest_testLongWithoutEqualSingleDash extends AbstractParserTestCase {

    /**
     * Instantiates a {@link PosixParser} for each test.
     *
     * <p>{@code @SuppressWarnings("deprecation")} is required because {@link PosixParser}
     * itself is marked {@code @Deprecated}; this test class must still exercise it directly.
     */
    @Override
    @SuppressWarnings("deprecation")
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new PosixParser();
    }

    /**
     * Disabled because {@link PosixParser} does not support long options expressed with a
     * single dash and no equals sign (e.g. {@code -foo value}).  The test exists in the parent
     * class but cannot pass against this parser implementation.
     */
    @Override
    @Test
    @Disabled("not supported by the PosixParser")
    void testLongWithoutEqualSingleDash() throws Exception {
    }
}
