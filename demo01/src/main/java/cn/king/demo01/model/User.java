package cn.king.demo01.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
// 此处必须指定表名，否则使用类名小写
@TableName(value = "tb_user")
public class User {

    /**
     * 注意一定要使用@TableName注解和@TableId注解。
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String email;
}