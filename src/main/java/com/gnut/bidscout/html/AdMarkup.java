package com.gnut.bidscout.html;

import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AdMarkup {
    private static final String SYNC_URL = "http://app.auctionscout.net/sync/user";
    private static final String IMP_URL ="http://app.auctionscout.net/imp";
    private static final String CLICK_URL = "http://app.auctionscout.net/click";
    private static final String WIDTH_MACRO = "WIDTH";
    private static final String HEIGHT_MACRO = "HEIGHT";
    private static final String IMPRESSION_MACRO = "IMPRESSION";
    private static final String SYNC_MACRO = "SYNC";
    private static final String CLICK_MACRO = "CLICK";
    private static final String ASSET_MACRO = "ASSET";
    private static final String HTML_OPEN = "<div style=\"width:" + WIDTH_MACRO + "px; height:" + HEIGHT_MACRO + "px;\">";
    private static final String CREATIVE_FOR_ALL_SIZES = "<div style=\"border: 1px solid black; background: #CCC; width:" + WIDTH_MACRO + "px; height:" + HEIGHT_MACRO + "px;\">";
    private static final String HTML_CLOSE = "</div>";
    private static final String CLICK_OPEN = "<a href=\"" + CLICK_MACRO + "\" target=\"_blank\">";
    private static final String CLICK_CLOSE = "</a>";
    private static final String IMPRESSION_IMG_HIDDEN = "<img src=\"" + IMPRESSION_MACRO + "\" style=\"width:0; height:0; display:none;\"/>";
    private static final String SYNC_IMG_HIDDEN = "<img src=\"" + SYNC_MACRO + "\" style=\"width:0; height:0; display:none;\"/>";
    private static final String ASSET_IMG = "<img src=\"" + ASSET_MACRO + "\" style=\"width:" + WIDTH_MACRO + "px; height:" + HEIGHT_MACRO + "px;\">";

    public String generateDisplayMarkup(BigDecimal price, String bidRequestId, Campaign campaign, Creative creative) {
        if (!Strings.isNullOrEmpty(creative.getAdm())) {
            return buildCustomAdmCreative(price, bidRequestId, campaign, creative);
        } else if (creative.getW() == 0 && creative.getH() == 0) {
            return buildDefaultCreative(price, bidRequestId, campaign, creative);
        } else {
            return buildCreativeWithCustomAsset(price, bidRequestId, campaign, creative);
        }
    }

    private String buildCustomAdmCreative(BigDecimal price, String bidRequestId, Campaign campaign, Creative creative) {
        final StringBuilder adm = new StringBuilder();

        adm.append(HTML_OPEN)
                .append(creative.getAdm())
                .append(IMPRESSION_IMG_HIDDEN);

        String sync = "";
        if (campaign.isSyncUsers() || creative.isSyncUsers()) {
            adm.append(SYNC_IMG_HIDDEN);
            sync = SYNC_URL;
        }

        adm.append(HTML_CLOSE);

        String imp = buildImpressionPixel(campaign, creative, bidRequestId, price);

        return adm.toString()
                .replace(IMPRESSION_MACRO, imp)
                .replace(SYNC_MACRO, sync)
                .replaceAll(WIDTH_MACRO, String.valueOf(creative.getW()))
                .replaceAll(HEIGHT_MACRO, String.valueOf(creative.getH()));
    }

    private String buildDefaultCreative(BigDecimal price, String bidRequestId, Campaign campaign, Creative creative) {
        final StringBuilder adm = new StringBuilder();

        adm.append(HTML_OPEN)
                .append(CLICK_OPEN)
                .append(CREATIVE_FOR_ALL_SIZES)
                .append(CLICK_CLOSE)
                .append(IMPRESSION_IMG_HIDDEN);

        String sync = "";
        if (campaign.isSyncUsers() || creative.isSyncUsers()) {
            adm.append(SYNC_IMG_HIDDEN);
            sync = SYNC_URL;
        }

        adm.append(HTML_CLOSE);

        String imp = buildImpressionPixel(campaign, creative, bidRequestId, price);
        String click = buildClickPixel(campaign, creative, bidRequestId);

        return adm.toString()
                .replace(IMPRESSION_MACRO, imp)
                .replace(SYNC_MACRO, sync)
                .replace(CLICK_MACRO, click)
                .replaceAll(WIDTH_MACRO, String.valueOf(creative.getW()))
                .replaceAll(HEIGHT_MACRO, String.valueOf(creative.getH()));
    }

    private String buildCreativeWithCustomAsset(BigDecimal price, String bidRequestId, Campaign campaign, Creative creative) {
        final StringBuilder adm = new StringBuilder();

        adm.append(HTML_OPEN)
                .append(CLICK_OPEN)
                .append(ASSET_IMG)
                .append(CLICK_CLOSE)
                .append(IMPRESSION_IMG_HIDDEN);

        String sync = "";
        if (campaign.isSyncUsers() || creative.isSyncUsers()) {
            adm.append(SYNC_IMG_HIDDEN);
            sync = SYNC_URL;
        }

        adm.append(HTML_CLOSE);

        String imp = buildImpressionPixel(campaign, creative, bidRequestId, price);
        String click = buildClickPixel(campaign, creative, bidRequestId);

        return adm.toString()
                .replace(IMPRESSION_MACRO, imp)
                .replace(SYNC_MACRO, sync)
                .replace(CLICK_MACRO, click)
                .replace(ASSET_MACRO, creative.getCreativeUrl())
                .replaceAll(WIDTH_MACRO, String.valueOf(creative.getW()))
                .replaceAll(HEIGHT_MACRO, String.valueOf(creative.getH()));
    }

    private String buildImpressionPixel(Campaign campaign, Creative creative, String bidRequestId, BigDecimal price) {
        StringBuilder imp = new StringBuilder();
        return imp.append(IMP_URL)
                .append("/")
                .append(campaign.getOwner())
                .append("/")
                .append(bidRequestId)
                .append("/")
                .append(campaign.getId())
                .append("/")
                .append(creative.getId())
                .append("/")
                .append(String.valueOf(price))
                .append("/")
                .append("${AUCTION_PRICE}")
                .append("/")
                .append("?cb=")
                .append(System.currentTimeMillis())
                .toString();
    }

    private String buildClickPixel(Campaign campaign, Creative creative, String bidRequestId) {
        StringBuilder click = new StringBuilder();
        return click.append(CLICK_URL)
                .append("/")
                .append(campaign.getOwner())
                .append("/")
                .append(bidRequestId)
                .append("/")
                .append(campaign.getId())
                .append("/")
                .append(creative.getId())
                .append("?cb=")
                .append(System.currentTimeMillis())
                .toString();
    }
}
