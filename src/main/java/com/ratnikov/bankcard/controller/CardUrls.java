package com.ratnikov.bankcard.controller;

public interface CardUrls {

    interface SearchCards {
        String NAME = "search-cards";
        String FuLL = "/" + NAME;
    }
    interface Card {
        String NAME = "card";
        String FuLL = "/" + NAME;

        interface New {
            String NAME = "new";
            String FULL = Card.FuLL + "/" + NAME;
        }
        interface Save {
            String NAME = "save";
            String FULL = Card.FuLL + "/" + NAME;
        }
        interface EditSave {
            String NAME = "edit-save";
            String FULL = Card.FuLL + "/" + NAME;
        }
        interface Edit {
            String NAME = "edit";
            String FULL = Card.FuLL + "/" + NAME;

            interface EditId {
                String NAME = "{id}";
                String FULL = Edit.FULL + "/" + NAME;
            }
        }
        interface Delete {
            String NAME = "delete";
            String FULL = Card.FuLL + "/" + NAME;

            interface DeleteId {
                String NAME = "{id}";
                String FULL = Delete.FULL + "/" + NAME;
            }
        }
        interface Page {
            String NAME = "page";
            String FULL = Card.FuLL + "/" + NAME;

            interface PageNo {
                String NAME = "{pageNo}";
                String FULL = Page.FULL + "/" + NAME;
            }
        }
    }
}
