package com.buct.acmer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.entity.PublicProperty;
import com.buct.acmer.service.ICfratingService;
import com.buct.acmer.service.impl.CfratingServiceImpl;
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
@Api(tags = "cfrating变化")
@RestController
@RequestMapping("/acmer/cfrating")
public class CfratingController {

    @Resource
    private CfratingServiceImpl cfratingService;


    //图4,内置比赛名称,
    @ApiOperation("按比赛名称查询某场cf竞赛学生参赛数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "fieldValue",value = "比赛名称",dataType = "String",required = true)
    })
    @GetMapping("/patici/{fieldValue}/{currentPage}/{pageSize}")
    public PublicProperty<Page<Cfrating>> selectParticipants(@PathVariable("currentPage") Integer currentPage,
                                                             @PathVariable("pageSize") Integer pageSize,
                                                             @PathVariable("fieldValue") String fieldValue) {

        Page<Cfrating> page = cfratingService.selectParticipantsByContestName(currentPage, pageSize,fieldValue);
        return new PublicProperty(200, "success", page);
    }

    //图6,
    @ApiOperation("通过学生名查找该学生参加的比赛")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "fieldValue",value = "学生名",dataType = "String",required = true)
    })
    @GetMapping("/stuContest/{fieldValue}/{currentPage}/{pageSize}")
    public PublicProperty<Page<Cfrating>> selectStuContest(@PathVariable("currentPage") Integer currentPage,
                                                             @PathVariable("pageSize") Integer pageSize,
                                                             @PathVariable("fieldValue") String fieldValue) {

        Page<Cfrating> page = cfratingService.selectContestByStuName(currentPage, pageSize,fieldValue);
        return new PublicProperty(200, "success", page);
    }

}
