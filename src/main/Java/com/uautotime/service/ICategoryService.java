package com.uautotime.service;

import com.uautotime.common.ServerResponse;
import com.uautotime.pojo.Category;

import java.util.*;

/**
 * Created by admin on 2019/5/6.
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<java.util.List<Category>> getChildrenParallelCategory(Integer categoyrId);
    ServerResponse selectCategoryAndChildrenById(Integer categoryId);

}
