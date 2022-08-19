package com.salesmanager.shop.store.model.promotion;



public class SimpleDescriptionVo {
    private String title, shortDesc, languageCode;

    public SimpleDescriptionVo() {
    }

    public SimpleDescriptionVo(String title, String shortDesc, String languageCode) {
        this.title = title;
        this.shortDesc = shortDesc;
        this.languageCode = languageCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
}
