package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.commons.cli.DefaultParser.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class DefaultParserTest_testBuilder extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testBuilder() {
        // @formatter:off
        final Builder builder = DefaultParser.builder().setStripLeadingAndTrailingQuotes(false).setAllowPartialMatching(false).setDeprecatedHandler(null);
        // @formatter:on
        parser = builder.build();
        assertEquals(DefaultParser.class, parser.getClass());
        parser = builder.get();
        assertEquals(DefaultParser.class, parser.getClass());
    }
}
