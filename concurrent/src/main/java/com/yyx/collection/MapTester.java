package com.yyx.collection;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MapTester {

    public static void main(String[] args) {
        String emailToAddress = "yinyx2@lenovo.com";
        String emailHeader = ":InventoryApiRecallMessageConsumer recall failed";
        StringBuilder emailStringBuilder=new StringBuilder();
        emailStringBuilder.append("InventoryApiRecallMessageConsumer 接收到的message:").append(System.getProperty("line.separator"));
        emailStringBuilder.append("message").append(System.getProperty("line.separator"));
        emailStringBuilder.append("fail Reason:").append(System.getProperty("line.separator"));
        emailStringBuilder.append("failReason");
        System.out.println(emailStringBuilder.toString());
     }

    public static class InventoryInfo implements Serializable {
        private static final long serialVersionUID = 4715264953435392960L;
        /**
         * 产品编号
         */
        private String productCode;

        /**
         * 活动类型	非活动产品时为空即可
         */
        private String activityType;
        /**
         * 库存状态,1：售卖中，  2：已售罄， 3：无库存信息
         */
        private Integer status;
        /**
         * 库存剩余数量
         */
        private Integer currentNum;

        /**
         * 显示到页面的库存剩余数量描述信息
         */
        private String inventoryShowMessage;

        /**
         * 显示到页面的库存剩余数量描述信息
         */
        private String translatedInventoryShowMessage;

        private Map<String, String> translatedInventoryShowMessageMap = new HashMap<>();

        /**
         * 仓库类型
         */
        private Integer sourceType;

        /**
         * 仓库编号
         */
        private String source;

        /**
         * 预期发货天数	单位：天
         */

        private Integer leadTime;

        /**
         * leadtime显示信息
         */
        private String leadTimeMessage;

        /**
         * leadtime显示信息
         */
        private String translatedLeadTimeMessage;

        private Map<String, String> translatedLeadTimeMessageMap = new HashMap<>();

        /**
         * 预计发货时间
         */
        private Date psd;

        /**
         * 北京时间预计发货日期
         */
        private String psdLeadTime;
        /**
         * 站点本地时间预计发货日期
         */
        private String localPsdLeadTime;

        /**
         * 获取leadtime失败原因类型,获取leadtime失败时才有值，参见NoPsdReasonTypeEnum
         */
        private Integer noPsdReasonType;

        /**
         * 没有跳过节假日的psd日期（hybris使用）
         */
        private String noneSkipPsdDate4Hybris;
        /**
         * 起始的psd日期（hybris使用）
         */
        private String startPsdDate4Hybris;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }


        public String getProductCode() {
            return productCode;
        }

        public void setProductCode(String productCode) {
            this.productCode = productCode;
        }

        public Integer getCurrentNum() {
            return currentNum;
        }

        public void setCurrentNum(Integer currentNum) {
            this.currentNum = currentNum;
        }

        public String getInventoryShowMessage() {
            return inventoryShowMessage;
        }

        public void setInventoryShowMessage(String inventoryShowMessage) {
            this.inventoryShowMessage = inventoryShowMessage;
        }

        public Integer getLeadTime() {
            return leadTime;
        }

        public void setLeadTime(Integer leadTime) {
            this.leadTime = leadTime;
        }

        public String getLeadTimeMessage() {
            return leadTimeMessage;
        }

        public void setLeadTimeMessage(String leadTimeMessage) {
            this.leadTimeMessage = leadTimeMessage;
        }

        public Date getPsd() {
            return psd;
        }

        public void setPsd(Date psd) {
            this.psd = psd;
        }

        public String getPsdLeadTime() {
            return psdLeadTime;
        }

        public void setPsdLeadTime(String psdLeadTime) {
            this.psdLeadTime = psdLeadTime;
        }

        public String getLocalPsdLeadTime() {
            return localPsdLeadTime;
        }

        public void setLocalPsdLeadTime(String localPsdLeadTime) {
            this.localPsdLeadTime = localPsdLeadTime;
        }

        public Integer getNoPsdReasonType() {
            return noPsdReasonType;
        }

        public void setNoPsdReasonType(Integer noPsdReasonType) {
            this.noPsdReasonType = noPsdReasonType;
        }

        public String getNoneSkipPsdDate4Hybris() {
            return noneSkipPsdDate4Hybris;
        }

        public void setNoneSkipPsdDate4Hybris(String noneSkipPsdDate4Hybris) {
            this.noneSkipPsdDate4Hybris = noneSkipPsdDate4Hybris;
        }

        public String getStartPsdDate4Hybris() {
            return startPsdDate4Hybris;
        }

        public void setStartPsdDate4Hybris(String startPsdDate4Hybris) {
            this.startPsdDate4Hybris = startPsdDate4Hybris;
        }

        public Integer getSourceType() {
            return sourceType;
        }

        public void setSourceType(Integer sourceType) {
            this.sourceType = sourceType;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTranslatedInventoryShowMessage() {
            return translatedInventoryShowMessage;
        }

        public void setTranslatedInventoryShowMessage(String translatedInventoryShowMessage) {
            this.translatedInventoryShowMessage = translatedInventoryShowMessage;
        }

        public String getTranslatedLeadTimeMessage() {
            return translatedLeadTimeMessage;
        }

        public void setTranslatedLeadTimeMessage(String translatedLeadTimeMessage) {
            this.translatedLeadTimeMessage = translatedLeadTimeMessage;
        }

        public String getActivityType() {
            return activityType;
        }

        public void setActivityType(String activityType) {
            this.activityType = activityType;
        }

        public Map<String, String> getTranslatedLeadTimeMessageMap() {
            return translatedLeadTimeMessageMap;
        }

        public void setTranslatedLeadTimeMessageMap(Map<String, String> translatedLeadTimeMessageMap) {
            this.translatedLeadTimeMessageMap = translatedLeadTimeMessageMap;
        }

        public Map<String, String> getTranslatedInventoryShowMessageMap() {
            return translatedInventoryShowMessageMap;
        }

        public void setTranslatedInventoryShowMessageMap(Map<String, String> translatedInventoryShowMessageMap) {
            this.translatedInventoryShowMessageMap = translatedInventoryShowMessageMap;
        }

        @Override
        public String toString() {
            return "InventoryInfo [productCode=" + productCode + ", activityType=" + activityType
                    + ", status=" + status + ", currentNum=" + currentNum
                    + ", inventoryShowMessage=" + inventoryShowMessage
                    + ", translatedInventoryShowMessageMap=" + translatedInventoryShowMessageMap
                    + ", sourceType=" + sourceType
                    + ", source=" + source
                    + ", leadTime=" + leadTime
                    + ", leadTimeMessage=" + leadTimeMessage
                    + ", translatedLeadTimeMessage=" + translatedLeadTimeMessage
                    + ", translatedLeadTimeMessageMap=" + translatedLeadTimeMessageMap
                    + ", psd=" + psd
                    + ", psdLeadTime=" + psdLeadTime
                    + ", localPsdLeadTime=" + localPsdLeadTime
                    + ", noPsdReasonType=" + noPsdReasonType
                    + ", noneSkipPsdDate4Hybris=" + noneSkipPsdDate4Hybris
                    + ", startPsdDate4Hybris=" + startPsdDate4Hybris
                    + "]";
        }

    }

}
