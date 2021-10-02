package com.cnft.notifier.lib.service;

import com.cnft.notifier.lib.model.Asset;
import com.cnft.notifier.lib.model.TagSpecification;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MarketplaceScannerCnftioServiceTest {

    @Test
    public void testScanNewAssets(){
        String apiDummyResponseString = "";
        try {
            apiDummyResponseString = new String(
                    Files.readAllBytes(Paths.get("src/test/resources/cnftio_dummy_api_response.json")),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            fail("IO exception raised while loading api dummy response file");
        }

        MarketplaceScannerCnftioService scannerService = new MarketplaceScannerCnftioService();
        List<Asset> assets = scannerService.fetchNewAssets(apiDummyResponseString);

        TagSpecification expectedTags = new TagSpecification();
        expectedTags.put("text", new ArrayList<String>(Collections.singletonList("better than the blue ones")));
        expectedTags.put("effect", new ArrayList<String>(Collections.singletonList("40% chance to force skip turn")));
        expectedTags.put("serial", new ArrayList<String>(Collections.singletonList("00000048")));
        expectedTags.put("edition", new ArrayList<String>(Collections.singletonList("first edition")));

        Asset expectedAsset = new Asset(
                "jetchicken",
                "red ball",
                "0fe7b9c1abbf139414d8e138721a85dd8d6e24ee7dc0d895587b4f576a633030303030303438",
                expectedTags,
                (float)8.0,
                Instant.parse("2021-09-06T12:12:06.527Z"),
                "https://cnft.io/token.php?id=6136059682dd9379ff0b203c"
        );

        assertEquals(expectedAsset, assets.get(0));
    }

}
