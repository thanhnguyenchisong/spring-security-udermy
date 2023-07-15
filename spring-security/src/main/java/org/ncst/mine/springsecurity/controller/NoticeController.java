package org.ncst.mine.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notice")
public class NoticeController {
    @RequestMapping(method = RequestMethod.GET, path = "/getNotices")
    public String getNotices() {
        return "Here are notices details from the DB";
    }
}
