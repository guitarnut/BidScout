package com.gnut.bidscout.html;

import com.gnut.bidscout.model.Campaign;
import com.gnut.bidscout.model.Creative;
import org.springframework.stereotype.Component;

@Component
public class AdMarkup {
    private static final String WIDTH_MACRO = "WIDTH";
    private static final String HEIGHT_MACRO = "HEIGHT";
    private static final String IMPRESSION_MACRO = "IMPRESSION";
    private static final String ASSET_MACRO = "ASSET";
    private static final String HTML_OPEN = "<div style=\"width:" + WIDTH_MACRO + "px; height:" + HEIGHT_MACRO + "px;\">";
    private static final String HTML_CLOSE = "</div>";
    private static final String IMPRESSION_IMG_HIDDEN = "<img src=\"" + IMPRESSION_MACRO + "\" style=\"width:0; height:0; display:none;\"/>";
    private static final String ASSET_IMG = "<img src=\"" + ASSET_MACRO + "\" style=\"width:" + WIDTH_MACRO + "px; height:" + HEIGHT_MACRO + "px;\">";

    public String generateMarkup(Campaign campaign, Creative creative) {
        final StringBuilder sb = new StringBuilder();
        sb.append(HTML_OPEN);
        sb.append(ASSET_IMG);
        sb.append(IMPRESSION_IMG_HIDDEN);
        sb.append(HTML_CLOSE);

        return sb.toString()
                .replace(IMPRESSION_MACRO, campaign.getImpression())
                .replace(ASSET_MACRO, creative.getCreativeUrl())
                .replaceAll(WIDTH_MACRO, String.valueOf(creative.getW()))
                .replaceAll(HEIGHT_MACRO, String.valueOf(creative.getH()));
    }
}
