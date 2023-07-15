package org.ncst.mine.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("card")
public class CardController {

    @RequestMapping(method = RequestMethod.GET, path = "/getCards")
    public String getCardDetails() {
        return "Here are cards details from the DB";
    }
}
