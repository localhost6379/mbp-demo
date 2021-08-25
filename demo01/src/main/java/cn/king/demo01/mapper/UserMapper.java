package cn.king.demo01.mapper;

import cn.king.demo01.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author: wjl@king.cn
 * @time: 2020/8/10 13:43
 * @version: 1.0.0
 * @description:
 */
public interface UserMapper extends BaseMapper<User> {

    List<User> getList();

}
