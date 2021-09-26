package com.cnft.notifier.lib.service;

import com.cnft.notifier.lib.model.Asset;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class MarketplaceScannerCnftioService extends MarketplaceScannerService{

    public MarketplaceScannerCnftioService() {
        super(120);
    }

    @Override
    public List<Asset> scanNewAssets() {
        List<Asset> assets = new ArrayList<Asset>();

        // todo: scan cnft.io

        setLastScanTime(Instant.now());

        return assets;
    }
}
