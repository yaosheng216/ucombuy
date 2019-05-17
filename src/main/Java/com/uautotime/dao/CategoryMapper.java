package com.uautotime.dao;

import com.uautotime.pojo.Category;

import java.util.*;

public interface CategoryMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    List<Category> selectCategoryChilerenByParentId(Integer parentId);

}