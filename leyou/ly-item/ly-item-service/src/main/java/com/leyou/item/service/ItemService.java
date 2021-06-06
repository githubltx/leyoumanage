package com.leyou.item.service;

import com.leyou.item.pojo.Item;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Auther: litaixu
 * @Date: 2021/5/1 - 05 - 01 - 22:42
 * @Description: com.leyou.item.service
 * @version: 1.0
 */
@Service
public class ItemService {
    public Item saveItem(Item item){
        int id = new Random().nextInt(100);
        item.setId(id);
        return item;
    }
}
