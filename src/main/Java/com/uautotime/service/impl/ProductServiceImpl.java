package com.uautotime.service.impl;

import com.uautotime.common.ResponseCode;
import com.uautotime.dao.CategoryMapper;
import com.uautotime.pojo.Category;
import com.uautotime.service.IProductService;
import com.uautotime.common.ServerResponse;
import com.uautotime.dao.ProductMapper;
import com.uautotime.pojo.Product;
import com.uautotime.util.DateTimeUtil;
import com.uautotime.util.PropertiesUtil;
import com.uautotime.vo.ProductDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2019/5/8.
 */
@Service("iProductService")
public class ProductServiceImpl {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse saveOrUpdateProduct(Product product){
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }
            if (product.getId() != null) {
                int rowCount =  productMapper.updateByPrimaryKey(product);
                if(rowCount > 0){
                    return ServerResponse.creatBySuccess("更新产品成功");
                }
                return ServerResponse.creatBySuccess("更新产品失败");
            } else {
                int rowCount =  productMapper.insert(product);
                if(rowCount > 0){
                    return ServerResponse.creatBySuccess("新增产品成功");
                }
                return ServerResponse.creatBySuccess("新增产品失败");
            }
        }
        return ServerResponse.creatByErrorMessage("新增或更新产品参数不正确");
    }

    public ServerResponse<String> setSaleStatus(Integer productId,Integer status){
        if(productId == null || status == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_RGUMENT.getCode(),ResponseCode.ILLEGAL_RGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if(rowCount > 0){
            return ServerResponse.creatBySuccess("修改产品销售状态成功");
        }
        return ServerResponse.creatByErrorMessage("修改产品销售状态失败");
    }

    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId){
        if(productId == null){
            return ServerResponse.creatByErrorCodeMessage(ResponseCode.ILLEGAL_RGUMENT.getCode(),ResponseCode.ILLEGAL_RGUMENT.getDesc());
        }
        Product product = ProductMapper.selectByPrimaryKey(productId);
        if(product == null){
            return ServerResponse.creatByErrorMessage("产品已下架或者删除");
        }
        return null;
        ProductDetailVo productDetailVo = assembleProductDetailVo(product);
        return ServerResponse.creatBySuccess(productDetailVo);
    }

    private assembleProductDetailVo(Product product){

        ProductDetailVo productDetailVo = new ProductDetailVo();
        productDetailVo.setId(product.getId());
        productDetailVo.setSubtitle(product.getSubtitle());
        productDetailVo.setPrice(product.getPrice());
        productDetailVo.setMainImage(product.getMainImage());
        productDetailVo.setSubImage(product.getSubImages());
        productDetailVo.setCategoryId(product.getCategoryId());
        productDetailVo.setDetail(product.getDetail());
        productDetailVo.setName(product.getName());
        productDetailVo.setStatus(product.getStatus());
        productDetailVo.setStock(product.getStock());

        productDetailVo.setImageHsost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));
        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if(category == null){
            productDetailVo.setParentCategoryId(0);    //默认根节点
        }else{
            productDetailVo.setParentCategoryId(category.getParentId());
        }

        productDetailVo.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVo.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVo;
    }
}
