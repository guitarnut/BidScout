package com.iab.openrtb.request;

import com.fasterxml.jackson.databind.node.ObjectNode;





public class TitleObject {
    Integer len;

    ObjectNode ext;

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
