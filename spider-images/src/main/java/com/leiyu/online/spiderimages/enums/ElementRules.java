package com.leiyu.online.spiderimages.enums;

public enum  ElementRules {
    SISE("ss"){
        @Override
        public String getPageUrl() {
            return ".pagination > a";
        }

        @Override
        public String getPageInfo() {
            return ".list-text-my li";
        }

        @Override
        public String getImgSelector() {
            return ".content > img";
        }

        @Override
        public String getImgPath() {
            return "data-original";
        }
    },
    JUPAO("jp"){
        @Override
        public String getPageUrl() {
            return ".hy-page a";
        }

        @Override
        public String getPageInfo() {
            return ".hy-video-list li";
        }

        @Override
        public String getImgSelector() {
            return ".playlist > a";
        }

        @Override
        public String getImgPath() {
            return "href";
        }
    },

    ;
    ElementRules(String name){
        this.name = name;
    }
    private String name;

    public abstract String getPageUrl();

    public abstract String getPageInfo();

    public abstract String getImgSelector();

    public abstract String getImgPath();

    public static ElementRules getRule(String name){
        for(ElementRules rule : ElementRules.values()){
            if(rule.name.equals(name)){
                return rule;
            }
        }
        return null;
    }
}
