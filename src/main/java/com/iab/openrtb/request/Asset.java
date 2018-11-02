package com.iab.openrtb.request;

import com.fasterxml.jackson.databind.node.ObjectNode;





public class Asset {
    Integer id;

    Integer required;

    TitleObject title;

    ImageObject img;

    VideoObject video;

    DataObject data;

    ObjectNode ext;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public TitleObject getTitle() {
        return title;
    }

    public void setTitle(TitleObject title) {
        this.title = title;
    }

    public ImageObject getImg() {
        return img;
    }

    public void setImg(ImageObject img) {
        this.img = img;
    }

    public VideoObject getVideo() {
        return video;
    }

    public void setVideo(VideoObject video) {
        this.video = video;
    }

    public DataObject getData() {
        return data;
    }

    public void setData(DataObject data) {
        this.data = data;
    }

    public ObjectNode getExt() {
        return ext;
    }

    public void setExt(ObjectNode ext) {
        this.ext = ext;
    }
}
