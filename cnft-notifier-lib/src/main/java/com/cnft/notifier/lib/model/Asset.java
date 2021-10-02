package com.cnft.notifier.lib.model;

import lombok.Getter;

import java.time.Instant;
import java.util.Objects;

public class Asset {
    @Getter
    private String project;
    @Getter
    private String name;
    @Getter
    private String itemId;
    @Getter
    private TagSpecification tagSpecification;
    @Getter
    private float price;
    @Getter
    private Instant dateListed;
    @Getter
    private String listingUrl;

    public Asset(String project, String name, String itemId, TagSpecification tagSpecification, float price, Instant dateListed, String listingUrl) {
        this.project = project;
        this.name = name;
        this.itemId = itemId;
        this.tagSpecification = tagSpecification;
        this.price = price;
        this.dateListed = dateListed;
        this.listingUrl = listingUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return Float.compare(asset.getPrice(), getPrice()) == 0 && Objects.equals(getProject(), asset.getProject()) && Objects.equals(getName(), asset.getName()) && Objects.equals(getItemId(), asset.getItemId()) && Objects.equals(getTagSpecification(), asset.getTagSpecification()) && Objects.equals(getDateListed(), asset.getDateListed()) && Objects.equals(getListingUrl(), asset.getListingUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProject(), getName(), getItemId(), getTagSpecification(), getPrice(), getDateListed(), getListingUrl());
    }

    @Override
    public String toString() {
        return "Asset{" +
                "project='" + project + '\'' +
                ", name='" + name + '\'' +
                ", itemId='" + itemId + '\'' +
                ", tagSpecification=" + tagSpecification +
                ", price=" + price +
                ", dateListed=" + dateListed +
                ", listingUrl='" + listingUrl + '\'' +
                '}';
    }
}
