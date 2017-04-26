package com.kxt.goldtransaction.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/26.
 */
import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Steven
 *
 */

public class XmlOpenBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private head   head;
    private body  body;

    public XmlOpenBean.body getBody() {
        return body;
    }

    public void setBody(XmlOpenBean.body body) {
        this.body = body;
    }

    public XmlOpenBean.head getHead() {
        return head;
    }

    public void setHead(XmlOpenBean.head head) {
        this.head = head;
    }

    public static class head implements Serializable {
        private String h_exch_code;
        private String h_bank_no;
        private String h_term_type;
        private String h_branch_id;
        private String h_teller_id;
        private String h_teller_id_1;
        private String h_teller_id_2;
        private String h_bk_seq_no;
        private String h_work_date;
        private String h_exch_date;
        private String h_rsp_msg;
        private String h_rsp_code;

        public String getH_rsp_msg() {
            return h_rsp_msg;
        }

        public void setH_rsp_msg(String h_rsp_msg) {
            this.h_rsp_msg = h_rsp_msg;
        }

        public String getH_rsp_code() {
            return h_rsp_code;
        }

        public void setH_rsp_code(String h_rsp_code) {
            this.h_rsp_code = h_rsp_code;
        }

        public String getH_exch_code() {
            return h_exch_code;
        }

        public void setH_exch_code(String h_exch_code) {
            this.h_exch_code = h_exch_code;
        }

        public String getH_bank_no() {
            return h_bank_no;
        }

        public void setH_bank_no(String h_bank_no) {
            this.h_bank_no = h_bank_no;
        }

        public String getH_term_type() {
            return h_term_type;
        }

        public void setH_term_type(String h_term_type) {
            this.h_term_type = h_term_type;
        }

        public String getH_branch_id() {
            return h_branch_id;
        }

        public void setH_branch_id(String h_branch_id) {
            this.h_branch_id = h_branch_id;
        }

        public String getH_teller_id() {
            return h_teller_id;
        }

        public void setH_teller_id(String h_teller_id) {
            this.h_teller_id = h_teller_id;
        }

        public String getH_teller_id_1() {
            return h_teller_id_1;
        }

        public void setH_teller_id_1(String h_teller_id_1) {
            this.h_teller_id_1 = h_teller_id_1;
        }

        public String getH_teller_id_2() {
            return h_teller_id_2;
        }

        public void setH_teller_id_2(String h_teller_id_2) {
            this.h_teller_id_2 = h_teller_id_2;
        }

        public String getH_bk_seq_no() {
            return h_bk_seq_no;
        }

        public void setH_bk_seq_no(String h_bk_seq_no) {
            this.h_bk_seq_no = h_bk_seq_no;
        }

        public String getH_work_date() {
            return h_work_date;
        }

        public void setH_work_date(String h_work_date) {
            this.h_work_date = h_work_date;
        }

        public String getH_exch_date() {
            return h_exch_date;
        }

        public void setH_exch_date(String h_exch_date) {
            this.h_exch_date = h_exch_date;
        }
    }

    public static class body implements Serializable {
        private record record;
        public static class record implements Serializable {
            private String branch_id;
            private String is_contain_self;

            public String getBranch_id() {
                return branch_id;
            }

            public void setBranch_id(String branch_id) {
                this.branch_id = branch_id;
            }

            public String getIs_contain_self() {
                return is_contain_self;
            }

            public void setIs_contain_self(String is_contain_self) {
                this.is_contain_self = is_contain_self;
            }
        }

        public body.record getRecord() {
            return record;
        }

        public void setRecord(body.record record) {
            this.record = record;
        }
    }

}

