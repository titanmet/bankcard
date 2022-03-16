package com.ratnikov.bankcard.controller;

public interface AppUrls {

    interface Login {
        String NAME = "login";
        String FuLL = "/" + NAME;
    }
    interface Registration {
        String NAME = "registration";
        String FuLL = "/" + NAME;
    }
    interface IndexCards {
        String NAME = "indexcards";
        String FuLL = "/" + NAME;
    }
    interface Page {
        String NAME = "page";
        String FULL = "/" + NAME;

        interface PageNo {
            String NAME = "{pageNo}";
            String FULL = AppUrls.Page.FULL + "/" + NAME;
        }
    }
}
