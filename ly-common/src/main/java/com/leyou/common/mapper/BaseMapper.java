package com.leyou.common.mapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Auther: litaixu
 * @Date: 2021/7/24 - 07 - 24 - 18:05
 * @Description: com.leyou.common.mapper
 * @version: 1.0
 */
@RegisterMapper
public interface BaseMapper<T> extends Mapper<T>, IdListMapper<T,Long>, InsertListMapper<T> {
}
