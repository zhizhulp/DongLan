package com.cn.danceland.myapplication.bean;

import java.util.List;

/**
 * Created by shy on 2018/7/17 16:33
 * Email:644563767@qq.com
 */


public class GroupMemeberInfo {

    private String ActionStatus;
    private int ErrorCode;
    private List<MemberList> MemberList;
    private int MemberNum;

    @Override
    public String toString() {
        return "GroupMemeberInfo{" +
                "ActionStatus='" + ActionStatus + '\'' +
                ", ErrorCode=" + ErrorCode +
                ", MemberList=" + MemberList +
                ", MemberNum=" + MemberNum +
                '}';
    }

    public void setActionStatus(String ActionStatus) {
        this.ActionStatus = ActionStatus;
    }
    public String getActionStatus() {
        return ActionStatus;
    }

    public void setErrorCode(int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }
    public int getErrorCode() {
        return ErrorCode;
    }

    public void setMemberList(List<MemberList> MemberList) {
        this.MemberList = MemberList;
    }
    public List<MemberList> getMemberList() {
        return MemberList;
    }

    public void setMemberNum(int MemberNum) {
        this.MemberNum = MemberNum;
    }
    public int getMemberNum() {
        return MemberNum;
    }

    /**
     * Auto-generated: 2018-07-17 16:33:27
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class MemberList {
        @Override
        public String toString() {
            return "MemberList{" +
                    "AppMemberDefinedData=" + AppMemberDefinedData +
                    ", JoinTime=" + JoinTime +
                    ", LastSendMsgTime=" + LastSendMsgTime +
                    ", Member_Account='" + Member_Account + '\'' +
                    ", MsgFlag='" + MsgFlag + '\'' +
                    ", MsgSeq=" + MsgSeq +
                    ", NameCard='" + NameCard + '\'' +
                    ", Role='" + Role + '\'' +
                    ", ShutUpUntil=" + ShutUpUntil +
                    '}';
        }

        private List<AppMemberDefinedData> AppMemberDefinedData;
        private long JoinTime;
        private long LastSendMsgTime;
        private String Member_Account;
        private String MsgFlag;
        private int MsgSeq;
        private String NameCard;
        private String Role;
        private int ShutUpUntil;
        public void setAppMemberDefinedData(List<AppMemberDefinedData> AppMemberDefinedData) {
            this.AppMemberDefinedData = AppMemberDefinedData;
        }
        public List<AppMemberDefinedData> getAppMemberDefinedData() {
            return AppMemberDefinedData;
        }

        public void setJoinTime(long JoinTime) {
            this.JoinTime = JoinTime;
        }
        public long getJoinTime() {
            return JoinTime;
        }

        public void setLastSendMsgTime(long LastSendMsgTime) {
            this.LastSendMsgTime = LastSendMsgTime;
        }
        public long getLastSendMsgTime() {
            return LastSendMsgTime;
        }

        public void setMember_Account(String Member_Account) {
            this.Member_Account = Member_Account;
        }
        public String getMember_Account() {
            return Member_Account;
        }

        public void setMsgFlag(String MsgFlag) {
            this.MsgFlag = MsgFlag;
        }
        public String getMsgFlag() {
            return MsgFlag;
        }

        public void setMsgSeq(int MsgSeq) {
            this.MsgSeq = MsgSeq;
        }
        public int getMsgSeq() {
            return MsgSeq;
        }

        public void setNameCard(String NameCard) {
            this.NameCard = NameCard;
        }
        public String getNameCard() {
            return NameCard;
        }

        public void setRole(String Role) {
            this.Role = Role;
        }
        public String getRole() {
            return Role;
        }

        public void setShutUpUntil(int ShutUpUntil) {
            this.ShutUpUntil = ShutUpUntil;
        }
        public int getShutUpUntil() {
            return ShutUpUntil;
        }
        public class AppMemberDefinedData {
            @Override
            public String toString() {
                return "AppMemberDefinedData{" +
                        "Key='" + Key + '\'' +
                        ", Value='" + Value + '\'' +
                        '}';
            }

            private String Key;
            private String Value;
            public void setKey(String Key) {
                this.Key = Key;
            }
            public String getKey() {
                return Key;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }
            public String getValue() {
                return Value;
            }

        }
    }
}
