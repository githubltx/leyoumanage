package com.leyou.item.mapper;

import com.leyou.common.mapper.BaseMapper;
import com.leyou.item.pojo.Stock;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: litaixu
 * @Date: 2021/7/24 - 07 - 24 - 17:20
 * @Description: com.leyou.item.mapper
 * @version: 1.0
 */
public interface StockMapper extends BaseMapper<Stock> ,InsertListMapper<Stock>{
}
