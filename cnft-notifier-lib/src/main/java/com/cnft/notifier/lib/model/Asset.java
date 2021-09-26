package com.cnft.notifier.lib.model;

import lombok.Getter;

public class Asset {
    //todo: more parameters

    @Getter
    private String project;
    @Getter
    private String itemId;
    @Getter
    private TagSpecification tagSpecification;
    @Getter
    private float price;

    public Asset(String project, String item, TagSpecification tagSpecification, float price) {
        this.project = project;
        this.itemId = item;
        this.tagSpecification = tagSpecification;
        this.price = price;
    }
}
