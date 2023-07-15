package org.ncst.mine.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("balance")
public class BalanceController {

    @RequestMapping(method = RequestMethod.GET, path = "/getBalances")
    public String getBalanceDetails() {
        return "Here are balance details from the DB";
    }
}
