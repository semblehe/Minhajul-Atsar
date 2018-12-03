package com.atsar.minhajul.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelFcm {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("msg")
        @Expose
        private String msg;

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

}
