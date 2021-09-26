package com.cnft.notifier.lib.service;

import com.cnft.notifier.lib.model.Asset;
import com.cnft.notifier.lib.model.AssetSpecification;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

public abstract class MarketplaceScannerService {
    @Getter
    @Setter(value = AccessLevel.PROTECTED)
    private Instant lastScanTime;

    @Getter
    private int scanIntervalSeconds;

    public MarketplaceScannerService(int scanIntervalSeconds) {
        this.scanIntervalSeconds = scanIntervalSeconds;
        this.lastScanTime = Instant.now();
    }

    public abstract List<Asset> scanNewAssets();
}
