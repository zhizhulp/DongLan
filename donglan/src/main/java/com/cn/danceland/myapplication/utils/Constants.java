package com.cn.danceland.myapplication.utils;

import com.cn.danceland.myapplication.BuildConfig;
import com.cn.danceland.myapplication.MyApplication;

public class Constants {
    public static final String PLATFORM = "1";//平台1：安卓，2：IOS,3：浏览器
    public static final int MAX_FRIEND_COUNT = 900;//最大好友数量
    public static int PER_PAGE_SIZE = 10;//每页数据有多少
    public static String TAG_DEF = "test_url";

    public static String getVersion() {
        return AppUtils.getVersionName(MyApplication.getContext());
    }

    public static String getPlatform() {
        return PLATFORM;
    }

    //  public static  String HOST_SERVICE = "http://192.168.1.96:8003/";//服务器地址
//   public static  String HOST_SERVICE = "http://wx.dljsgw.com/";//阿里云服务器地址
    // public static  String HOST_SERVICE = "http://coder-hc.oicp.io:57068/";//佳楠
//    public static String HOST_SERVICE = "http://192.168.1.138:8003/";//
    // public static final String HOST_SERVICE = "http://39.104.188.91:6003/";// 测试地址
//    public static String HOST_SERVICE = "http://192.168.1.66:8003/";//老高
//    public static String HOST_SERVICE = "http://192.168.1.78:8003/";//超哥
//   public static  String HOST_SERVICE = "http://39.104.188.91:6003/";//审核服务器
    // public static String HOST = "http://192.168.1.189:8003/";//杨爱鑫
    public static String HOST = BuildConfig.HOST;

    public static String plus(String half) {
        return HOST + half;
    }

    public static boolean DEV_CONFIG = BuildConfig.DEBUG;

    public static String APP_ID_UMENG = "5ba1ee11f1f5569f370000f7";//第三方AppId   友盟
    public static String APP_ID_WEIXIN = "wx530b17b3c2de2e0d";//第三方AppId   微信
    public static String APP_SECRET_WEIXIN = "a8887e732c374dbffc37cd1390cf8bce";//第三方AppSecret   微信
    public static String APP_ID_QQ_ZONE = "101506602";//第三方AppId   QQ
    public static String APP_SECRET_QQ_ZONE = "2f150fc09c11923ecd7574ecca211d30";//第三方AppSecret   QQ

    public static String DYNAMICCODE = "/employ/getDynamicCode";//动态验证码
    public static String CHECKUPDATE = "/versionLog/checkUpdate";//检查更新
    public static String GET_SMS_URL = "person/encode/";//获取验证码
    public static String REGISTER_URL = "person/register";//注册用户
    public static String REGISTER_AGREEMENT_URL = "http://img.dljsgw.com/protocol/app_register_protocol.html";//注册用户协议

    public static String LOGIN_URL = "person/login";//登录
    public static String QUERY_USER_DYN_INFO_URL = "/appDynMsg/findPersonDyn/";//查询用户动态相关资料
    //public static String QUERY_USERINFO_URL = HOST + "user/findOne/";//查询用户资料
    public static String RESET_PASSWORD_URL = "/person/updatePwd";//忘记密码
    public static String UNBIND_ANDREGISTER = "/person/unbindAndRegister";//解绑原手机号并创建新帐户
    public static String VERIFY_PHONEPASSWORD = "/person/verifyPhonePassword";//验证手机号与密码是否一致


    //   public static final String RESET_PASSWORD_URL = HOST + "user/updatePwd";//重置密码
    public static String SET_BASE_USERINFO_URL = "user/updateBase";//设置用户基本资料
    //public static String RESET_USERINFO_URL = HOST + "user/changeUserInformation";//重置用户资料
    public static String LOGIN_BY_PHONE_URL = "/person/loginNoPwd";//短信登录
    public static String FIND_PHONE_URL = "/person/queryPhone";//查询手机号是否存在
    public static String BIND_DEVICE_NOPWD_URL = "/person/bindDeviceNoPwd";//绑定新设备
    public static String BIND_DEVICE_URL = "/person/bindDevice";//绑定新设备
    public static String FORGET_PWD_URL = "/person/forgetPwd";//忘记密码
    public static String VALIDATE_IDENTITYCARD_URL = "/person/validateIdentityCard";//验证身份证号后六位

