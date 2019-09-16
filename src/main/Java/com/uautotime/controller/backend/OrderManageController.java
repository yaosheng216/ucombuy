package com.uautotime.controller.backend;

import com.github.pagehelper.PageInfo;
import com.uautotime.common.ServerResponse;
import com.uautotime.service.IOrderService;
import com.uautotime.service.IUserService;
import com.uautotime.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yaosheng on 2019/6/14.
 */
@Controller
@RequestMapping("/manage/order")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IOrderService iOrderService;

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpServletRequest httpServletRequest, @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10")int pageSzie){
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2obj(userJsonStr,User.class);
//
//        if(user == null){
//            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            //填充增加业务的逻辑
//            return iOrderService.manageList(pageNum,pageSzie);
//        }else{
//            return ServerResponse.creatByErrorMessage("无权限操作");
//        }
        //全部通过拦截器验证是否登陆以及权限
        return iOrderService.manageList(pageNum,pageSzie);
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(HttpServletRequest httpServletRequest, Long orderNo){

//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2obj(userJsonStr,User.class);
//
//        if(user == null){
//            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            //填充增加业务的逻辑
//            return iOrderService.manageDetail(orderNo);
//        }else{
//            return ServerResponse.creatByErrorMessage("无权限操作");
//        }
        //全部通过拦截器验证是否登陆以及权限
        return iOrderService.manageDetail(orderNo);
    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpServletRequest httpServletRequest, Long orderNo,@RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2obj(userJsonStr,User.class);
//
//        if(user == null){
//            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            //填充增加业务的逻辑
//            return iOrderService.manageSearch(orderNo,pageNum,pageSize);
//        }else{
//            return ServerResponse.creatByErrorMessage("无权限操作");
//        }
        //全部通过拦截器验证是否登陆以及权限
        return iOrderService.manageSearch(orderNo,pageNum,pageSize);
    }

    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpServletRequest httpServletRequest, Long orderNo){

//        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.string2obj(userJsonStr,User.class);
//
//        if(user == null){
//            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
//        }
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            //填充增加业务的逻辑
//            return iOrderService.manageSendGoods(orderNo);
//        }else{
//            return ServerResponse.creatByErrorMessage("无权限操作");
//        }
        //全部通过拦截器验证是否登陆以及权限
        return iOrderService.manageSendGoods(orderNo);
    }

}
