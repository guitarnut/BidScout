package com.iab.openrtb.request;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.List;

/**
 * This object describes the content in which the impression will appear, which
 * may be syndicated or non- syndicated content. This object may be useful when
 * syndicated content contains impressions and does not necessarily match the
 * publisher’s general content. The exchange might or might not have knowledge
 * of the page where the content is running, as a result of the syndication
 * method. For example might be a video impression embedded in an iframe on an
 * unknown web property or device.
 */


public class Content {

    /** ID uniquely identifying the content. */
    String id;

    /** Episode number. */
    Integer episode;

    /**
     * Content title.
     * <p>Video Examples: “Search Committee” (television), “A New Hope” (movie),
     * or “Endgame” (made for web).
     * <p>Non-Video Example: “Why an Antarctic Glacier Is Melting So Quickly”
     * (Time magazine article).
     */
    String title;

    /**
     * Content series.
     * <p>Video Examples: “The Office” (television), “Star Wars” (movie),
     * or “Arby ‘N’ The Chief” (made for web).
     * <p>Non-Video Example: “Ecocentric” (Time Magazine blog).
     */
    String series;

    /** Content season (e.g., “Season 3”). */
    String season;

    /** Artist credited with the content. */

    String artist;

    /** Genre that best describes the content (e.g., rock, pop, etc). */
    String genre;

    /** Album to which the content belongs; typically for audio. */
    String album;

    /** International Standard Recording Code conforming to ISO- 3901. */
    String isrc;

    /** Details about the content Producer (Section 3.2.17). */
    Producer producer;

    /** URL of the content, for buy-side contextualization or review. */
    String url;

    /**
     * Array of IAB content categories that describe the content producer.
     * Refer to List 5.1.
     */
    List<String> cat;

    /** Production quality. Refer to List 5.13. */
    Integer prodq;

    /** Type of content (game, video, text, etc.). Refer to List 5.18. */
    Integer context;

    /** Content rating (e.g., MPAA). */
    String contentrating;

    /** User rating of the content (e.g., number of stars, likes, etc.). */
    String userrating;

    /** Media rating per IQG guidelines. Refer to List 5.19. */
    Integer qagmediarating;

    /** Comma separated list of keywords describing the content. */
    String keywords;

    /** 0 = not live, 1 = content is live (e.g., stream, live blog). */
    Integer livestream;

    /** 0 = indirect, 1 = direct. */
    Integer sourcerelationship;

    /** Length of content in seconds; appropriate for video or audio. */
    Integer len;

    /** Content language using ISO-639-1-alpha-2. */
    String language;

    /**
     * Indicator of whether or not the content is embeddable (e.g., an
     * embeddable video player), where 0 = no, 1 = yes.
     */
    Integer embeddable;

    /**
     * Additional content data. Each Data object (Section 3.2.21) represents a
     * different data source.
     */
    List<Data> data;

    /** Placeholder for exchange-specific extensions to OpenRTB. */
    ObjectNode ext;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getIsrc() {
        return isrc;
    }

    public void setIsrc(String isrc) {
        this.isrc = isrc;
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getCat() {
        return cat;
    }

    public void setCat(List<String> cat) {
        this.cat = cat;
    }

    public Integer getProdq() {
        return prodq;
    }

    public void setProdq(Integer prodq) {
        this.prodq = prodq;
    }

    public Integer getContext() {
        return context;
    }

    public void setContext(Integer context) {
        this.context = context;
    }

    public String getContentrating() {
        return contentrating;
    }

    public void setContentrating(String contentrating) {
        this.contentrating = contentrating;
    }

    public String getUserrating() {
        return userrating;
    }

    public void setUserrating(String userrating) {
        this.userrating = userrating;
    }

    public Integer getQagmediarating() {
        return qagmediarating;
    }

    public void setQagmediarating(Integer qagmediarating) {
        this.qagmediarating = qagmediarating;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getLivestream() {
        return livestream;
    }

    public void setLivestream(Integer livestream) {
        this.livestream = livestream;
    }

    public Integer getSourcerelationship() {
        return sourcerelationship;
    }

    public void setSourcerelationship(Integer sourcerelationship) {
        this.sourcerelationship = sourcerelationship;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getEmbeddable() {
        return embeddable;
    }

    public void setEmbeddable(Integer embeddable) {
        this.embeddable = embeddable;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