    public static String LOGOUT_URL = "/person/logout";//用户退出
    //public static String ZONE = HOST + "/zone";//城市区域
    public static String MODIFY_ZONE = "/user/modifyZoneCode";//修改区域
    public static String MODIFY_NAME = "user/modifyNickName";//修改昵称
    public static String MODIFYY_IMAGE = "user/modifySelfAvatar";//修改头像
    public static String RESET_PHONE_URL = "user/modifyPhone";//修改手机号
    //public static String MODIFY_GENDER = HOST + "user/modifyGender";//修改性别
    public static String MODIFY_WEIXIN = "user/modifyWeichatNo";//修改微信
    public static String MODIFY_MAIL = "user/modifyMail";//修改邮箱
    public static String MODIFY_HEIGHT = "user/modifyHeight";//修改身高
    public static String MODIFY_WEIGHT = "user/modifyWeight";//修改身高
    public static String MODIFY_IDENTIFY = "user/modifyIdentityCard";//修改身份证
    public static String MODIFY_HOBBY = "user/modifyHobby";//修改喜好
    public static String MODIFY_SIGN = "user/modifySign";//修改个性签名
    public static String BRANCH = "branch";//获取门店列表
    public static String JOINBRANCH = "branch/join";//加入或者取消门店
    //public static String GETMENUS = HOST + "appRoleFunction/getMenus";//获取菜单权限
    public static String GETYUANGONGMENUS = "appMenuFunction/queryByRoleIdEmpFuncV2";//获取员工菜单权限
    public static String GETHUIYUANMENUS = "appMenuFunction/queryMemFunctions";//获取会员菜单权限
    public static String RELOAD_LOGININFO = "/person/reloadLoginInfo";//重新获取用户信息
    public static String SAVE_REPORT = "/appReport/saveReport";//举报动态
    public static String ISJOINBRANCH = "/branch/iSJoinBranch";//判断是否加入该门店
    public static String PAY_WAYS_URL = "/payConf/findById";//支持哪种支付方式


    public static String FINDMyLOCKERS = "appLocker/findMyLockers";//我的租柜

    //报表相关
    public static String BUSSTATISTICSREPORT = "statistics/busStatisticsReport";//业务报表
    public static String SCORESTATISTICSREPORT = "statistics/scoreStatisticsReport";//业绩报表
    public static String SAVEREPORT = "/dailySummary/save";//新增报表
    public static String FINDREPORT = "/dailySummary/findByEmployeeId";//查询报表
    public static String QUERYVIDEOTYPE = "/api/video/queryVideoType";//视频列表
    public static String QUERYVIDEO = "/api/video/queryVideo";//视频列表


    //体测上传图片
    public static String BCAUPLOAD = "bcaAnalysis/uploadFile";//体测上传图片
    public static String FIND_ADD_USER_USRL = "/appDynMsg/queryByPhone_V1_2";//搜索好友
    public static String FIND_JOIN_SHOP_URL = "/myJoinBranch/findJoinBranchs";//查找已经加入门店
    public static String FIND_CONSULTANT_URL = "/employ/queryAdmin_v2";//查找会籍顾问
    public static String FIND_JIAOLIAN_URL = "/employ/queryTeach_v2";//查找教练
    public static String FIND_SERVICE_URL = "/employ/queryService";//查找服务

    public static String FIND_CLUBDYNAMIC_URL = "/clubDynamic/queryPage";//会所动态

    public static String CHANGE_CURRENT_SHOP_URL = "branch/changeCurrentBranch";//更换默认店
    public static String ADD_GUANZHU = "appUserFollow/saveUserFollower";//
    public static String ADD_ZAN_URL = "appPraise/giveThumbs";//点赞
    public static String ADD_BLACKLIST_URL = "/blackList/save";//添加黑名单
    public static String FIND_BLACKLIST_URL = "/blackList/queryList";//查询黑名单
    public static String DEL_BLACKLIST_URL = "/blackList/delete";//移除黑名单

