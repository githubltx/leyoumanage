package com.leyou.item.web;

import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: litaixu
 * @Date: 2021/7/18 - 07 - 18 - 10:16
 * @Description: com.leyou.item.web
 * @version: 1.0
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key
    ){
        return ResponseEntity.ok(goodsService.querySpuByPage(page,rows,saleable,key));
    }

    /**
     * 新增商品
     * @param spu
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu) {
        try {
            this.goodsService.saveGoods(spu);
            return  ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * 请求方式：GET
     * 请求路径：/spu/detail/{id}
     * 请求参数：id，应该是spu的id
     * 返回结果：SpuDetail对象
     * @return
     */
    @GetMapping("/spu/detail/{id}")
    public ResponseEntity<SpuDetail> querySpuDetailById(@PathVariable("id") Long id) {
        SpuDetail detail = this.goodsService.querySpuDetailById(id);
        if (detail == null) {
            return new ResponseEntity<SpuDetail>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(detail);
    }

    /**
     * 请求方式：Get
     * 请求路径：/sku/list
     * 请求参数：id，应该是spu的id
     * 返回结果：sku的集合
     * @return ResponseEntity<List<Sku>>
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id) {
        List<Sku> skus = this.goodsService.querySkuBySpuId(id);
        if (skus == null || skus.size() == 0) {
            return new ResponseEntity<List<Sku>>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(skus);
    }
    /**
     * 新增商品
     * @param spu
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu) {
        try {
            this.goodsService.update(spu);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
