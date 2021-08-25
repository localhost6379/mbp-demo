package cn.king.demo01;

import cn.king.demo01.mapper.UserMapper;
import cn.king.demo01.model.Item;
import cn.king.demo01.model.ItemVO;
import cn.king.demo01.model.User;
import cn.king.demo01.util.CommonConverterUtils;
import cn.king.demo01.util.ItemConverter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootTest
class Demo01ApplicationTests {

    @Resource
    private UserMapper userMapper;


    @Test
    public void test100() throws Exception{
         List<Item> itemList = Arrays.asList(
                Item.builder().strNum("10").build(),
                Item.builder().strNum("20").build(),
                Item.builder().strNum("30").build(),
                Item.builder().strNum("40").build(),
                Item.builder().strNum("50").build(),
                Item.builder().strNum("70").build(),
                Item.builder().strNum("26").build(),
                Item.builder().strNum("16").build(),
                Item.builder().strNum("27").build(),
                Item.builder().strNum("12").build(),
                Item.builder().strNum("23").build(),
                Item.builder().strNum("34").build()
        );

        final List<ItemVO> list = CommonConverterUtils.toList(itemList, ItemConverter::item2itemVO);
        list.forEach(System.out::println);

        //itemList.forEach(System.out::println);

        // List<String> stringList = CommonConverterUtils.toDistinctList(itemList, Item::getStrNum);
        ////stringList.forEach(System.out::println);
        //
        //final LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery(User.class)
        //        .in(User::getAge, CommonConverterUtils.toDistinctList(itemList, Item::getStrNum))
        //        .eq(User::getId, 1)
        //        .eq(User::getEmail, "123@qq.com");
        //final List<User> users = userMapper.selectList(wrapper);
        //users.forEach(System.out::println);
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 23:13
     * @param:
     * @return: void
     * @description: 分页。
     * 配了分页插件之后selectPage方法才有用。
     * 不需要引入额外的jar包，需要在配置类中配置插件，见cn.king.demo01.config.MybatisPlusConfig#paginationInterceptor()
     */
    @Test
    public void test16(){
        Page<User> page = userMapper.selectPage(new Page<User>(1, 2), Wrappers.<User>lambdaQuery().ge(User::getAge, 40));
        System.out.println(JSONObject.toJSONString(page));
        log.info("----------------------");
        Page<User> page2 = userMapper.selectPage(new Page<User>(2, 2), Wrappers.<User>lambdaQuery().ge(User::getAge, 40));
        System.out.println(JSONObject.toJSONString(page2));
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 23:10
     * @param:
     * @return: void
     * @description: 更新。
     * 将name为张三的数据修改为 name为李华，age为20，邮箱为123456@163.com
     */
    @Test
    public void test15(){
        User user = User.builder()
                .name("李华")
                .age(20)
                .email("123456@163.com").build();

        int i = userMapper.update(user, Wrappers.<User>lambdaQuery().eq(User::getName, "张三"));
        System.out.println(i);

        System.out.println(userMapper.selectById(6));
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 19:35
     * @param:
     * @return: void
     * @description: 只返回第一个字段的值。例如下面的语句只会返回所有的id列表。用不到。
     */
    @Test
    public void test14(){
        userMapper.selectObjs(null).forEach(System.out::println);
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 19:30
     * @param:
     * @return: void
     * @description: 这个应该几乎用不到
     */
    @Test
    public void test13(){
        List<Map<String, Object>> list = userMapper.selectMaps(Wrappers.<User>lambdaQuery());
        list.forEach(System.out::println);
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 19:25
     * @param:
     * @return: void
     * @description: 模糊查询
     */
    @Test
    public void test12() {
        // LIKE '%值%'
        userMapper.selectList(Wrappers.<User>lambdaQuery().like(User::getName, "三")).forEach(System.out::println);
        log.info("------------------------");

        userMapper.selectList(Wrappers.<User>lambdaQuery().likeLeft(User::getName, "三")).forEach(System.out::println);
        log.info("------------------------");

        userMapper.selectList(Wrappers.<User>lambdaQuery().likeRight(User::getName, "三")).forEach(System.out::println);
        log.info("------------------------");

    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 19:21
     * @param:
     * @return: void
     * @description: 测试大于小于
     */
    @Test
    public void test11() {
        userMapper.selectList(Wrappers.<User>lambdaQuery().le(User::getAge, 30)).forEach(System.out::println);
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 19:07
     * @param:
     * @return: void
     * @description: 测试排序
     */
    @Test
    public void test10() {
        // 注意使用lambdaQuery()后获取的对象必须使用lambda
        // 按照age进行升序
        List<User> users = userMapper.selectList(Wrappers.<User>lambdaQuery().orderByAsc(User::getAge));
        users.forEach(System.out::println);

        List<User> selectList = userMapper.selectList(Wrappers.<User>lambdaQuery().orderByAsc(User::getAge, User::getName));
        selectList.forEach(System.out::println);
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 19:07
     * @param:
     * @return: void
     * @description: 测试条件查询
     */
    @Test
    public void test09() {
        //userMapper.selectList(Wrappers.lambdaQuery().eq())
        List<User> users = userMapper.selectList(Wrappers.<User>query().eq(true, "name", "张三"));
        users.forEach(System.out::println);

        log.info("--------------------------");

        // 如果第一个参数传入false，那么最后的sql中不会有这个字段。适用于判断如果为null那么返回false的情况。
        List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(true, User::getAge, 30));
        userList.forEach(System.out::println);

        log.info("--------------------------");

        // 相当于上面的方法，然后第一个参数固定为true
        List<User> selectList = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getAge, 30));
        selectList.forEach(System.out::println);

        log.info("--------------------------");

    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 17:01
     * @param:
     * @return: void
     * @description: 使用条件构造器
     * 注意，EntityWrapper 和 Condition 都继承了 Wrapper，他们是兄弟关系，
     * 都是用来封装各种查询条件的条件构造器。封装条件时候使用的是数据库的字段名，而不是实体类的属性名。
     * 推荐使用EntityWrapper。
     */
    @Test
    public void test08() {

        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("name", "张三"));
        users.forEach(System.out::println);
        log.info("------------------------------");

        // SELECT id,name,age,email FROM tb_user WHERE (name = ? AND age = ?)
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().eq("name", "王五").eq("age", 100));
        users1.forEach(System.out::println);

        log.info("-------查询全部1------------------------");
        List<User> users2 = userMapper.selectList(Wrappers.emptyWrapper());
        users2.forEach(System.out::println);
        log.info("-------查询全部2----比较low--------------------");
        //List<User> users3 = userMapper.selectList(new QueryWrapper<>());
        //users3.forEach(System.out::println);

    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 17:00
     * @param:
     * @return: void
     * @description: 根据id批量查询
     */
    @Test
    public void test07() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        users.forEach(System.out::println);
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 16:58
     * @param:
     * @return: void
     * @description: 根据id批量删除
     */
    @Test
    public void test06() {
        int i = userMapper.deleteBatchIds(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println(i);
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 16:57
     * @param:
     * @return: void
     * @description: 多字段删除
     */
    @Test
    public void test05() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("age", 30);
        columnMap.put("name", "lisi");
        int i = userMapper.deleteByMap(columnMap);
        System.out.println(i);
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 16:56
     * @param:
     * @return: void
     * @description: 多字段查询
     */
    @Test
    public void test04() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("age", 30);
        columnMap.put("name", "lisi");
        List<User> users = userMapper.selectByMap(columnMap);
        users.forEach(System.out::println);
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 16:28
     * @param:
     * @return: void
     * @description:
     */
    @Test
    public void test03() {
        User user = userMapper.selectById(1);
        System.out.println(user);
        System.out.println("-----------------------");
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 13:55
     * @param:
     * @return: void
     * @description: 测试根据id更新数据
     */
    @Test
    public void test02() {

        User user = User.builder()
                .id(1L)
                .name("lisi")
                .age(30)
                .email("456@qq.com").build();

        // 如果传入的实体类中没有id属性，那么修改失败，不报错，返回影响行数0
        int i = userMapper.updateById(user);
        System.out.println(i);

        System.out.println(userMapper.selectById(1));
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 13:55
     * @param:
     * @return: void
     * @description: 测试mp新增并返回主键
     */
    @Test
    public void test01() {
        User user = User.builder()
                .name("zhangsan")
                .age(20)
                .email("123@qq.com").build();
        int i = userMapper.insert(user);
        // 返回的是影响条数
        System.out.println(i);
        System.out.println(user);
    }


    @Test
    void contextLoads() {
    }

}
