package com.buct.acmer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.Cfrating;
import com.buct.acmer.entity.Cfsubmission;
import com.buct.acmer.entity.PublicProperty;
import com.buct.acmer.entity.Student;
import com.buct.acmer.mapper.CfsubmissionMapper;
import com.buct.acmer.mapper.StudentMapper;
import com.buct.acmer.service.impl.CfsubmissionServiceImpl;
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
@Api(tags = "cfSubmission")
@RestController
@RequestMapping("/acmer/cfsubmission")
public class CfsubmissionController {
    @Resource
    private CfsubmissionServiceImpl cfsubmissionService;
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private CfsubmissionMapper cfsubmissionMapper;

    //图5
    @ApiOperation("按比赛名称查询某场cf竞赛学生参赛数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",dataType = "int",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",dataType = "int",required = true),
            @ApiImplicitParam(name = "fieldValue",value = "比赛名称",dataType = "String",required = true)
    })
    @GetMapping("/patici/{fieldValue}/{currentPage}/{pageSize}")
    public PublicProperty<Page<Cfsubmission>> selectParticipants(@PathVariable("currentPage") Integer currentPage,
                                                             @PathVariable("pageSize") Integer pageSize,
                                                             @PathVariable("fieldValue") String fieldValue) {

        Page<Cfsubmission> page = cfsubmissionService.selectSubmissionsByContestName(currentPage, pageSize,fieldValue);
        return new PublicProperty(200, "success", page);
    }

    @ApiOperation("查询全部提交信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",required = true)
    })
    @GetMapping("/all/{currentPage}/{pageSize}")
    public PublicProperty<Page<Cfsubmission>> selectall(@PathVariable("currentPage") Integer currentPage,
                                                    @PathVariable("pageSize") Integer pageSize){

        Page<Cfsubmission> page = cfsubmissionService.selectAllSubmissions(currentPage, pageSize);
        return new PublicProperty(200,"success",page);
    }


}
