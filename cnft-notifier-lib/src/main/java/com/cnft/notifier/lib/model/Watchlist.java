package com.cnft.notifier.lib.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Watchlist {
    @Getter
    private int id;
    @Getter
    private List<AssetSpecification> assetSpecifications;
    @Getter
    private Boolean active;


    public Watchlist(int id, List<AssetSpecification> assetSpecifications, Boolean active) {
        this.id = id;
        this.assetSpecifications = assetSpecifications;
        this.active = active;
    }

    public List<Asset> getMatchedAssets(){
        List<Asset> matchedAssets = new ArrayList<Asset>();

        for(AssetSpecification assetSpecification: assetSpecifications){
            matchedAssets.addAll(assetSpecification.getMatchedAssets());
        }

        return matchedAssets;
    }
}
