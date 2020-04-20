package cn.lnexin.dingtalk.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 钉钉应用下单数据封装
 * 可以不用管,使用自己的就可以
 *
 * @author lnexin@aliyun.com
 * @since 2019-09-11
 */
public class Order implements Serializable {


    private static final long serialVersionUID = -581283561383339594L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 订单编号
     */
    private Long orderId;
    /**
     * 用户购买应用的SuiteKey
     */
    private String suiteKey;
    /**
     * 购买该应用企业的corpid
     */
    private String corpId;
    /**
     * 购买的商品码
     */
    private String goodsCode;
    /**
     * 购买的商品规格码
     */
    private String itemCode;
    /**
     * 购买的商品规格名称
     */
    private String itemName;
    /**
     * 购买的商品规格能服务的最多企业人数
     */
    private Integer maxPeople;
    /**
     * 购买的商品规格能服务的最少企业人数
     */
    private Integer minPeople;
    /**
     * 下单时间
     */
    private Long paidtime;
    /**
     * 该订单的服务到期时间
     */
    private Long serviceStopTime;
    /**
     * 订单支付费用，以分为单位
     */
    private Long payFee;
    /**
     * 订单创建来源，如果来自钉钉分销系统，则值为"DRP"
     */
    private String orderCreateSource;
    /**
     * 钉钉分销系统提单价，以分为单位
     */
    private Long nominalPayFee;
    /**
     * 折扣减免费用
     */
    private Long discountFee;
    /**
     * 订单折扣
     */
    private BigDecimal discount;
    /**
     * 钉钉分销系统提单的代理商的企业corpId
     */
    private String distributorCorpId;
    /**
     * 钉钉分销系统提单的代理商的企业名称
     */
    private String distributorCorpName;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSuiteKey() {
        return suiteKey;
    }

    public void setSuiteKey(String suiteKey) {
        this.suiteKey = suiteKey;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getMinPeople() {
        return minPeople;
    }

    public void setMinPeople(Integer minPeople) {
        this.minPeople = minPeople;
    }

    public Long getPaidtime() {
        return paidtime;
    }

    public void setPaidtime(Long paidtime) {
        this.paidtime = paidtime;
    }

    public Long getServiceStopTime() {
        return serviceStopTime;
    }

    public void setServiceStopTime(Long serviceStopTime) {
        this.serviceStopTime = serviceStopTime;
    }

    public Long getPayFee() {
        return payFee;
    }

    public void setPayFee(Long payFee) {
        this.payFee = payFee;
    }

    public String getOrderCreateSource() {
        return orderCreateSource;
    }

    public void setOrderCreateSource(String orderCreateSource) {
        this.orderCreateSource = orderCreateSource;
    }

    public Long getNominalPayFee() {
        return nominalPayFee;
    }

    public void setNominalPayFee(Long nominalPayFee) {
        this.nominalPayFee = nominalPayFee;
    }

    public Long getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Long discountFee) {
        this.discountFee = discountFee;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getDistributorCorpId() {
        return distributorCorpId;
    }

    public void setDistributorCorpId(String distributorCorpId) {
        this.distributorCorpId = distributorCorpId;
    }

    public String getDistributorCorpName() {
        return distributorCorpName;
    }

    public void setDistributorCorpName(String distributorCorpName) {
        this.distributorCorpName = distributorCorpName;
    }


    @Override
    public String toString() {
        return "Order{" +
                ", id=" + id +
                ", orderId=" + orderId +
                ", suiteKey=" + suiteKey +
                ", corpId=" + corpId +
                ", goodsCode=" + goodsCode +
                ", itemCode=" + itemCode +
                ", itemName=" + itemName +
                ", maxPeople=" + maxPeople +
                ", minPeople=" + minPeople +
                ", paidtime=" + paidtime +
                ", serviceStopTime=" + serviceStopTime +
                ", payFee=" + payFee +
                ", orderCreateSource=" + orderCreateSource +
                ", nominalPayFee=" + nominalPayFee +
                ", discountFee=" + discountFee +
                ", discount=" + discount +
                ", distributorCorpId=" + distributorCorpId +
                ", distributorCorpName=" + distributorCorpName +
                "}";
    }
}
