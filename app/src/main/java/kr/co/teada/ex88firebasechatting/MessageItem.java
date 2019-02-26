package kr.co.teada.ex88firebasechatting;

public class MessageItem {
    //채팅창에서 공통으로 들어가는(반복되는) 요소들

    String name;
    String msg;
    String time;
    String profileUrl;

    //생성자 Alt+insert
    public MessageItem(String name, String msg, String time, String profileUrl) {
        this.name = name;
        this.msg = msg;
        this.time = time;
        this.profileUrl = profileUrl;
    }

    //Firebase DB 에 저장하려면 빈 생성자가 있어야 해
    public MessageItem() {
    }

    //getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