    public static String FIND_SELF_DT_MSG = "/appDynMsg/findSelfDynMsg";//个人动态

    public static String FIND_COMMENT_LIST = "/reply/queryReplyByMsgId";//动态评论列表
    public static String FIND_ONE_DYN = "/appDynMsg/findOneDynMsg";   //查一条动态

    public static String SAVE_DYN_MSG = "appDynMsg/saveDynMsg";//发布动态
    public static String DEL_DYN_MSG = "appDynMsg/deleteOneDynMsg/";//删除一条动态
    public static String SET_MIPUSH_ID = "user/modifyRegId";//设置mipushid
    public static String UPDATE_MIPUSH_CONFIG = "update_mipush_config";//更新mipush


    public static String FIND_JINGXUAN_DT_MSG = "/appDynMsg/findChoiceness";//精选动态
    public static String FIND_GUANZHU_DT_MSG = "appDynMsg/findFollowerUserDyn";//关注的动态
    public static String FIND_GUANZHU_USER_LIST_MSG = "/appUserFollow/queryByFollowPage";//查看关注的人
    public static String FIND_FANS_USER_LIST_MSG = "/appUserFollow/queryByUserIdPage";//查看粉丝
    public static String FIND_ZAN_USER_LIST_MSG_5 = "/appPraise/findByMsgIdOrderByIdDesc";//查看点赞（前5个）
    public static String FIND_ZAN_USER_LIST_MSG = "/appPraise/findByMsgIdOrderByIdDesc";//查看点赞（全部）
    public static String FIND_PUSH_MSG = "/appDynMsg/findAllFlowerUserById";//推荐用户
    public static String UPLOADFILE_URL = "user/uploadFile";//上传文件
    public static String UPLOADVEDIO = "appDynMsg/uploadVedio";//上传视频

    public static String UPLOADTH = "appDynMsg/uploadThumbnail";//上传缩略图
    public static String UPLOAD_FILES_URL = "appDynMsg/uploadFiles";//上传多文件

    //public static String FIND_NEWS_URL =  "appHome/showNews/";//查找新闻列表
    public static String FIND_NEWS_URL_NEW = "/appHome/showHomeNews";//查找新闻列表 new
    public static String FIND_IMAGE_NEWS_URL = "appHome/showCarousel";//查找新闻轮播图片
    //public static String FIND_BRANCHRANKING =  "/cardenter/branchRanking";//我的门店的排名
    public static String FIND_MYRANKING_URL = "/cardenter/myRanking";//我的排名


    public static String SEND_COMMENT_REPLY = "reply/saveReply";//发布评论
    public static String DEL_COMMENT_REPLY = "/reply/delete";//删除发布评论

    public static String FINDALLCARDS = "card/labels";//查找会员卡
    public static String FIND_CARDS_BY_CARDTYPE = "card/types?labelId=";//查找会员卡
    public static String FINDMEMBER = "appBca/searchMember_V1_2";//

    //订单相关

    public static String COMMIT_DEPOSIT = "appOrder/saveOrder";//提交定金订单
    public static String FIND_ALL_DEPOSIT = "appDeposit/findAllDeposit";//查询订单
    public static String COMMIT_CARD_ORDER = "appOrder/saveOrder";//提交卡订单
    public static String FIND_ALL_ORDER = "appOrder/findAllPage";//查询订单
    public static String FIND_ALL_MY_CARD_LIST = "card/list";//查询我的会员卡
    public static String FIND_ALL_OTHER_CARD_LIST = "/card/othersList";//查询我送出的会员卡

    public static String CANSEL_ORDER = "/appOrder/cancelOrder";//取消订单

    public static String FIND_BC_DATA = "appBca/findMemberBcaData";//查找最近一次体测记录
    public static String BANNER = "branch/banner";//门店轮播图
    public static String BRANCH_PICTURE_URL = "/branch/queryBranchPicture";//门店介绍图片列表

    public static String MYCONSUME = "/appOrder/queryMyOrder";//我的消费


    //参数相关

