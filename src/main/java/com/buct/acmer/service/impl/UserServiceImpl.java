package com.buct.acmer.service.impl;

import com.buct.acmer.entity.User;
import com.buct.acmer.mapper.UserMapper;
import com.buct.acmer.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author BUCT
 * @since 2024-07-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
