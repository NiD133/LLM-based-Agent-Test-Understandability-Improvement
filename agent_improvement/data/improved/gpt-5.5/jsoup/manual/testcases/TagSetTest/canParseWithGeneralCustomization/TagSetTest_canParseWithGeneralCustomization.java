package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagSetTest_canParseWithGeneralCustomization {
    private static final String INPUT_HTML = "<custom-data />Bar <script />Text";
    private static final String EXPECTED_BODY_HTML = "<custom-data></custom-data>Bar\n<script>Text</script>";

    @Test
    void canParseWithGeneralCustomization() {
        Parser parser = Parser.htmlParser();
        parser.tagSet().onNewTag(tag -> {
            if (!tag.isKnownTag()) {
                tag.set(Tag.SelfClose);
            }
        });

        Document doc = Jsoup.parse(INPUT_HTML, parser);

        assertEquals(EXPECTED_BODY_HTML, doc.body().html());
    }
}
