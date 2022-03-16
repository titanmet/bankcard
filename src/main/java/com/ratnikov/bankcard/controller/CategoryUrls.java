package com.ratnikov.bankcard.controller;

public interface CategoryUrls {

    interface SearchCategories {
        String NAME = "search-categories";
        String FuLL = "/" + NAME;
    }
    interface Categories {
        String NAME = "categories";
        String FuLL = "/" + NAME;

        interface New {
            String NAME = "new";
            String FULL = CategoryUrls.Categories.FuLL + "/" + NAME;
        }
        interface Save {
            String NAME = "save";
            String FULL = CategoryUrls.Categories.FuLL + "/" + NAME;
        }
        interface Delete {
            String NAME = "delete";
            String FULL = CategoryUrls.Categories.FuLL + "/" + NAME;

            interface DeleteId {
                String NAME = "{id}";
                String FULL = CategoryUrls.Categories.Delete.FULL + "/" + NAME;
            }
        }
        interface Page {
            String NAME = "page";
            String FULL = CategoryUrls.Categories.FuLL + "/" + NAME;

            interface PageNo {
                String NAME = "{pageNo}";
                String FULL = CategoryUrls.Categories.Page.FULL + "/" + NAME;
            }
        }
    }
}

