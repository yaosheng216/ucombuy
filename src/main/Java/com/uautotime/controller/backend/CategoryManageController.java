package com.uautotime.controller.backend;

import com.uautotime.common.ResponseCode;
import com.uautotime.common.ServerResponse;
import com.uautotime.pojo.User;
import com.uautotime.service.ICategoryService;
import com.uautotime.service.IUserService;
import com.uautotime.util.CookieUtil;
import com.uautotime.util.JsonUtil;
import com.uautotime.util.RedisShardedPoolUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yaosheng on 2019/5/6.
 */
@Controller
@RequestMapping("/manage/category")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest httpServletRequest, String categoryName, @RequestParam(value = "parentId",defaultValue = "0") int parentId) {

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);

        if (user == null) {
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录，请登录");
        }
        //校验一下是否是管理员登陆
        if (iUserService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //增加分类处理的逻辑
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.creatByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest httpServletRequest,Integer categoryId,String categoryName){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);

        if(user == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //更新categoryName
            return iCategoryService.updateCategoryName(categoryId,categoryName);
        }else{
            return ServerResponse.creatByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);

        if(user == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            // 查询子节点的category信息，并且不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.creatByErrorMessage("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest httpServletRequest,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);

        if(user == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            // 查询当前节点的id和递归子节点的id
            //0->10000->100000
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return ServerResponse.creatByErrorMessage("无权限操作，需要管理员权限");
        }
    }
    
}
