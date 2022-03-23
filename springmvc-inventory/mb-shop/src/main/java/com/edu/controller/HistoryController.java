package com.edu.controller;

import com.edu.entity.History;
import com.edu.entity.ProductInStock;
import com.edu.model.Paging;
import com.edu.service.HistoryService;
import com.edu.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @RequestMapping(value = {"/history/list", "/history/list/"})
    public String moveListHistory() {
        return "redirect:/history/list/1";
    }

    @RequestMapping(value = "/history/list/{page}")
    public String showHistoryList(Model model, @ModelAttribute("searchForm") History mHistory, @PathVariable("page") int page) {
        Paging paging = new Paging(10);
        paging.setIndexPage(page);
        List<History> histories = historyService.getAllHistory(mHistory, paging);
        model.addAttribute("pageInfo", paging);
        model.addAttribute("histories", histories);
        model.addAttribute("mapType", initMapType());
        return "history-list";

    }
    private Map<String, String> initMapType() {
        Map<String, String> mapType = new HashMap<>();
        mapType.put(String.valueOf(Constant.TYPE_ALL), "All");
        mapType.put(String.valueOf(Constant.TYPE_GOODS_RECEIPT), "Goods Receipt");
        mapType.put(String.valueOf(Constant.TYPE_GOODS_ISSUES), "Goods Issues");
        return mapType;
    }
}
