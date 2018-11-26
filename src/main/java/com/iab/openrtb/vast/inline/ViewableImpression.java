package com.iab.openrtb.vast.inline;

import com.iab.openrtb.vast.inline.viewableimpression.NotViewable;
import com.iab.openrtb.vast.inline.viewableimpression.ViewUndetermined;
import com.iab.openrtb.vast.inline.viewableimpression.Viewable;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="Ad")
public class ViewableImpression {

    @XmlAttribute(name="id")
    private String id;

    @XmlElement
    private Viewable viewable;

    @XmlElement
    private NotViewable notViewable;

    @XmlElement
    private ViewUndetermined viewUndetermined;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Viewable getViewable() {
        return viewable;
    }

    public void setViewable(Viewable viewable) {
        this.viewable = viewable;
    }

    public NotViewable getNotViewable() {
        return notViewable;
    }

    public void setNotViewable(NotViewable notViewable) {
        this.notViewable = notViewable;
    }

    public ViewUndetermined getViewUndetermined() {
        return viewUndetermined;
    }

    public void setViewUndetermined(ViewUndetermined viewUndetermined) {
        this.viewUndetermined = viewUndetermined;
    }
}
