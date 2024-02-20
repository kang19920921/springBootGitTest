package cn.tedu.algorithm;

import lombok.Data;

@Data
public class Prize {
    private int id;//奖品id
    private String prize_name;//奖品名称
    private int prize_amount;//奖品（剩余）数量
    private int prize_weight;//奖品权重
}
