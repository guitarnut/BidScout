package com.gnut.bidscout.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DisplayAd implements Ad {
    @Id
    private String id;
    private String owner;
    private String name;
    private String creativeId;
    private List<String> adDomain = new ArrayList<>();
    private List<String> iabCategories = new ArrayList<>();
    private List<Integer> attr = new ArrayList<>();
    private List<Integer> btype = new ArrayList<>();
    private List<String> mimes = new ArrayList<>();
    private String adId;
    private String crid;
    private String creativeUrl;
    private int width;
    private int height;
    private String adm;
    private boolean syncUsers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreativeId() {
        return creativeId;
    }

    public void setCreativeId(String creativeId) {
        this.creativeId = creativeId;
    }

    public List<String> getAdDomain() {
        return adDomain;
    }

    public void setAdDomain(List<String> adDomain) {
        this.adDomain = adDomain;
    }

    public List<String> getIabCategories() {
        return iabCategories;
    }

    public void setIabCategories(List<String> iabCategories) {
        this.iabCategories = iabCategories;
    }

    public List<Integer> getAttr() {
        return attr;
    }

    public void setAttr(List<Integer> attr) {
        this.attr = attr;
    }

    public List<Integer> getBtype() {
        return btype;
    }

    public void setBtype(List<Integer> btype) {
        this.btype = btype;
    }

    public List<String> getMimes() {
        return mimes;
    }

    public void setMimes(List<String> mimes) {
        this.mimes = mimes;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getCrid() {
        return crid;
    }

    public void setCrid(String crid) {
        this.crid = crid;
    }

    public String getCreativeUrl() {
        return creativeUrl;
    }

    public void setCreativeUrl(String creativeUrl) {
        this.creativeUrl = creativeUrl;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public boolean isSyncUsers() {
        return syncUsers;
    }

    public void setSyncUsers(boolean syncUsers) {
        this.syncUsers = syncUsers;
    }
}
