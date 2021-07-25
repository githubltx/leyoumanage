package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.lang.reflect.Parameter;
import java.util.List;

/**
 * @Auther: litaixu
 * @Date: 2021/7/16 - 07 - 16 - 23:00
 * @Description: com.leyou.item.web
 * @version: 1.0
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    private ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable ("cid")Long cid){
        return ResponseEntity.ok(specificationService.queryGroupByCid(cid));
    }


    //@RequestMapping(value = "group", method = RequestMethod.PUT)
    @PutMapping(value = "group")
    @ResponseBody
    private ResponseEntity<SpecGroup> updateGroupByCid(@RequestBody SpecGroup specGroup){
        return ResponseEntity.ok(specificationService.updateGroupById(specGroup));
    }

    //@RequestMapping(value = "group", method = RequestMethod.PUT)
    @PostMapping (value = "group")
    @ResponseBody
    private ResponseEntity<SpecGroup> addGroupByCid(@RequestBody SpecGroup specGroup){
        return ResponseEntity.ok(specificationService.addGroupById(specGroup));
    }

    /*开发过程IDEA提示如将@RequestMapping(value="/abc" , method = “RequestMethod.POST”)替换成@PostMapping。
        现对@PostMapping的实现。
        @PostMapping是一个复合注解，
        Spring framework 4.3引入了@RequestMapping注释的变体，以更好地表示带注释的方法的语义，
        作为@RequestMapping(method = RequestMethod.POST)的快捷方式。也就是可以简化成@PostMapping(value="/abc" )即可，
        主要是方便识记。
        //@RequestMapping(value = “”, path = “”, params = “”, headers = “”,consumes = “”, produces = “”)
        //@GetMapping、@PutMapping、@PatchMapping和@DeleteMapping，与@PostMapping实现类似
    */
    //@RequestMapping(value="group/{id}", method=RequestMethod.DELETE)
    @DeleteMapping("group/{id}")
    @ResponseBody
    private  ResponseEntity<SpecGroup> deleteGroupByCid(@PathVariable ("id")Long id){
        return ResponseEntity.ok(specificationService.deleteGroupById(id));
    }

    /**
     * 查询参数集合
     * @param gid 组id
     * @param cid 分类id
     * @param searching 是否搜索
     * @return
     */
    @GetMapping("params")
    private ResponseEntity<List<SpecParam>> queryParamByGid(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value = "cid",required = false) Long cid,
            @RequestParam(value = "searching",required = false) Boolean searching){
        return ResponseEntity.ok(specificationService.queryParamList(gid,cid,searching));
    }
}
