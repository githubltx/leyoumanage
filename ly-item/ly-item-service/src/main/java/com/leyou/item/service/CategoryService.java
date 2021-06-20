package com.leyou.item.service;

import com.leyou.common.advice.CommonExceptionHanler;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.CatagoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther: litaixu
 * @Date: 2021/5/4 - 05 - 04 - 22:51
 * @Description: com.leyou.item.service
 * @version: 1.0
 */
@Service
public class CategoryService {
    @Autowired
    private CatagoryMapper catagroyMapper;

    public List<Category> queryCatagoryListByPid(Long t) {
        Category category = new Category();
        category.setParentId(t);
        List<Category> categoryList = catagroyMapper.select(category);
        if(CollectionUtils.isEmpty(categoryList)){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        return categoryList;
    }
}
