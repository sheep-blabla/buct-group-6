package com.buct.acmer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfproblem;
import com.buct.acmer.entity.PublicProperty;
import com.buct.acmer.service.impl.CfproblemServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BUCT
 * @since 2024-07-09
 */
@Api(tags = "Cfproblem")
@RestController
@RequestMapping("/acmer/cfproblem")
public class CfproblemController {
    @Resource
    private CfproblemServiceImpl cfproblemService;


    //完成图2
    @ApiOperation("查询codeforces全部题目信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true)
    })
    @GetMapping("/all/{currentPage}/{pageSize}")
    public PublicProperty<Page<Cfproblem>> selectAll(@PathVariable("currentPage") Integer currentPage,
                                                     @PathVariable("pageSize") Integer pageSize) {

        Page<Cfproblem> page = new Page<>(currentPage,pageSize);
        return new PublicProperty(200,"success",cfproblemService.page(page));
    }

    @ApiOperation("根据值查询codeforces题目信息")    //这里的fieldName为数据库的字段
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "fieldName",value = "要查询的字段名",dataType = "String",required = true),
            @ApiImplicitParam(name = "fieldValue",value = "字段值",dataType = "String",required = true)
    })
    @GetMapping("all/select/{currentPage}/{pageSize}/{fieldName}/{fieldValue}")
    public PublicProperty<Page<Cfproblem>> selectByField(@PathVariable("currentPage") Integer currentPage,
                                                         @PathVariable("pageSize") Integer pageSize,
                                                         @PathVariable("fieldName") String fieldName,
                                                         @PathVariable("fieldValue") String fieldValue) {

        Page<Cfproblem> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Cfproblem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(fieldName, fieldValue);
        Page<Cfproblem> resultPage = cfproblemService.page(page, queryWrapper);
        return new PublicProperty<>(200, "success", resultPage);
        /**
         QueryWrapper<Cfproblem> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq(fieldName, fieldValue);
         List<Cfproblem> resultList = cfproblemService.list(queryWrapper);
         return new PublicProperty<>(200, "success", resultList);
         */
    }

}
