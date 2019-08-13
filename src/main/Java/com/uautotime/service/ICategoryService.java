package com.uautotime.service;

import com.uautotime.common.ServerResponse;
import com.uautotime.pojo.Category;

import java.util.List;

/**
 * Created by yaosheng on 2019/5/6.
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoyrId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);

}
