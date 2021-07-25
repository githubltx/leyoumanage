package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.beans.Transient;
import java.util.*;
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
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

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

    @Transactional
    public void saveGoods(Spu spu) {
        // 保存spu
        spu.setId(null);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(false);
        int insert = this.spuMapper.insert(spu);
        if(insert!=1){
            throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
        }
        // 保存spu详情
        spu.getSpuDetail().setSpuId(spu.getId());
        if(spu.getSpuDetail().getSpecTemplate()==null){
            spu.getSpuDetail().setSpecTemplate("");
        }
        if(spu.getSpuDetail().getSpecifications()==null){
            spu.getSpuDetail().setSpecifications("");
        }
        this.spuDetailMapper.insert(spu.getSpuDetail());
        // 新增sku
        List<Sku> skus = spu.getSkus();
        List<Stock> stocks = new ArrayList<>();
        for (Sku sku : skus) {
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setSpuId(spu.getId());
            insert = skuMapper.insert(sku);
            if(insert!=1){
                throw new LyException(ExceptionEnum.GOODS_SAVE_ERROR);
            }
            //新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);
            stockMapper.insert(stock);
        }
        //stockMapper.insertList(stocks);

        // 保存sku和库存信息
//        saveSkuAndStock(spu.getSkus(), spu.getId());
    }

    public SpuDetail querySpuDetailById(Long id) {
        return this.spuDetailMapper.selectByPrimaryKey(id);
    }

    public List<Sku> querySkuBySpuId(Long spuId) {
        // 查询sku
        Sku record = new Sku();
        record.setSpuId(spuId);
        List<Sku> skus = this.skuMapper.select(record);
        List<Long> ids = skus.stream().map(Sku::getId).collect(Collectors.toList());
        List<Stock> stocks = stockMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(stocks)){
            throw  new LyException(ExceptionEnum.NOT_FOUND);
        }
        //把stock 变成一个map，key为sku的id，值为库存值
        Map<Long, Integer> stockMap = stocks.stream().
                collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skus.forEach(s ->s.setStock(stockMap.get(s.getId())));

//        for (Sku sku : skus) {
//            // 同时查询出库存
//            sku.setStock(this.stockMapper.selectByPrimaryKey(sku.getId()).getStock());
//        }
        return skus;
    }
    @Transactional
    public void update(Spu spu) {
        // 查询以前sku
        List<Sku> skus = this.querySkuBySpuId(spu.getId());
        // 如果以前存在，则删除
        if(!CollectionUtils.isEmpty(skus)) {
            List<Long> ids = skus.stream().map(s -> s.getId()).collect(Collectors.toList());
            // 删除以前库存
            Example example = new Example(Stock.class);
            example.createCriteria().andIn("skuId", ids);
            this.stockMapper.deleteByExample(example);

            // 删除以前的sku
            Sku record = new Sku();
            record.setSpuId(spu.getId());
            this.skuMapper.delete(record);

        }
        // 新增sku和库存
        saveSkuAndStock(spu.getSkus(), spu.getId());

        // 更新spu
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);
        spu.setValid(null);
        spu.setSaleable(null);
        this.spuMapper.updateByPrimaryKeySelective(spu);

        // 更新spu详情
        this.spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
    }

    private void saveSkuAndStock(List<Sku> skus, Long id) {
    }
}
