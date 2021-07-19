package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Auther: litaixu
 * @Date: 2021/7/16 - 07 - 16 - 22:59
 * @Description: com.leyou.item.service
 * @version: 1.0
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> specGroupList = specGroupMapper.select(specGroup);
        if(CollectionUtils.isEmpty(specGroupList)){
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return specGroupList;
    }

    public SpecGroup updateGroupById(SpecGroup specGroup) {
        int succ = specGroupMapper.updateByPrimaryKey(specGroup);
        if(succ!=1){
            throw new LyException(ExceptionEnum.SPEC_GROUP_FAIL);
        }
        return specGroup;
    }

    public SpecGroup deleteGroupById(Long id) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setId(id);
        int succ = specGroupMapper.delete(specGroup);
        if(succ!=1){
            throw new LyException(ExceptionEnum.SPEC_GROUP_FAIL);
        }
        return specGroup;
    }

    public SpecGroup addGroupById(SpecGroup specGroup) {
        int succ = specGroupMapper.insert(specGroup);
        if(succ!=1){
            throw new LyException(ExceptionEnum.SPEC_GROUP_FAIL);
        }
        return specGroup;
    }

    public List<SpecParam> queryParamByGid(Long gid) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        List<SpecParam> list = specParamMapper.select(specParam);
        if(CollectionUtils.isEmpty(list)){
             new ResponseEntity<SpecParam>(HttpStatus.NOT_FOUND);
        }
        return list;
    }
}
