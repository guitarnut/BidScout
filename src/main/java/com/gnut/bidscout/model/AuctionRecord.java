package com.gnut.bidscout.model;

import com.iab.openrtb.request.BidRequest;
import com.iab.openrtb.response.BidResponse;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuctionRecord {
    @Id
    private String id;
    private String publisher;
    private String owner;
    private String ip;
    private String userAgent;
    private long requestTimestamp;
    private long responseTimestamp;
    private long impressionTimestamp;
    private String bidRequestId;
    private BidRequest bidRequest;
    private BidResponse bidResponse;
    private String markup;
    private String cookies;
    private String host;
    private String xForwardedFor;
    private String campaign;
    private String creative;
    private Map<String, String> targetingFailures = new HashMap<>();
    private List<String> bidRequestErrors = new ArrayList<>();

    public AuctionRecord(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public long getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(long requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public long getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(long responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public long getImpressionTimestamp() {
        return impressionTimestamp;
    }

    public void setImpressionTimestamp(long impressionTimestamp) {
        this.impressionTimestamp = impressionTimestamp;
    }

    public String getBidRequestId() {
        return bidRequestId;
    }

    public void setBidRequestId(String bidRequestId) {
        this.bidRequestId = bidRequestId;
    }

    public BidRequest getBidRequest() {
        return bidRequest;
    }

    public void setBidRequest(BidRequest bidRequest) {
        this.bidRequest = bidRequest;
    }

    public BidResponse getBidResponse() {
        return bidResponse;
    }

    public void setBidResponse(BidResponse bidResponse) {
        this.bidResponse = bidResponse;
    }

    public String getMarkup() {
        return markup;
    }

    public void setMarkup(String markup) {
        this.markup = markup;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getxForwardedFor() {
        return xForwardedFor;
    }

    public void setxForwardedFor(String xForwardedFor) {
        this.xForwardedFor = xForwardedFor;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getCreative() {
        return creative;
    }

    public void setCreative(String creative) {
        this.creative = creative;
    }

    public List<String> getBidRequestErrors() {
        return bidRequestErrors;
    }

    public void setBidRequestErrors(List<String> bidRequestErrors) {
        this.bidRequestErrors = bidRequestErrors;
    }

    public Map<String, String> getTargetingFailures() {
        return targetingFailures;
    }

    public void setTargetingFailures(Map<String, String> targetingFailures) {
        this.targetingFailures = targetingFailures;
    }
}
