package com.example.dev_j310nam.dto;

public enum TypeEnum {

    JURIDICAL("Юридическое лицо"),
    NATURAL("Физическое лицо");

    private String type;

    TypeEnum(String type) {
        this.type = type;
    }

    public String getTypeValue(){
        return type;
    }

    public static TypeEnum getTypeEnum(String type){
        if(type==null) return null;
        for(TypeEnum te : TypeEnum.values()){
            if(te.type.equals(type)) return te;
        }
        return null;
    }

}
