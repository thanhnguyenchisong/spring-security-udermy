package org.ncst.mine.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("loan")
public class LoanController {

    @RequestMapping(method = RequestMethod.GET, path = "/getLoans")
    public String getLoanDetails() {
        return "Here are loans details from the DB";
    }
}
