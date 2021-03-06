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
     * @description: ?????????
     * ????????????????????????selectPage??????????????????
     * ????????????????????????jar?????????????????????????????????????????????cn.king.demo01.config.MybatisPlusConfig#paginationInterceptor()
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
     * @description: ?????????
     * ???name??????????????????????????? name????????????age???20????????????123456@163.com
     */
    @Test
    public void test15(){
        User user = User.builder()
                .name("??????")
                .age(20)
                .email("123456@163.com").build();

        int i = userMapper.update(user, Wrappers.<User>lambdaQuery().eq(User::getName, "??????"));
        System.out.println(i);

        System.out.println(userMapper.selectById(6));
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 19:35
     * @param:
     * @return: void
     * @description: ???????????????????????????????????????????????????????????????????????????id?????????????????????
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
     * @description: ???????????????????????????
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
     * @description: ????????????
     */
    @Test
    public void test12() {
        // LIKE '%???%'
        userMapper.selectList(Wrappers.<User>lambdaQuery().like(User::getName, "???")).forEach(System.out::println);
        log.info("------------------------");

        userMapper.selectList(Wrappers.<User>lambdaQuery().likeLeft(User::getName, "???")).forEach(System.out::println);
        log.info("------------------------");

        userMapper.selectList(Wrappers.<User>lambdaQuery().likeRight(User::getName, "???")).forEach(System.out::println);
        log.info("------------------------");

    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 19:21
     * @param:
     * @return: void
     * @description: ??????????????????
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
     * @description: ????????????
     */
    @Test
    public void test10() {
        // ????????????lambdaQuery()??????????????????????????????lambda
        // ??????age????????????
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
     * @description: ??????????????????
     */
    @Test
    public void test09() {
        //userMapper.selectList(Wrappers.lambdaQuery().eq())
        List<User> users = userMapper.selectList(Wrappers.<User>query().eq(true, "name", "??????"));
        users.forEach(System.out::println);

        log.info("--------------------------");

        // ???????????????????????????false??????????????????sql???????????????????????????????????????????????????null????????????false????????????
        List<User> userList = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(true, User::getAge, 30));
        userList.forEach(System.out::println);

        log.info("--------------------------");

        // ?????????????????????????????????????????????????????????true
        List<User> selectList = userMapper.selectList(Wrappers.<User>lambdaQuery().eq(User::getAge, 30));
        selectList.forEach(System.out::println);

        log.info("--------------------------");

    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 17:01
     * @param:
     * @return: void
     * @description: ?????????????????????
     * ?????????EntityWrapper ??? Condition ???????????? Wrapper???????????????????????????
     * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
     * ????????????EntityWrapper???
     */
    @Test
    public void test08() {

        List<User> users = userMapper.selectList(new QueryWrapper<User>().eq("name", "??????"));
        users.forEach(System.out::println);
        log.info("------------------------------");

        // SELECT id,name,age,email FROM tb_user WHERE (name = ? AND age = ?)
        List<User> users1 = userMapper.selectList(new QueryWrapper<User>().eq("name", "??????").eq("age", 100));
        users1.forEach(System.out::println);

        log.info("-------????????????1------------------------");
        List<User> users2 = userMapper.selectList(Wrappers.emptyWrapper());
        users2.forEach(System.out::println);
        log.info("-------????????????2----??????low--------------------");
        //List<User> users3 = userMapper.selectList(new QueryWrapper<>());
        //users3.forEach(System.out::println);

    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 17:00
     * @param:
     * @return: void
     * @description: ??????id????????????
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
     * @description: ??????id????????????
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
     * @description: ???????????????
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
     * @description: ???????????????
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
     * @description: ????????????id????????????
     */
    @Test
    public void test02() {

        User user = User.builder()
                .id(1L)
                .name("lisi")
                .age(30)
                .email("456@qq.com").build();

        // ?????????????????????????????????id????????????????????????????????????????????????????????????0
        int i = userMapper.updateById(user);
        System.out.println(i);

        System.out.println(userMapper.selectById(1));
    }

    /**
     * @author: wjl@king.cn
     * @createTime: 2020/8/10 13:55
     * @param:
     * @return: void
     * @description: ??????mp?????????????????????
     */
    @Test
    public void test01() {
        User user = User.builder()
                .name("zhangsan")
                .age(20)
                .email("123@qq.com").build();
        int i = userMapper.insert(user);
        // ????????????????????????
        System.out.println(i);
        System.out.println(user);
    }


    @Test
    void contextLoads() {
    }

}
