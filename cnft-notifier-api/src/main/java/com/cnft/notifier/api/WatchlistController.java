package com.cnft.notifier.api;

import com.cnft.notifier.lib.watchlist.Watchlist;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class WatchlistController {

    @GetMapping("watchlist/")
    public Watchlist getWatchlist(@RequestParam int userId, @RequestParam int watchlistId){
        System.out.println("userId" + userId);
        System.out.println("watchlistId" + watchlistId);

        return new Watchlist();
    }
}
