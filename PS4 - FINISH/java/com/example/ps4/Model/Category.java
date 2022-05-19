package com.example.ps4.Model;

import java.io.Serializable;

public class Category implements Serializable {

    String grp, code, codeKor, caption, creation_Date, processing_Date;

    public Category(){}

    public Category(String grp, String code, String codeKor, String caption, String creation_Date, String processing_Date) {
        this.grp = grp;
        this.code = code;
        this.codeKor = codeKor;
        this.caption = caption;
        this.creation_Date = creation_Date;
        this.processing_Date = processing_Date;
    }

    public String getGrp() {
        return grp;
    }

    public void setGrp(String grp) {
        this.grp = grp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeKor() {
        return codeKor;
    }

    public void setCodeKor(String codeKor) {
        this.codeKor = codeKor;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCreation_Date() {
        return creation_Date;
    }

    public void setCreation_Date(String creation_Date) {
        this.creation_Date = creation_Date;
    }

    public String getProcessing_Date() {
        return processing_Date;
    }

    public void setProcessing_Date(String processing_Date) {
        this.processing_Date = processing_Date;
    }

    @Override
    public String toString() {
        return "Category{" +
                "grp='" + grp + '\'' +
                ", code='" + code + '\'' +
                ", codeKor='" + codeKor + '\'' +
                ", caption='" + caption + '\'' +
                ", creation_Date='" + creation_Date + '\'' +
                ", processing_Date='" + processing_Date + '\'' +
                '}';
    }
}