    public static String FIND_BY_TYPE_CODE = "/appDict/queryList";//查询潜客资料相关参数
    public static String FIND_BY_TYPE_CODE_VISIT = "appDict/queryVisitList";//查询潜客资料相关参数
    //public static String FIND_BY_TYPE_CODE_GET =  "/appDict/findByTypeCode/";//查询潜客资料相关参数

    public static String FIND_PING_FEN = "evaluate/findEmployeeAverage";//查找教练

    public static String COMMIT_ALIPAY = "/appOrder/alipayNotify";//支付宝回调
    public static String COMMIT_WECHAT_PAY = "/appOrder/weichatNotify";//微信回调
    public static String COMMIT_CHUZHIKA = "appOrder/storeConsume";//储值卡消费回调
    public static String GET_GROUP_MEMBERS = "/group/getGroupMembers";//获取IM群组成员资料
    public static String GET_CONTACTS = "/group/getEmployes";//获取IM群组成员资料

    //潜客相关
    public static String ADD_POTENTIAL = "/appPotential/savePotential";//添加潜客
    public static String ADD_POTENTIAL_FINDBYPHONE = "/appPotential/findByPhone";//验证手机号
    public static String FIND_POTENTIAL_LIST = "/appPotential/recentMaintain";//查询潜客列表
    public static String ADD_VISIT_RECOR = "/appPotential/saveVisitRecord";//添加回访记录
    public static String FIND_BY_ID_POTENTIAL = "/appPotential/findByIdPotential/";//查询潜客详情
    public static String FIND_VISIT_RECORD = "/appPotential/findVisitRecord";//查询回访记录
    public static String ADD_UPCOMING_MATTER = "/api/work/saveWork";//添加待办
    public static String FIND_UPCOMING_MATTER_PARAM = "/api/work/queryWorkType";//查询待办参数
    public static String FIND_UPCOMING_MATTER = "/api/work/queryWorkPage";//查询待办列表
    public static String UPDATE_MATTER_STATUS = "/api/work/updateWorkStatus";//处理待办
    public static String FIND_NOT_UPCOMINGMATTER = "/api/work/queryWorkCount";//查询未处理待办
    public static String UPDATE_POTENTIAL = "/appPotential/updatePotential_V1_7";//编辑潜客资料

    public static String INTRODUCE_SAVE = "/introduce/save";//推荐好友
    public static String INTRODUCE_QUERYPAGEBYEMPLOYEE = "/introduce/queryPageByEmployee";//会员推荐
    public static String INTRODUCE_CONFIRM = "/introduce/confirm";//提交推荐
    public static String INTRODUCE_QUERYLIST = "/introduce/queryList";//查询推荐和被推荐

    //public static String SELL_CARD_PRARMS = "http://192.168.1.93/test.txt";//买卡参数

    public static String ISLOGINED = "islogined";//是否登录
    public static String MY_USERID = "my_userid";//我的ID

    public static String MY_MEMBER_ID = "my_member_id";//我的member_ID

    public static String MY_DYN = "my_nyn";//我的动态数
    public static String MY_FANS = "my_fans";//我的粉丝数
    public static String MY_FOLLOWS = "my_follows";//我的评论数

    //public static String MY_MEMBERNO = "memberNo";//我的会员号
    public static String MY_PSWD = "mypswd";//我的密码
    public static String MY_TOKEN = "my_token";//我的token
    //public static String MY_LOCATION = "my_location";//我的地区
    public static String MY_INFO = "my_info";//我的资料
    public static String MY_MIPUSH_ID = "my_mipush_id";//小米推送id
    public static String MY_WEAR_FIT_SETTING = "my_wear_fit_setting";//手环设置
    public static String MY_APP_MESSAGE_SUM = "my_app_message_sum";//应用消息总数 用于桌面icon显示

    //public static String BRANCH_DEPOSIT_DAYS = "deposit_days";//定金有效期
    //public static String BRANCH_OPEN_DAYS = "open_days";//开卡有效期
    public static String FIND_PARAM_KEY = "appParam/findParamKey";//查询参数
    public static String REAYTEST = "appBca/bcaTestUpdateMemberAndPerson";//提交用户信息
    public static String GETEQUIPMENT = "appBca/findPageBranchBca";//获取门店体测仪列表
    public static String CONNECTEQU = "appBca/connectTester";//连接设备
    public static String ISFINISHED = "appBca/isFinishedBca";//体测是否完成
    public static String FITNESS_HITORY = "appBca/findHistoryRecord";//体测历史记录
    public static String FINDONEHISTORY = "appBca/findOneHistoryRecord";//查看某条历史记录
    public static String FINDBODYAGE = "appBca/findBodyAge";//查询身体年龄
    public static String CANCELTEST = "appBca/cancelTester";//取消体测

