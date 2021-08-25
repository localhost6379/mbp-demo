package cn.king.demo01.util;

import cn.king.demo01.model.Item;
import cn.king.demo01.model.ItemVO;

/**
 * @author: wjl@king.cn
 * @time: 2021/8/25 2:18
 * @version: 1.0.0
 * @description:
 * @why:
 */
public class ItemConverter {
    public static ItemVO item2itemVO(Item item) {
        ItemVO itemVO = new ItemVO();
        itemVO.setStrNum(item.getStrNum());
        return itemVO;

    }
}
