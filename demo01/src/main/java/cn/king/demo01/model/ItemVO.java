package cn.king.demo01.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: wjl@king.cn
 * @time: 2021/8/25 1:56
 * @version: 1.0.0
 * @description:
 * @why:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemVO {
    private String strNum;
}
