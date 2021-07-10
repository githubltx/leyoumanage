package com.leyou.item.web;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Auther: litaixu
 * @Date: 2021/5/4 - 05 - 04 - 22:54
 * @Description: com.leyou.item.web
 * @version: 1.0
 */
@RestController
@RequestMapping("category")//category
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("list")
    @ResponseBody
    public ResponseEntity<List<Category>> queryCatagoryListByPid(@RequestParam("pid")Long  pid){

        return  ResponseEntity.ok(categoryService.queryCatagoryListByPid(pid));
    }
}
