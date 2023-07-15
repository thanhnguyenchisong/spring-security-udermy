package org.ncst.mine.springsecurity.controller;

import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contact")
public class ContractController {
    @RequestMapping(method = RequestMethod.GET, path = "/getContacts")
    public String getContactDetails() {
        return "Here are contacts details from the DB";
    }
}
