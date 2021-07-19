package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.Spu;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: litaixu
 * @Date: 2021/7/18 - 07 - 18 - 8:55
 * @Description: com.leyou.item.service
 * @version: 1.0
 */
@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    public PageResult<Spu> querySpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        //1.分页
        PageHelper.startPage(page,rows);
        //2.过滤
        Example example = new Example(Spu.class);
        Example.Criteria exampleCriteria = example.createCriteria();
        //搜索字段过滤
        if(StringUtils.isNotBlank(key)){
            exampleCriteria.andLike("title","%"+key+"%");
        }
        //上下架过滤
        if(saleable!=null){
            exampleCriteria.andEqualTo("saleable", saleable);
        }

        //3.默认排序
        example.setOrderByClause("last_update_time DESC");
        //查询
        List<Spu> spuList = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(spuList)){
            throw  new LyException(ExceptionEnum.NOT_FOUND);
        }
        //结构分类和品牌的名称
        loadCategoryAndBrandName(spuList);
        //解析分页结果
        PageInfo<Spu> spuPageInfo = new PageInfo<Spu>(spuList);
        return new PageResult<Spu>(spuPageInfo.getTotal(),spuList);
    }

    public void loadCategoryAndBrandName(List<Spu> spuList) {
        for (Spu spu :spuList) {
            //处理分类名称
            List<String> categoryNames = categoryService.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(categoryNames,"/"));
            //处理品牌名称
            spu.setBname(brandService.queryById(spu.getBrandId()).getName());
        }
    }
}
