package com.xyy.subway.game2d.ar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Target {
    private String targetId;
    private String allowSimilar;
    private Integer detectableDistinctivenes;
    private Integer detectableFeatureCount;
    private Integer trackableDistinctiveness;
    private Integer detectableFeatureDistribution;
    private Integer trackableFeatureCount;
    private Integer detectableRate;
    private Integer trackableFeatureDistribution;
    private Integer trackablePatchContrast;
    private Integer trackablePatchAmbiguity;
    private Integer trackableRate;
    private String type;
    private String trackingImage;
    private String date;
    private String size;
    private String meta;
    private String grade;
    private String name;
    private String appKey;
    private String active;
    private long modified;

    public Target() {
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTrackingImage() {
        return trackingImage;
    }

    public void setTrackingImage(String trackingImage) {
        this.trackingImage = trackingImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public long getModified() {
        return modified;
    }

    public void setModified(long modified) {
        this.modified = modified;
    }
}