    //私教相关
    public static String TIMETABLES = "/appGroupCourse/queryGroupCourseTimetables";//课程表
    public static String COURSETYPELIST = "appCourse/findPageCourseType";//购买私教课程列表
    public static String FINDCourseTypeEmployee = "appCourse/findCourseTypeEmployee";//查私教教练
    //public static String FINDGROUPCLASS =  "appGroupClass/queryPage";//小团课列表
    public static String FINDMEMBERCOURSE = "appCourse/findMemberCourse";//我的私教
    public static String FINDEMPCOURSE = "appCourse/findEmployeeCourse";//教练的私教
    public static String FINDAVAI = "/appCourse/queryTimelineList";//我的私教预约记录
    public static String MEMBER_TIME_LINE = "timeline/memberTimeline";//会员约教练时间轴
    public static String TEACH_TIME_LINE = "timeline/teachTimeline";//教练约会员时间轴

    public static String COURSEAPPOIN = "appCourse/courseAppoint";//预约私教
    public static String QUERYKECHENGBIAO = "/appGroupCourse/queryGroupCourseByDate";//小团课课程表
    public static String GROUPAPPOINT = "appGroupCourse/groupAppoint";//预约小团课
    public static String FINDGROUP = "appGroupCourse/findById";//单节小团课详情
    public static String FREEGROUPCOURSE = "appFreeGroupCourse/findById";//免费团课详情
    public static String FINDGROUPCOURSEAPPOINTLIST = "appGroupCourse/findGroupCourseAppointList";//小团课预约记录
    public static String FreeCourse = "appFreeGroupCourse/queryFreeGroupCourseByDate";//查询某会员某天的团课课程表
    public static String FreeCourseApply = "appFreeGroupCourse/freeGroupApply";//报名小团课
    public static String APPOINTLIST = "appCourse/findCourseAppointList";//获取会员的预约记录
    public static String FIND_TEACH_COURSELIST = "appCourse/findTeachCourseList";//获取教练预约记录
    public static String APPOINTCANCEL = "appCourse/courseAppointCancel";//取消私教
    public static String FREECOURSELIST = "appFreeGroupCourse/findFreeGroupCourseApplyList";//免费团课记录
    public static String FREECANCELGROUP = "appFreeGroupCourse/freeGroupCancelApply";//取消免费团课报名
    public static String GROUPCOURSEAPPOINTCANCEL = "/appGroupCourse/groupCourseAppointCancel";//取消小团课报名
    public static String PINGJIA = "evaluate/save";//评价接口
    public static String FINDPINGJIA = "evaluate/findById/";//查询一条评价
    public static String ENTERCOURSE = "appCourse/courseAppointConfirm ";//确认私教
    //public static String SCAN_QRCODE =  "qrcode";//扫码入场
    public static String SCAN_QRCODE_ENTER_V2 = "/qrcode/enter_v2";//扫码处理工作 扫码入场升级版
    public static String QUERYGROUPCOURSE = "appGroupCourse/queryBuyCoursePersonInfo";//查询购买此小团课的人
    public static String FINDGROUPCOURSEAPPOINTPERSON = "appGroupCourse/findGroupCourseAppointPerson";//查看某节小团课参与成员
    public static String QUERYBUYCOURSEPERSONINFO = "appCourse/queryBuyCoursePersonInfo";//查询购买此一对一课种的人
    public static String FINDFREEGROUPCOURSEAPPLYPERSON = "appFreeGroupCourse/findFreeGroupCourseApplyPerson";//免费团课
    public static String FOROTHERSIJIAOLIST = "appCourse/queryForOthersList";//为他人购买的私教列表
    public static String QUERYAVERAGE = "/evaluate/queryAverage";//课程详情里边的三个评分
    public static String QUERY_TEACH_CALENDAR = "appCourse/queryTeachCalendar";//返回教练私教界面查询日历红点数据
    public static String QUERY_MEMBER_CALENDAR = "appCourse/queryMemberCalendar";//返回会员私教界面查询日历红点数据
    public static String GROUP_QUERY_MEMBER_CALENDAR = "appFreeGroupCourse/queryMemberCalendar";//返回会员私教界面查询日历红点数据


