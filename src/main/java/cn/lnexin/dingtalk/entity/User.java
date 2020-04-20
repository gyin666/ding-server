package cn.lnexin.dingtalk.entity;


import cn.lnexin.dingtalk.utils.JsonTool;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

/**
 * 钉钉用户信息封装
 * 可以不用管,使用自己的就可以
 *
 * @author lnexin@aliyun.com
 * @since 2019-09-10
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1737657858725217691L;

    /**
     * 员工在当前开发者企业账号范围内的唯一标识，系统生成，固定值，不会改变
     */
    private String unionId;

    /**
     * 企业ID
     */
    private String corpId;
    /**
     * 员工在当前企业内的唯一标识，也称staffId
     */
    private String userId;

    /**
     * 员工名字
     */
    private String name;
    /**
     * 职位信息
     */
    private String position;
    /**
     * 头像url
     */
    private String avatar;


    public User() {
    }

    public User(String responseBody) {
        //response {
        //         "errcode": 0,
        //         "errmsg": "ok",
        //         "name": "张三",
        //         "unionid": "PiiiPyQqBNBii0HnCJ3zljcuAiEiE",
        //         "userid": "zhangsan",
        //         "isLeaderInDepts": "{1:false}",
        //         "isBoss": false,
        //         "hiredDate": 1520265600000,
        //         "isSenior": false,
        //         "department": [1,2],
        //         "orderInDepts": "{1:71738366882504}",
        //         "active": false,
        //         "avatar": "xxx",
        //         "isAdmin": false,
        //         "isHide": false,
        //         "jobnumber": "001",
        //         "position": "manager",
        //         "roles": [
        //                {
        //                  "id": 149507744,
        //                  "name": "总监",
        //                  "groupName": "职务"
        //                }
        //              ]
        //          }
        JsonNode userNode = JsonTool.getNode(responseBody);
        this.userId = userNode.get("userid").asText();
        this.unionId = userNode.get("unionid").asText();
        this.name = userNode.get("name").asText();

        this.position = userNode.get("position").textValue();
        this.avatar = userNode.get("avatar").textValue();

    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }


    @Override
    public String toString() {
        return "User{" +
                "unionId='" + unionId + '\'' +
                ", corpId='" + corpId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", position='" + position + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
