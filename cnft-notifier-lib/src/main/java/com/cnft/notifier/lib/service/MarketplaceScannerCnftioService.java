package com.cnft.notifier.lib.service;

import com.cnft.notifier.lib.model.Asset;
import com.cnft.notifier.lib.model.TagSpecification;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


public class MarketplaceScannerCnftioService extends MarketplaceScannerService{
    public MarketplaceScannerCnftioService() {
        super(120);
    }

    private String callMarketplaceApi(int page) throws IOException {
        Map<String, Object> postParameters = new HashMap<String, Object>();
        postParameters.put("sort", "date");
        postParameters.put("order", "desc");
        postParameters.put("sold", false);
        postParameters.put("verified", true);
        postParameters.put("page", page);

        ObjectMapper jsonMapper = new ObjectMapper();
        String postParametersJsonString = jsonMapper.writeValueAsString(postParameters);

        byte[] postParametersBytes = postParametersJsonString.getBytes(StandardCharsets.UTF_8);

        URL url = new URL("https://api.cnft.io/market/listings");
        HttpURLConnection httpConn = (HttpsURLConnection)url.openConnection();

        httpConn.setDoOutput(true);
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Content-Type", "application/json");
        httpConn.setRequestProperty("Content-Length", Integer.toString(postParametersBytes.length));
        httpConn.setRequestProperty("charset", "utf-8");
        httpConn.setUseCaches(false);

        httpConn.getOutputStream().write(postParametersBytes);

        System.out.println("Response code:" + httpConn.getResponseCode());
        System.out.println("Response message:" + httpConn.getResponseMessage());

        // todo: handle response codes better
        if(httpConn.getErrorStream() != null){
            System.out.println(new BufferedReader(new InputStreamReader(httpConn.getErrorStream())).lines().collect(Collectors.joining("\n")));
            throw new IOException("HTTP connection failed");
        }

        String responseString = new BufferedReader(new InputStreamReader(httpConn.getInputStream())).lines().collect(Collectors.joining("\n"));

        System.out.println(responseString);

        return responseString;
    }

    public List<Asset> fetchNewAssets(String apiResponse) {
        ObjectMapper jsonMapper = new ObjectMapper();
        List<Asset> assets = new ArrayList<>();

        try {
            JsonNode node = jsonMapper.readTree(apiResponse);

            int foundAssetsNum = node.path("found").asInt();
            JsonNode assetJsonNodes = node.path("assets");

            for (JsonNode assetJsonNode : assetJsonNodes) {
                String project = assetJsonNode.path("verified").path("project").asText();
                String name = assetJsonNode.path("metadata").path("name").asText();
                String itemId = assetJsonNode.path("unit").asText();

                TagSpecification tagSpecification = new TagSpecification();

                for(JsonNode tagNode: assetJsonNode.path("metadata").path("tags")) {
                    JsonNode attributesNode = tagNode.path("attributes");

                    Iterator<String> tagNames = attributesNode.fieldNames();
                    while (tagNames.hasNext()) {
                        String tagName = tagNames.next();
                        JsonNode tagAttributeNodes = attributesNode.get(tagName);
                        List<String> tagValues = new ArrayList<String>() {
                        };
                        if (tagAttributeNodes.isArray()) {
                            for (JsonNode tagAttributeNode : tagAttributeNodes) {
                                tagValues.add(tagAttributeNode.textValue());
                            }
                        } else {
                            tagValues.add(tagAttributeNodes.textValue());
                        }

                        tagSpecification.put(tagName, tagValues);
                    }
                }

                float price = (float) assetJsonNode.path("price").asDouble() / 1000000;
                Instant dateListed = Instant.ofEpochMilli(assetJsonNode.path("dateListed").longValue());
                String listingUrl = "https://cnft.io/token.php?id=" + assetJsonNode.path("id").asText();

                Asset asset = new Asset(
                        project,
                        name,
                        itemId,
                        tagSpecification,
                        price,
                        dateListed,
                        listingUrl
                );

                assets.add(asset);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return assets;
    }

    @Override
    public List<Asset> scanNewAssets() {
        String apiResponse;

        try {
            apiResponse = callMarketplaceApi(0);
        } catch (IOException e) {
            e.printStackTrace();
            apiResponse = "{}";
        }

        List<Asset> assets = fetchNewAssets(apiResponse);

        setLastScanTime(Instant.now());

        return assets;
    }
}
