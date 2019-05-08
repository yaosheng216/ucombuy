package com.uautotime.service;

import com.uautotime.common.ServerResponse;
import com.uautotime.pojo.Product;
import com.uautotime.vo.ProductDetailVo;

/**
 * Created by admin on 2019/5/8.
 */
public class IProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse<String> setSaleStatus(Integer productId,Integer status);

    public ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

}