    public static String YEWU_URL = "/api/memberRelated/queryPage";//查询业务
    //手环相关
    public static String QUERY_WEAR_FIT_HEART_RATE_LIST = "/personHeartRate/queryList";//按条件查询不分页[人员心率数据]列表
    public static String QUERY_WEAR_FIT_HEART_RATE_FANDLAST = "/personHeartRate/findLast";//根据条件查询最后一条心率数据
    public static String QUERY_WEAR_FIT_HEART_RATE_SAVE = "/personHeartRate/save";//新增[人员心率数据]
    public static String QUERY_WEAR_FIT_HEART_RATE_FANDAVG = "/personHeartRate/findAvg";//根据条件查询心率每日平均数
    public static String QUERY_WEAR_FIT_HEART_RATE_FANDRATE = "/personHeartRate/findRate";//根据条件查询心率合格率
    public static String QUERY_WEAR_FIT_SLEEP_FANDLAST = "/personSleep/findLast";//根据条件查询最后一条睡眠数据
    public static String QUERY_WEAR_FIT_SLEEP_LIST = "/personSleep/queryList";//按条件查询不分页[人员睡眠数据]列表
    public static String QUERY_WEAR_FIT_SLEEP_SAVE = "/personSleep/save";//新增[人员睡眠数据]
    public static String QUERY_WEAR_FIT_SLEEP_FINDSUM = "/personSleep/findSum";//查询指定时间段内，每天睡眠时间的总和
    public static String QUERY_WEAR_FIT_STEP_SAVE = "/personStep/save";//新增[人员计步数据]
    public static String QUERY_WEAR_FIT_STEP_FANDLAST = "/personStep/findLast";//根据条件查询最后一条计步数据
    public static String QUERY_WEAR_FIT_STEP_LIST = "/personStep/queryList";//按条件查询不分页[人员计步数据]列表
    public static String QUERY_WEAR_FIT_STEP_FINDMAX = "/personStep/findMax";//按条件查询每天最后一条数据
    public static String QUERY_WEAR_FIT_STEP_FINDFATIGUE_AVG = "/personStep/findFatigueAvg";//根据条件查询疲劳每日平均数

    //咨询 推荐
    public static String QUERY_MY_CONSULT = "/consultation/queryMyConsultations";//获取我的咨询
    public static String QUERY_SAVE_CONSULT = "/consultation/saveConsultation";//新增[咨询记录表]
    public static String QUERY_MY_RECOMMEND = "/consultation/queryMyReferrals";//获取我的推荐
    public static String QUERY_SAVE_RECOMMEND = "/consultation/saveReferral";//新增我的推荐记录

    //我的通知-通知
    public static String QUERY_QUERY_PAGE = "/pushRecord/queryPage";// 按条件查询分页[消息推送记录]列表

    //体测分析-体测须知
    public static String QUERY_BCAQUESTION_LIST = "/bcaQuestion/queryList";//按条件查询不分页[问题题干]列表

    //体测分析-结果汇总
    public static String QUERY_BCAQUESTION_FIND_BYID = "/bcaAnalysis/findById";//按主键查单条[体测分析]记录

    //扫码入场-手牌列表
    public static String QUERY_HAND_CARD = "/handCard/queryHandCard";//查询当前可用的手牌列表

    //扫码入场-手牌列表
    public static String SEND_CARD_ENTER = "/cardenter/cardEnter";//确认入场或确认离场接口

    //体测分析-历史记录
    public static String QUERY_FITNESS_LIST = "/bcaAnalysis/queryPage";//按条件查询不分页[体测分析]列表

    //第三方登录-微信
    public static String SEND_LOGIN_WEIXIN = "/auth/wxLogin";//第三方登录 微信

