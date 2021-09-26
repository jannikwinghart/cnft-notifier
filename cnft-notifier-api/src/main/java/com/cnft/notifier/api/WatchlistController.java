package com.cnft.notifier.api;

import com.cnft.notifier.lib.model.AssetSpecification;
import com.cnft.notifier.lib.model.TagSpecification;
import com.cnft.notifier.lib.model.Watchlist;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class WatchlistController {

    @GetMapping("watchlist")
    public Watchlist getWatchlist(@RequestParam int userId, @RequestParam int watchlistId){
        System.out.println("userId " + userId);
        System.out.println("watchlistId " + watchlistId);

        // todo: load watchlist from db

        TagSpecification tagSpecificationCryptoDino = new TagSpecification();
        tagSpecificationCryptoDino.put("Species", "Triceratops");
        AssetSpecification assetSpecificationCryptoDino = new AssetSpecification("CryptoDino", tagSpecificationCryptoDino);
        List<AssetSpecification> assetSpecificationsWatchlist = new ArrayList<AssetSpecification>();
        assetSpecificationsWatchlist.add(assetSpecificationCryptoDino);

        return new Watchlist(0, assetSpecificationsWatchlist, true);
    }
}
