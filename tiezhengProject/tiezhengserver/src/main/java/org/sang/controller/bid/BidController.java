package org.sang.controller.bid;

import org.sang.bean.*;
import org.sang.service.BidService;
import org.sang.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * zhaoxian 2018/11/30
 */
@RestController
@RequestMapping("/bid")
public class BidController {
    @Autowired
    BidService bidService;
    @Autowired
    CompanyService companyService;
    @RequestMapping(value = "/getBidsByPage",method = RequestMethod.GET)
    public Map<String,Object> getBids(@RequestParam(defaultValue = "1") Integer page,
                                       @RequestParam(defaultValue = "10") Integer size){
        Map<String,Object> bidInfo = new HashMap<>();
        bidInfo.put("bids",bidService.getBidsByPage(page,size));
        bidInfo.put("count",50);
        return bidInfo;
    }
    @RequestMapping(value = "/addBid", method = RequestMethod.POST)
    public RespBean addBid(Bid bid) {
        if (bidService.addBid(bid) == 1) {
            System.out.println("菜单添加成功");
            return RespBean.ok("添加成功!");
        }
        return RespBean.error("添加失败!");
    }
    @RequestMapping(value = "/updateBid", method = RequestMethod.PUT)
    public RespBean updateBid(Bid bid,BindingResult bindingResult) {
        if (bidService.updateBid(bid) == 1) {
            return RespBean.ok("更新成功!");
        }
        return RespBean.error("更新失败!");
    }
    @RequestMapping(value = "/deleteBid/{ids}", method = RequestMethod.DELETE)
    public RespBean deleteBidById(@PathVariable String ids) {
        if (bidService.deleteBidById(ids)) {
            return RespBean.ok("删除成功!");
        }
        return RespBean.error("删除失败!");
    }
    @RequestMapping(value = "/getBasicData",method = RequestMethod.GET)
    public Map<String,Object> getBasicData(){
        Map<String,Object> bidInfo = new HashMap<>();
        bidInfo.put("bids",bidService.getAllBids());
        bidInfo.put("companys",companyService.getAllCompanys());
        return bidInfo;
    }
}
