package com.cnft.notifier.lib.service;

import com.cnft.notifier.lib.model.Asset;
import com.cnft.notifier.lib.model.AssetSpecification;
import com.cnft.notifier.lib.model.TagSpecification;
import com.cnft.notifier.lib.model.Watchlist;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScanAndNotifyIntegrationTest {

    @Test
    void testScanAndNotifyUsecase(){
		/*
		Integration Test to check the whole process from scanning a marketplace until sending of notifications
		 */

        // generate Watchlist
        TagSpecification tagSpecificationCryptoDino = new TagSpecification();
        tagSpecificationCryptoDino.put("Species", "Triceratops");
        AssetSpecification assetSpecificationCryptoDino = new AssetSpecification("CryptoDino", tagSpecificationCryptoDino);
        List<AssetSpecification> assetSpecificationsWatchlist = new ArrayList<AssetSpecification>();
        assetSpecificationsWatchlist.add(assetSpecificationCryptoDino);
        Watchlist watchlist = new Watchlist(0, assetSpecificationsWatchlist, true);

        System.out.println(watchlist);

        // scan cnft.io
        MarketplaceScannerCnftioService marketplaceScannerCnftioService = new MarketplaceScannerCnftioService();
        List<Asset> newAssets = marketplaceScannerCnftioService.scanNewAssets();

        TagSpecification dummyAssetTagSpecification = new TagSpecification();
        dummyAssetTagSpecification.put("Mint", "9432");
        dummyAssetTagSpecification.put("Species", "Triceratops");
        dummyAssetTagSpecification.put("Teeth", "Hippo");
        Asset dummyAsset = new Asset("CryptoDino", "CryptoDino09432", dummyAssetTagSpecification, 45);
        // todo: mock scanner and remove manual add of asset
        newAssets.add(dummyAsset);

        System.out.println(newAssets);

        // match new assets with watchlist
        List<Watchlist> watchlists = new ArrayList<Watchlist>();
        watchlists.add(watchlist);
        WatchlistCheckerService watchlistCheckerService = new WatchlistCheckerService(watchlists);
        List<Watchlist> triggeredWatchlists = watchlistCheckerService.checkTriggeredWatchlists(newAssets);

        System.out.println(triggeredWatchlists);

        assertEquals(triggeredWatchlists.get(0), watchlist);
        assertEquals(triggeredWatchlists.get(0).getMatchedAssets().get(0), dummyAsset);

        // notify users
        for (Watchlist triggeredWatchlist: triggeredWatchlists) {
            System.out.println(triggeredWatchlists);
            System.out.println(triggeredWatchlist.getMatchedAssets());
        }
        // todo: test if correct user would be notified
    }

}
