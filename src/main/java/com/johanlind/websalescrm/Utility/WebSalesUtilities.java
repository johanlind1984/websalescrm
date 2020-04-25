package com.johanlind.websalescrm.Utility;

import com.johanlind.websalescrm.entity.User;

public class WebSalesUtilities {

    public static String getHeaderString(User user) {
        if(user.getAuthority().getAuthorityName().equals("ROLE_ADMIN")) {
            return "admin/admin-header.html";
        } else if(user.getAuthority().getAuthorityName().equals("ROLE_MANAGER")) {
            return "manager/manager-header.html";
        } else if(user.getAuthority().getAuthorityName().equals("ROLE_EMPLOYEE")) {
            return "employee/employee-header.html";
        }
        return null;
    }
}
