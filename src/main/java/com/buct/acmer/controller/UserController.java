package com.buct.acmer.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.buct.acmer.entity.PublicProperty;
import com.buct.acmer.entity.Student;
import com.buct.acmer.entity.User;
import com.buct.acmer.mapper.StudentMapper;
import com.buct.acmer.mapper.UserMapper;
import com.buct.acmer.service.impl.StudentServiceImpl;
import com.buct.acmer.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author BUCT
 * @since 2024-07-15
 */
@Api(tags = "user信息")
@RestController
@RequestMapping("/acmer/user")
public class UserController {

    @Resource
    private UserServiceImpl userService;
    @Autowired
    private UserMapper userMapper;

    //没啥用
    @ApiOperation("查询用户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage",value = "当前页数",required = true),
            @ApiImplicitParam(name = "pageSize",value = "页面大小",required = true)
    })
    @GetMapping("/some/{currentPage}/{pageSize}")
    public PublicProperty<Page<User>> selectsome(@PathVariable("currentPage") Integer currentPage,
                                                    @PathVariable("pageSize") Integer pageSize){

        Page<User> page = new Page<>(currentPage,pageSize);
        return new PublicProperty(200,"success",userService.page(page));
    }

    @ApiOperation("新增用户信息")
    @PostMapping("/insert")
    public PublicProperty insert(@RequestBody User user){

        User exist = userService.getById(user.getUsername());
        if(exist == null){
            boolean save = userService.save(user);
            if(save){
                return new PublicProperty(200,"success",null);
            }else{
                return new PublicProperty(400,"failed",null);
            }
        }else {
            return new PublicProperty(400,"user exist",null);
        }
    }
}
