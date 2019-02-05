package com.iab.openrtb.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
import java.util.Map;

/**
 * This object should be included if the ad supported content is a non-browser
 * application (typically in mobile) as opposed to a website. A bid request must
 * not contain both an {@link App} and a {@link Site} object. At a minimum, it
 * is useful to provide an Application ID or bundle, but this is not strictly required.
 */

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class App {

    /** Exchange-specific app ID. (recommended) */
    String id;

    /** Application name (may be aliased at the publisher’s request). */
    String name;

    /**
     * A platform-specific application identifier intended to be unique to the
     * app and independent of the exchange. On Android, this should be a bundle
     * or package name (e.g., com.foo.mygame). On iOS, it is typically a numeric
     * ID.
     */
    String bundle;

    /** Domain of the app (e.g., “mygame.foo.com”). */
    String domain;

    /** Application store URL for an installed app; for IQG 2.1 compliance. */
    String storeurl;

    /** Array of IAB content categories of the app. Refer to List 5.1. */
    List<String> cat;

    /**
     * Array of IAB content categories that describe the current section of
     * the app. Refer to List 5.1.
     */
    List<String> sectioncat;

    /**
     * Array of IAB content categories that describe the current page or view of
     * the app. Refer to List 5.1.
     */
    List<String> pagecat;

    /** Application version. */
    String ver;

    /** Indicates if the app has a privacy policy, where 0 = no, 1 = yes. */
    Integer privacypolicy;

    /** 0 = app is free, 1 = the app is a paid version. */
    Integer paid;

    /** Details about the Publisher (Section 3.2.15) of the app. */
    Publisher publisher;

    /** Details about the Content (Section 3.2.16) within the app. */
    Content content;

    /** Comma separated list of keywords about the app. */
    List<String> keywords;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    Map<Object, Object> ext;

    public App(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBundle() {
        return bundle;
    }

    public void setBundle(String bundle) {
        this.bundle = bundle;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStoreurl() {
        return storeurl;
    }

    public void setStoreurl(String storeurl) {
        this.storeurl = storeurl;
    }

    public List<String> getCat() {
        return cat;
    }

    public void setCat(List<String> cat) {
        this.cat = cat;
    }

    public List<String> getSectioncat() {
        return sectioncat;
    }

    public void setSectioncat(List<String> sectioncat) {
        this.sectioncat = sectioncat;
    }

    public List<String> getPagecat() {
        return pagecat;
    }

    public void setPagecat(List<String> pagecat) {
        this.pagecat = pagecat;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public Integer getPrivacypolicy() {
        return privacypolicy;
    }

    public void setPrivacypolicy(Integer privacypolicy) {
        this.privacypolicy = privacypolicy;
    }

    public Integer getPaid() {
        return paid;
    }

    public void setPaid(Integer paid) {
        this.paid = paid;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public Map<Object, Object> getExt() {
        return ext;
    }

    public void setExt(Map<Object, Object> ext) {
        this.ext = ext;
    }
}
