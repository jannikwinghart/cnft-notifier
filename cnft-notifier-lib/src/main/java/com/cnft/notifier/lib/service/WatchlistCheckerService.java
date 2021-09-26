package com.cnft.notifier.lib.service;

import com.cnft.notifier.lib.model.Asset;
import com.cnft.notifier.lib.model.AssetSpecification;
import com.cnft.notifier.lib.model.Watchlist;

import java.util.*;

public class WatchlistCheckerService {
    private Map<AssetSpecification, List<Watchlist>> watchedAssetSpecifications;

    public WatchlistCheckerService(List<Watchlist> watchedWatchlists) {
        watchedAssetSpecifications = new HashMap<AssetSpecification, List<Watchlist>>();

        for(Watchlist watchlist: watchedWatchlists){
            for(AssetSpecification assetSpecification: watchlist.getAssetSpecifications()){
                watchedAssetSpecifications.putIfAbsent(assetSpecification, new ArrayList<Watchlist>());
                watchedAssetSpecifications.get(assetSpecification).add(watchlist);
            }
        }
    }

    public List<AssetSpecification> checkTriggeredAssetSpecifications(List<Asset> newAssets){
        List<AssetSpecification> triggeredAssetSpecifications = new ArrayList<AssetSpecification>();

        for(AssetSpecification assetSpecification: this.watchedAssetSpecifications.keySet()){
            if(assetSpecification.matchAssets(newAssets)){
                triggeredAssetSpecifications.add(assetSpecification);
            }
        }

        return triggeredAssetSpecifications;
    }

    public List<Watchlist> checkTriggeredWatchlists(List<Asset> newAssets){
        List<AssetSpecification> triggeredAssetSpecifications = checkTriggeredAssetSpecifications(newAssets);

        List<Watchlist> triggeredWatchlists = new ArrayList<Watchlist>();
        for(AssetSpecification triggeredAssetSpecification: triggeredAssetSpecifications){
            triggeredWatchlists.addAll(watchedAssetSpecifications.get(triggeredAssetSpecification));
        }

        return triggeredWatchlists;
    }

}
