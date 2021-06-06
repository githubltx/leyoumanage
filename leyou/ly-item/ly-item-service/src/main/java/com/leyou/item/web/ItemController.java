package com.leyou.item.web;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.pojo.Item;
import com.leyou.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: litaixu
 * @Date: 2021/5/1 - 05 - 01 - 22:52
 * @Description: com.leyou.item.web
 * @version: 1.0
 */
@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> saveItem(Item item){
        //校验价格为空
        if(item.getPrice()==null){
            throw new LyException(ExceptionEnum.PRICE_COUNT_NOT_NULL);
        }
        Item saveItem = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(saveItem);
    }
}
