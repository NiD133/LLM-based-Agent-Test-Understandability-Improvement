package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.commons.cli.DefaultParser.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DefaultParserTest_testBuilder extends AbstractParserTestCase {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
        parser = new DefaultParser();
    }

    @Test
    void testBuilder() {
        final Builder builder = DefaultParser.builder()
                .setStripLeadingAndTrailingQuotes(false)
                .setAllowPartialMatching(false)
                .setDeprecatedHandler(null);

        parser = builder.build();
        assertEquals(DefaultParser.class, parser.getClass());

        parser = builder.get();
        assertEquals(DefaultParser.class, parser.getClass());
    }
}
