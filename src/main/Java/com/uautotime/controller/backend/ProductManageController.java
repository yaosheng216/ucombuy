package com.uautotime.controller.backend;

import com.google.common.collect.Maps;
import com.uautotime.common.ResponseCode;
import com.uautotime.common.ServerResponse;
import com.uautotime.pojo.Product;
import com.uautotime.pojo.User;
import com.uautotime.service.IFileService;
import com.uautotime.service.IProductService;
import com.uautotime.service.IUserService;
import com.uautotime.util.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by yaosheng on 2019/5/8.
 */
@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IFileService iFileService;

    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest httpServletRequest, Product product){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);

        if(user == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充增加业务的逻辑
            return iProductService.saveOrUpdateProduct(product);
        }else{
            return ServerResponse.creatByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest httpServletRequest, Integer productId,Integer status){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);

        if(user == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            return iProductService.setSaleStatus(productId,status);
        }else{
            return ServerResponse.creatByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest httpServletRequest, Integer productId){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);
        if(user == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            return iProductService.manageProductDetail(productId);
        }else{
            return ServerResponse.creatByErrorMessage("无权限操作");
        }
    }

    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpServletRequest httpServletRequest,@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSzize",defaultValue = "10") int pageSize){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);
        if(user == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录管理员");
        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            return iProductService.getProductList(pageNum,pageSize);
        }else{
            return ServerResponse.creatByErrorMessage("无权限操作");
        }

    }

    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest httpServletRequest,String productName,Integer productId, @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,@RequestParam(value = "pageSize",defaultValue = "10") int pageSize){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);

        if(user == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
        }else{
            return ServerResponse.creatByErrorMessage("无权限操作");
        }

    }

    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(HttpServletRequest httpServletRequest,@RequestParam(value = "upload_file",required = false) MultipartFile File, HttpServletRequest request){

        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            return ServerResponse.creatByErrorMessage("用户未登录，无法获取当前用户信息");
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);
        if(user == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(File,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;

            Map fileMape = Maps.newHashMap();
            fileMape.put("uri",targetFileName);
            fileMape.put("url",url);
            return ServerResponse.creatBySuccess(fileMape);
        }else{
            return ServerResponse.creatByErrorMessage("无权限操作");
        }

    }

    @RequestMapping("richtext_img_upload.do")
    @ResponseBody
    public Map richtextImgUpload(HttpServletRequest httpServletRequest, @RequestParam(value = "upload_file",required = false) MultipartFile File, HttpServletRequest request, HttpServletResponse response){

        Map resultMap = Maps.newHashMap();
        String loginToken = CookieUtil.readLoginToken(httpServletRequest);
        if(StringUtils.isEmpty(loginToken)){
            resultMap.put("sucess",false);
            resultMap.put("msg","请登录管理员");
            return resultMap;
        }
        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
        User user = JsonUtil.string2obj(userJsonStr,User.class);

        if(user == null){
            resultMap.put("sucess",false);
            resultMap.put("msg","请登录管理员");
            return resultMap;

        }
        //富文本中对于返回值有自己的要求，我们使用的是simditor，所以按照simditor的要求进行返回
        // {
        //    "success": true/false,
        //        "msg": "error message", # optional
        //    "file_path": "[real file path]"
        // }

        if(iUserService.checkAdminRole(user).isSuccess()){
            //填充业务
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(File,path);
            if(StringUtils.isBlank(targetFileName)){
                resultMap.put("sucess",false);
                resultMap.put("msg","上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;
            resultMap.put("sucess",true);
            resultMap.put("msg","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");
            return resultMap;
        }else{
            resultMap.put("sucess",false);
            resultMap.put("msg","无权限操作");
            return resultMap;
        }

    }

}
