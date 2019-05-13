package com.uautotime.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Created by admin on 2019/4/25.
 */
public class Const {

    public static final String CURRENT_USER = "currentUser";

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";

    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("prcie_desc","price_asc");
    }
    public interface Role{
        int ROLE_CUSTOMER = 0;         //普通用户
        int ROLE_ADMIN = 1;            //管理员
    }

    public enum ProductStatusEnum{

        ON_SALE(1,"在线") ;
        private String value;
        private int code;
        ProductStatusEnum(String value,int code){
            this.value = value;
            this.code = code;
        }

        public String getValue() {
            return value;
        }

        public int getCode() {
            return code;
        }
    }
}
