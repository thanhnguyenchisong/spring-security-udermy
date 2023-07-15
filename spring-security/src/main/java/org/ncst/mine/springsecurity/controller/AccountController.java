package org.ncst.mine.springsecurity.controller;

import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountController {

    @RequestMapping(method = RequestMethod.GET, path = "/getAccounts")
    public String getAccountDetails() {
        return "Here are the account details from the DB";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/help")
    public String help() {
        return "This is UserController helping";
    }
}
