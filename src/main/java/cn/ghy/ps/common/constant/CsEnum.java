package cn.ghy.ps.common.constant;

/**
 * 枚举常量
 * @author 穹鏡
 */
public interface CsEnum{


    /**
     * 用户头像默认
     */
    enum avatar{
        USER_AVATAR("/img/commonuserimg.jpg", "用户默认头像");
        private String value;
        private String descr;

        avatar(String value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public String getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }
    }


    /**
     * 用户常量
     */
    enum user{
        USER_USER_BLOCKED(1, "用户被锁定"), USER_IS_OK(0, "用户正常");
        private int value;
        private String descr;

        user(int value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public int getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (user u : values())
            {
                if (u.getValue() == value)
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }

    /**
     * 唯一值验证
     */
    enum unique{
        IS_UNIQUE("0", "唯一"), NOT_UNIQUE("1", "不是唯一值");
        private String value;
        private String descr;

        unique(String value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public String getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (unique u : values())
            {
                if (u.getValue().equals(value))
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }

    /**
     * 工作日程
     */
    enum scheduled{
        SCHEDULE_NO_COMPLETE(0, "未完成"), SCHEDULE_YES_COMPLETE(1, "已完成");
        private int value;
        private String descr;

        scheduled(int value, String descr)
        {
            this.value = value;
            this.descr = descr;
        }

        public int getValue()
        {
            return value;
        }


        public String getDescr()
        {
            return descr;
        }

        //通过value获取msg
        public static String getMsg(int value)
        {
            for (scheduled u : values())
            {
                if (u.getValue() == value)
                {
                    return u.getDescr();
                }
            }
            return null;
        }
    }

    /**
     * 菜单默认父id
     */
    enum menu{
        MENU_PID(0, "顶级菜单父Id默认为0"),
        MENU_TYPE_ONE(1,"根目录，一级菜单"),
        MENU_TYPE_TWO(2,"菜单，二级菜单"),
        MENU_TYPE_THREE(3,"按钮");
        private int value;
        private String msg;

        menu(int value, String msg)
        {
            this.value = value;
            this.msg = msg;
        }

        public int getValue()
        {
            return value;
        }

        public String getMsg()
        {
            return msg;
        }
    }

}
