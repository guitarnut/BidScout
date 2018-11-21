package com.gnut.bidscout.html;

import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AdMarkup {
    private static final String WIDTH_MACRO = "WIDTH";
    private static final String HEIGHT_MACRO = "HEIGHT";
    private static final String IMPRESSION_MACRO = "IMPRESSION";
    private static final String SYNC_MACRO = "SYNC";
    private static final String CLICK_MACRO = "CLICK";
    private static final String ASSET_MACRO = "ASSET";
    private static final String HTML_OPEN = "<div style=\"width:" + WIDTH_MACRO + "px; height:" + HEIGHT_MACRO + "px;\">";
    private static final String HTML_CLOSE = "</div>";
    private static final String CLICK_OPEN = "<a href=\"" + CLICK_MACRO + "\" target=\"_blank\">";
    private static final String CLICK_CLOSE = "</a>";
    private static final String IMPRESSION_IMG_HIDDEN = "<img src=\"" + IMPRESSION_MACRO + "\" style=\"width:0; height:0; display:none;\"/>";
    private static final String SYNC_IMG_HIDDEN = "<img src=\"" + SYNC_MACRO + "\" style=\"width:0; height:0; display:none;\"/>";
    private static final String ASSET_IMG = "<img src=\"" + ASSET_MACRO + "\" style=\"width:" + WIDTH_MACRO + "px; height:" + HEIGHT_MACRO + "px;\">";

    public String generateMarkup(BigDecimal price, String bidRequestId, Campaign campaign, Creative creative) {
        final StringBuilder adm = new StringBuilder();

        if (Strings.isNullOrEmpty(creative.getAdm())) {
            adm.append(HTML_OPEN)
                    .append(CLICK_OPEN)
                    .append(ASSET_IMG)
                    .append(CLICK_CLOSE)
                    .append(IMPRESSION_IMG_HIDDEN);
        } else {
            adm.append(HTML_OPEN)
                    .append(creative.getAdm())
                    .append(IMPRESSION_IMG_HIDDEN);
        }

        String sync = "";
        if (campaign.isSyncUsers() || creative.isSyncUsers()) {
            adm.append(SYNC_IMG_HIDDEN);
            sync = "http://localhost:8080/sync/user";
        }

        adm.append(HTML_CLOSE);

        StringBuilder imp = new StringBuilder();
        imp.append("http://localhost:8080/imp")
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

        StringBuilder click = new StringBuilder();
        click.append("http://localhost:8080/click")
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

        return adm.toString()
                .replace(IMPRESSION_MACRO, imp)
                .replace(SYNC_MACRO, sync)
                .replace(CLICK_MACRO, click)
                .replace(ASSET_MACRO, creative.getCreativeUrl())
                .replaceAll(WIDTH_MACRO, String.valueOf(creative.getW()))
                .replaceAll(HEIGHT_MACRO, String.valueOf(creative.getH()));
    }
}