    //第三方登录-QQ
    public static String SEND_LOGIN_QQ = "/auth/qqLogin";//第三方登录 QQ

    //第三方登录-微信登录绑定手机号
    public static String BIND_ACCOUNT_WEIXIN = "/auth/wxBindAccount";//微信登录绑定手机号

    //第三方登录-QQ登录绑定手机号
    public static String BIND_ACCOUNT_QQ = "/auth/bindQQUser";//QQ登录绑定手机号

    public static String SHARE_RECORD_SAVE = "/shareRecord/save";//新增分享记录

    public static String SHARERECORD_CREATESHARE = "/shareRecord/createShare";//获取分享信息

    //我的通知-接收移动端传过来的角标数
    public static String PUSH_RECORD_SET_BADGE = "/pushRecord/setbadge";//接收移动端传过来的角标数

    //获取推送角标数
    public static String PUSH_RECORD_QUERY_BADGE = "/pushRecord/querybadge";//获取推送角标数

    //新增[收藏表
    public static String PUSH_COLLECT_SAVE = "/collect/save";//新增[收藏表

    //扫码训练
    public static String PUSH_SCANER_TRAIN = "/SHDevice/openSHDevice";//扫码训练

    //扫码训练 查询是否占用健身设备
    public static String QUERY_SCANER_TRAIN_STATUS = "/SHDevice/queryStatus";//扫码训练 查询是否占用健身设备

    //分页查询当前登录的人收藏的新闻列表
    public static String PUSH_COLLECT_QUERY = "/collect/queryCollect";//分页查询当前登录的人收藏的新闻列表

    //更新新闻资讯的阅读数
    public static String PUSH_READ_NUMBER = "/appHome/updateReadNumber";//更新新闻资讯的阅读数

    //修改个人资料(v 2.0增加接口)
    public static String PUSH_MODIFY_PERSON_DATA = "/person/modifyPersonData_v2";//修改个人资料(v 2.0增加接口)

    //更新会送动态的阅读数
    public static String PUSH_CLUB_READ_NUMBER = "/clubDynamic/updateReadNumber";//更新会送动态的阅读数

    //新增问题题干
    //public static String PUSH_BAC_QUESTION_SAVE =  "bcaQuestion/save";//新增问题题干

    //验证手机号与密码是否一致
    public static String PUSH_VERIFY_PHONE_PASS = "/person/verifyPhonePass";//验证手机号与密码是否一致

    //获取推送设置列表
    public static String PUSH_RECEIVE_LIST = "/pushReceive/list";//获取推送设置列表

    //我的-我的通知
    public static String PUSH_RECEIVE_SAVE = "/pushReceive/save";//设置某个类型推送是否接收

    //我的-我的通知
    public static String QUERY_COUNT = "/pushRecord/queryCount";// 查询当前登录的人未读的通知数量

    public static String QUERY_HUIJI = "/panel/queryHuiJi";// 查询会籍业绩
    public static String QUERY_JIAOLIANYEJI = "/panel/queryJiaoLianYeJi";// 查询教练业绩
    public static String QUERY_HUIJIYEWU = "/panel/queryHuiJiYeWu";// 查询会籍业务
    public static String QUERY_JIAOLIANYEWU = "/panel/queryJiaoLianYeWu";// 查询教练业务
    public static String QUERY_HUIJIYEJIMINGXI = "/panel/queryHuiJiYeJiMingXi";// 查询会籍业务明细
    public static String QUERY_JIAOLIANYEJIMINGXI = "/panel/queryJiaoLianYeJiMingXi";// 查询教练业务明细
    public static String QUERY_TICEMINGXI = "/panel/queryTiCeMingXi";// 查询体测明细
    public static String QUERY_TICEFENXIMINGXI = "/panel/queryTiCeFenXiMingXi";// 查询体测分析明细
    public static String EMP_MEM_PUSHRECEIVE = "/empMemPushReceive/save";//设置推送


    public static String QUERY_XINQIANKE = "/panel/queryXinQianKe";// 查询会籍新增潜客


    //我的-运动数据
    public static String QUERY_SH_AEROBIC = "/shData/queryAerobicTraining";// 分页查询有氧运动数据列表

