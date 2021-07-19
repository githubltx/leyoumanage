package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: litaixu
 * @Date: 2021/5/4 - 05 - 04 - 22:49
 * @Description: com.leyou.item
 * @version: 1.0
 */
public interface CatagoryMapper extends Mapper<Category> , IdListMapper<Category,Long> {
}
