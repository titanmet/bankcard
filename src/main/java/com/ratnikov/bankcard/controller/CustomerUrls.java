package com.ratnikov.bankcard.controller;

public interface CustomerUrls {

    interface SearchCustomers {
        String NAME = "search-customers";
        String FuLL = "/" + NAME;
    }
    interface Customer {
        String NAME = "customer";
        String FuLL = "/" + NAME;

        interface New {
            String NAME = "new";
            String FULL = CustomerUrls.Customer.FuLL + "/" + NAME;
        }
        interface Save {
            String NAME = "save";
            String FULL = CustomerUrls.Customer.FuLL + "/" + NAME;
        }
        interface EditSave {
            String NAME = "edit-save";
            String FULL = CustomerUrls.Customer.FuLL + "/" + NAME;
        }
        interface Edit {
            String NAME = "edit";
            String FULL = CustomerUrls.Customer.FuLL + "/" + NAME;

            interface EditId {
                String NAME = "{id}";
                String FULL = CustomerUrls.Customer.Edit.FULL + "/" + NAME;
            }
        }
        interface Delete {
            String NAME = "delete";
            String FULL = CustomerUrls.Customer.FuLL + "/" + NAME;

            interface DeleteId {
                String NAME = "{id}";
                String FULL = CustomerUrls.Customer.Delete.FULL + "/" + NAME;
            }
        }
        interface Page {
            String NAME = "page";
            String FULL = CustomerUrls.Customer.FuLL + "/" + NAME;

            interface PageNo {
                String NAME = "{pageNo}";
                String FULL = CustomerUrls.Customer.Page.FULL + "/" + NAME;
            }
        }
    }
}