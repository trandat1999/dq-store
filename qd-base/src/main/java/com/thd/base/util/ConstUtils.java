package com.thd.base.util;

public class ConstUtils {
    public static final String API = "api";
    public static final String SLASH = "/";
    public static final String DOT = ".";
    public static final String COMMA = ",";
    public static final String SPACE = " ";
    public static final String UNDERLINE = "_";
    public static final String SEMICOLON = ";";
    public static final String EQUAL = "=";
    public static final String QUESTION_MARK = "?";
    public static final String AT = "@";
    public static final String PLUS = "+";
    public static final String COLON = ":";
    public static final String DASH = "-";
    public static final String XLSX = "xlsx";
    public static final String XLS = "xls";
    public static final String DOT_XLSX = ".xlsx";
    public static final String DOT_XLS = ".xls";

    public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
    public static final String DATE_FORMAT_DMY = "dd/MM/yyyy";
    public static final String DATE_FORMAT_YMD = "yyyy/MM/dd";
    public static final String DATE_FORMAT_DMYHMS = "dd/MM/yyyy HH:mm:ss";
    public static final String DATE_FORMAT_DMYHM = "dd/MM/yyyy HH:mm";
    public static final String APPLICATION_JSON = "application/json";
    public static final String UTF8 = "UTF-8";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String TEMP_UUID = "00000000-0000-0000-0000-000000000000";
    public static final String AUDITOR_AWARE = "auditorAware";
    public static final String TASK_EXECUTOR_BEAN_NAME = "taskExecutor";
    public static final String PREFIX_NAME_TASK_EXECUTOR = "ThreadPoolTaskExecutor-";
    public static final String SCHEDULING_BEAN_NAME = "scheduling";
    public static final String PREFIX_NAME_SCHEDULING = "ThreadPoolTaskScheduler-";
    public static final String URI_LOGOUT = "/api/auth/logout";
    public static final String ADMIN_USERNAME_ROOT = "admin_root";
    public static final String ADMIN_PASSWORD_ROOT = "password_root";

    public static final String[] WHITE_LIST_ROOT = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
}
