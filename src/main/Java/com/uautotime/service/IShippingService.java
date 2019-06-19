package com.uautotime.service;

import com.github.pagehelper.PageInfo;
import com.uautotime.common.ServerResponse;
import com.uautotime.pojo.Shipping;

/**
 * Created by admin on 2019/5/19.
 */
public interface IShippingService {

    ServerResponse add(Integer userId, Shipping shipping);

    ServerResponse<String> del(Integer userId,Integer shippingId);

    ServerResponse update(Integer userId,Shipping shipping);

    ServerResponse<Shipping> select(Integer userId,Integer shippingId);

    ServerResponse<PageInfo> list(Integer userId, int pageNum, int pageSize);

}
