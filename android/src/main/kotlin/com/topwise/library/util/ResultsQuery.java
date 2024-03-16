//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.topwise.library.util;

import java.util.HashMap;
import java.util.Map;

public class ResultsQuery {
    private final Map<String, ResultsBean> map = new HashMap();
    private ResultsBean resultsBean = new ResultsBean("承兑或交易成功", "A", "承兑或交易成功", "交易成功");
    private ResultsBean resultsValue;
    private static final ResultsQuery resultsQuery = new ResultsQuery();

    public static ResultsQuery getInstance() {
        return resultsQuery;
    }

    public ResultsQuery() {
        this.map.put("00", this.resultsBean);
        this.resultsBean = new ResultsBean("查发卡行", "C", "查发卡行", "请持卡人与发卡银行联系");
        this.map.put("01", this.resultsBean);
        this.resultsBean = new ResultsBean("无效商户", "D", "操作员没收卡", "此卡被没收");
        this.map.put("03", this.resultsBean);
        this.resultsBean = new ResultsBean("没收卡", "A", "承兑或交易成功", "交易成功");
        this.map.put("04", this.resultsBean);
        this.resultsBean = new ResultsBean("身份认证失败", "C", "发卡不予承兑，预约信息匹配失败", "持卡人认证失败");
        this.map.put("05", this.resultsBean);
        this.resultsBean = new ResultsBean("部分承兑", "A", "部分金额批准，请收取余额", "显示部分批准金额，提示操作员");
        this.map.put("10", this.resultsBean);
        this.resultsBean = new ResultsBean("重要人物批准（VIP）", "A", "此为VIP客户", "成功，VIP客户");
        this.map.put("11", this.resultsBean);
        this.resultsBean = new ResultsBean("无效的关联交易", "C", "发卡行不支持的交易", "无效交易");
        this.map.put("12", this.resultsBean);
        this.resultsBean = new ResultsBean("无效金额", "B", "金额为0或其他非法值", "无效金额");
        this.map.put("13", this.resultsBean);
        this.resultsBean = new ResultsBean("无效卡号（无此账号）", "B", "卡种未在中心登记或读卡号有误", "无效卡号");
        this.map.put("14", this.resultsBean);
        this.resultsBean = new ResultsBean("无此发卡方", "C", "此发卡行未与中心开通业务", "此卡无对应发卡方");
        this.map.put("15", this.resultsBean);
        this.resultsBean = new ResultsBean("卡未初始化", "C", "1、该卡未激活、开卡；\n2、该卡初始密码未变更；\n3、初始密码限制的交易；\n4、长期未使用而冻结或状态为“睡眠”的卡。", "该卡未初始化或睡眠卡");
        this.map.put("21", this.resultsBean);
        this.resultsBean = new ResultsBean("故障怀疑，关联交易错误", "C", "POS状态与中心不符，可重新签到", "操作有误，或超出交易允许天数");
        this.map.put("22", this.resultsBean);
        this.resultsBean = new ResultsBean("找不到原始交易", "C", "发卡行未能找到有关记录", "没有原始交易，请联系发卡方");
        this.map.put("25", this.resultsBean);
        this.resultsBean = new ResultsBean("报文格式错误", "C", "格式错误（不符合磁道预校验规则）", "请重试");
        this.map.put("30", this.resultsBean);
        this.resultsBean = new ResultsBean("有作弊嫌疑", "D", "有作弊嫌疑的卡，操作员可以没收", "作弊卡,吞卡");
        this.map.put("34", this.resultsBean);
        this.resultsBean = new ResultsBean("超过允许的PIN试输入", "D", "密码错次数超限，操作员可以没收", "密码错误次数超限，请与发卡方联系");
        this.map.put("38", this.resultsBean);
        this.resultsBean = new ResultsBean("请求的功能尚不支持", "C", "发卡行不支持的交易类型", "发卡方不支持的交易类型");
        this.map.put("40", this.resultsBean);
        this.resultsBean = new ResultsBean("挂失卡", "D", "挂失的卡， 操作员可以没收", "挂失卡，请没收（POS）");
        this.map.put("41", this.resultsBean);
        this.resultsBean = new ResultsBean("被窃卡", "D", "被窃卡， 操作员可以没收", "被窃卡，请没收");
        this.map.put("43", this.resultsBean);
        this.resultsBean = new ResultsBean("资金不足", "C", "账户内余额不足", "可用余额不足");
        this.map.put("51", this.resultsBean);
        this.resultsBean = new ResultsBean("过期的卡", "C", "过期的卡", "该卡已过期");
        this.map.put("54", this.resultsBean);
        this.resultsBean = new ResultsBean("不正确的PIN", "C", "密码输错", "密码错");
        this.map.put("55", this.resultsBean);
        this.resultsBean = new ResultsBean("不允许持卡人进行的交易", "C", "不允许持卡人进行的交易", "不允许此卡交易");
        this.map.put("57", this.resultsBean);
        this.resultsBean = new ResultsBean("不允许终端进行的交易", "C", "该商户不允许进行的交易", "发卡方不允许该卡在本终端进行此交易");
        this.map.put("58", this.resultsBean);
        this.resultsBean = new ResultsBean("有作弊嫌疑", "C", "CVN验证失败", "卡片校验错");
        this.map.put("59", this.resultsBean);
        this.resultsBean = new ResultsBean("超出金额限制", "C", "一次交易的金额太大", "交易金额超限");
        this.map.put("61", this.resultsBean);
        this.resultsBean = new ResultsBean("受限制的卡", "C", "", "受限制的卡");
        this.map.put("62", this.resultsBean);
        this.resultsBean = new ResultsBean("原始金额错误", "C", "原始金额不正确", "交易金额与原交易不匹配");
        this.map.put("64", this.resultsBean);
        this.resultsBean = new ResultsBean("超出消费次数限制", "C", "超出消费次数限制", "超出消费次数限制");
        this.map.put("65", this.resultsBean);
        this.resultsBean = new ResultsBean("发卡行响应超时", "C", "发卡行规定时间内没有回答", "交易超时，请重试");
        this.map.put("68", this.resultsBean);
        this.resultsBean = new ResultsBean("允许的输入PIN次数超限", "C", "允许的输入PIN次数超限", "密码错误次数超限");
        this.map.put("75", this.resultsBean);
        this.resultsBean = new ResultsBean("正在日终处理", "C", "日期切换正在处理", "系统日切，请稍后重试");
        this.map.put("90", this.resultsBean);
        this.resultsBean = new ResultsBean("发卡方不能操作", "C", "电话查询发卡方，可重作", "发卡方状态不正常，请稍后重试");
        this.map.put("91", this.resultsBean);
        this.resultsBean = new ResultsBean("金融机构或中间网络设施找不到或无法达到", "C", "电话查询发卡方或网络中心，可重作", "发卡方线路异常，请稍后重试");
        this.map.put("92", this.resultsBean);
        this.resultsBean = new ResultsBean("重复交易", "C", "查询网络中心，可能是一笔已经成功上送的交易", "拒绝，重复交易，请稍后重试");
        this.map.put("94", this.resultsBean);
        this.resultsBean = new ResultsBean("处理中心系统异常、失效", "C", "发卡方或网络中心出现故障", "拒绝，交换中心异常，请稍后重试");
        this.map.put("96", this.resultsBean);
        this.resultsBean = new ResultsBean("POS终端号找不到", "D", "终端未在中心或银行登记", "终端未登记");
        this.map.put("97", this.resultsBean);
        this.resultsBean = new ResultsBean("银联处理中心收不到发卡方应答", "E", "银联收不到发卡行应答", "发卡方超时");
        this.map.put("98", this.resultsBean);
        this.resultsBean = new ResultsBean("PIN格式错", "B", "可重新签到作交易", "PIN格式错，请重新签到");
        this.map.put("99", this.resultsBean);
        this.resultsBean = new ResultsBean("MAC鉴别失败", "B", "可重新签到作交易", "MAC校验错，请重新签到");
        this.map.put("A0", this.resultsBean);
        this.resultsBean = new ResultsBean("转账货币不一致", "C", "转账货币不一致", "转账货币不一致");
        this.map.put("A1", this.resultsBean);
        this.resultsBean = new ResultsBean("有缺陷的成功", "A", "银联处理中心转发了原充值交易请求，但未收到发卡方应答时，银联处理中心直接向受理方应答为有缺陷的成功交易", "交易成功，请向发卡行确认");
        this.map.put("A2", this.resultsBean);
        this.resultsBean = new ResultsBean("资金到账行无此账户", "C", "资金到账行账号不正确", "账户不正确");
        this.map.put("A3", this.resultsBean);
        this.resultsBean = new ResultsBean("有缺陷的成功", "A", "未收到原充值交易请求时，对关联的确认交易的承兑为有缺陷的成功交易", "交易成功，请向发卡行确认");
        this.map.put("A4", this.resultsBean);
        this.resultsBean = new ResultsBean("有缺陷的成功", "A", "原充值交易为拒绝时，对关联的确认交易的承兑为有缺陷的成功交易", "交易成功，请向发卡行确认");
        this.map.put("A5", this.resultsBean);
        this.resultsBean = new ResultsBean("有缺陷的成功", "A", "银联处理中心转发了原充值交易请求，但未收到发卡方应答时，对受理方发来的关联的确认交易的承兑为有缺陷的成功交易", "交易成功，请向发卡行确认");
        this.map.put("A6", this.resultsBean);
        this.resultsBean = new ResultsBean("安全处理失败", "C", "1、调用MAC校验程序失败\n2、调用PIN校验程序失败\n3、MAC处理失败\n4、密钥处理失败", "拒绝，交换中心异常，请稍后重试");
        this.map.put("A7", this.resultsBean);
        this.resultsBean = new ResultsBean("短信验证码输入跳转通知", "C", "交易流程需跳转到录入短信验证码界面", "");
        this.map.put("YM", this.resultsBean);
        this.resultsBean = new ResultsBean("支付开通跳转通知", "C", "交易流程需跳转至录入开通信息的界面", "");
        this.map.put("YT", this.resultsBean);
        this.resultsBean = new ResultsBean("支付开通跳转通知", "C", "交易流程需跳转至录入开通信息的界面", "");
        this.map.put("YT", this.resultsBean);
    }

    public ResultsBean getResultsBean(String rescode) {
        if (this.map.containsKey(rescode)) {
            this.resultsValue = (ResultsBean)this.map.get(rescode);
        } else {
            this.resultsValue = new ResultsBean("交易失败", "交易失败", "交易失败", "交易失败");
        }

        return this.resultsValue;
    }
}
