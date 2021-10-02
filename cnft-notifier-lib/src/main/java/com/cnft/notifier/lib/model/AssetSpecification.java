package com.cnft.notifier.lib.model;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

public class AssetSpecification {
    @Getter
    private String project;
    @Getter
    private TagSpecification tagSpecification;
    @Getter
    private List<Asset> matchedAssets;

    public AssetSpecification(String project, TagSpecification tagSpecification) {
        this.project = project;
        this.tagSpecification = tagSpecification;
        this.matchedAssets = new ArrayList<Asset>();
    }

    public boolean isMatchingAsset(Asset asset){
        for(String tagName: tagSpecification.keySet()){
            for(String tagValue: tagSpecification.get(tagName)){
                if(!asset.getTagSpecification().containsKey(tagName) || !asset.getTagSpecification().get(tagName).contains(tagValue)){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean matchAssets(List<Asset> assets){
        boolean newAssetsAdded = false;
        for(Asset asset: assets){
            if(this.isMatchingAsset(asset)){
                this.matchedAssets.add(asset);
                newAssetsAdded = true;
            }
        }

        return newAssetsAdded;
    }

}
