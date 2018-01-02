package zalezone.retrofitlibrary.model;

import java.util.List;

/**
 * Created by zale on 2017/3/7.
 */

public class RefundModel {

    public String productName; //商品名称，目前对应专辑名

    public String payOrderNo; //订单编号

    public long purchaseTime; //购买时间

    public String refundAmount; // 退款金额

    public List<String> reasons;


    public long refundId; //对应退款申请的Id

    public int statusId; //退款状态：1 – 待退款；2 – 退款成功；3 – 已取消

    public long createTime;//申请退款时间

    public long lastUpdateTime;//更新时间：若状态为已成功，则表示成功退款时间。若状态为已取消，则表示取消时间

    public String reason;//退款理由

    public String refundType; //退款方式

    @Override
    public String toString() {
        return "RefundModel{" +
                "productName='" + productName + '\'' +
                ", payOrderNo='" + payOrderNo + '\'' +
                ", purchaseTime=" + purchaseTime +
                ", refundAmount='" + refundAmount + '\'' +
                ", reasons=" + reasons +
                ", refundId=" + refundId +
                ", statusId=" + statusId +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                ", reason='" + reason + '\'' +
                ", refundType='" + refundType + '\'' +
                '}';
    }
}