    //我的-运动数据
    public static String QUERY_SH_WEIGHT = "/shData/queryWeightTraining";// 分页查询无氧运动数据列表

    //我的-运动数据
    public static String QUERY_SH_TOTAL = "/shData/queryTotalData";// 查询数据总览数据列表

    //门店-新增[文字推送]
    public static String TEXT_PUSH_SAVE = "/textPush/save";// 新增[文字推送]

    //门店-新增[文字推送]-按条件查询分页[文字推送]列表
    public static String QUERY_TEXT_PUSH_LIST = "/textPush/queryPage";// 按条件查询分页[文字推送]列表

    //环信相关
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String CHAT_ROOM = "item_chatroom";
    public static final String ACCOUNT_REMOVED = "account_removed";
    public static final String ACCOUNT_CONFLICT = "conflict";
    public static final String ACCOUNT_FORBIDDEN = "user_forbidden";
    public static final String ACCOUNT_KICKED_BY_CHANGE_PASSWORD = "kicked_by_change_password";
    public static final String ACCOUNT_KICKED_BY_OTHER_DEVICE = "kicked_by_another_device";
    public static final String CHAT_ROBOT = "item_robots";
    public static final String MESSAGE_ATTR_ROBOT_MSGTYPE = "msgtype";
    public static final String ACTION_GROUP_CHANAGED = "action_group_changed";
    public static final String ACTION_CONTACT_CHANAGED = "action_contact_changed";

    public static final String EXTRA_CONFERENCE_ID = "confId";
    public static final String EXTRA_CONFERENCE_PASS = "password";
    public static final String EXTRA_CONFERENCE_IS_CREATOR = "is_creator";


    public static final int QR_MAPPING_CARD_ENTER = 13; //刷卡入场
    public static final int QR_MAPPING_COURSE_ENTER = 11; //私教入场
    public static final int QR_MAPPING_GROUP_COURSE_ENTER = 12;//小团课入场


    public static final int ROLE_ID_HUIJIGUWEN = 1;//会籍顾问
    public static final int ROLE_ID_JIAOLIAN = 2;//教练
    public static final int ROLE_ID_QIANTAI = 3;//前台
    public static final int ROLE_ID_DIANZHANG = 4;//店长
    public static final int ROLE_ID_HUIJIZHUGUANG = 5;//会籍主管
    public static final int ROLE_ID_JIAOLIANZHUGUAN = 6;//教练主管
    public static final int ROLE_ID_QIANTAIZHUGUAN = 7;//前台主管
    public static final int ROLE_ID_CHUNA = 9;//出纳
    public static final int ROLE_ID_SHOUYIN = 10;//收银
    public static final int ROLE_ID_JIANZHIJIAOLIAN = 11;//兼职教练
    public static final int ROLE_ID_QIANKE = 666;//潜客
    public static final int ROLE_ID_HUIYUAN = 888;//会员


    public final static String ROLE_ID = "ROLE_ID";

    public final static String EXTRA_SEND_DATA_TO_BLE = "EXTRA_SEND_DATA_TO_BLE";
    public final static String ADDRESS = "address";//手环
    public final static String NAME = "name";
    public final static String MY_TXIM_ADMIN = "my_txim_admin";
    public final static String SCANER_CODE_TRAIN_ISLOOK = "scaner_code_train_islook";//扫码训练  true最后一条没看

    public static String TRAINTYPE_QUERYPAGE = "/trainType/queryPage";
    public static String TRAIN_QUERYPAGE = "/train/queryPage";//
    public static String CARDENTER_BRANCH_RANKING = "/cardenter/branchRanking";
    public static String CARDENTER_MY_RANKING = "/cardenter/myRanking";
    public static String CARDENTER_LASTMONTH_BRANCH_RANKING = "/cardenter/lastMonthBranchRanking";
    public static String CARDENTER_MY_LASTMONTH_RANKING = "/cardenter/myLastMonthRanking";
    public static String CARDENTER_MY_MONTH_RANKING = "/cardenter/myMonthRanking";
    public static String CARDENTER_MONTH_BRANCH_RANKING = "/cardenter/monthBranchRanking";
}
