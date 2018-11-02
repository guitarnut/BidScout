package com.iab.openrtb.request;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * This object should be included if the ad supported content is a website as
 * opposed to a non-browser application. A bid request must not contain both a
 * {@link Site} and an {@link App} object. At a minimum, it is useful to provide
 * a site ID or page URL, but this is not strictly required.
 */


public class Site {

    /** Exchange-specific site ID. (recommended) */
    String id;

    /** Site name (may be aliased at the publisher’s request). */
    String name;

    /** Domain of the site (e.g., “mysite.foo.com”). */
    String domain;

    /** Array of IAB content categories of the site. Refer to List 5.1. */
    List<String> cat;

    /**
     * Array of IAB content categories that describe the current section of the
     * site. Refer to List 5.1.
     */
    List<String> sectioncat;

    /**
     * Array of IAB content categories that describe the current page or view of
     * the site. Refer to List 5.1.
     */
    List<String> pagecat;

    /** URL of the page where the impression will be shown. */
    String page;

    /** Referrer URL that caused navigation to the current page. */
    String ref;

    /** Search string that caused navigation to the current page. */
    String search;

    /**
     * Indicates if the site has been programmed to optimize layout when viewed
     * on mobile devices, where 0 = no, 1 = yes.
     */
    Integer mobile;

    /** Indicates if the site has a privacy policy, where 0 = no, 1 = yes. */
    Integer privacypolicy;

    /** Details about the Publisher (Section 3.2.15) of the site. */
    Publisher publisher;

    /** Details about the Content (Section 3.2.16) within the site. */
    Content content;

    /** Comma separated list of keywords about the site. */
    String keywords;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
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

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public Integer getPrivacypolicy() {
        return privacypolicy;
    }

    public void setPrivacypolicy(Integer privacypolicy) {
        this.privacypolicy = privacypolicy;
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

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
