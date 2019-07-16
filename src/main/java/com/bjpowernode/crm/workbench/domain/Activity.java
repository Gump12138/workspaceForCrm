package com.bjpowernode.crm.workbench.domain;

/**
 * 市场活动实体类
 * Author: 甘明波
 * 2019-07-15
 */
public class Activity {
    private String id;  //识别符号，主键...UUID
    private String owner;   //拥有者...UUID
    private String name;    //市场活动名称
    private String startDate;   //开始日期
    private String endDate;     //结束时间
    private String cost;        //成本
    private String description; //描述
    private String createTime;  //创建时间
    private String createBy;    //创建者
    private String editTime;    //上一次编辑时间
    private String editBy;      //编辑人

    public String getId() {
        return id;
    }

    public Activity setId(String id) {
        this.id = id;
        return this;
    }

    public String getOwner() {
        return owner;
    }

    public Activity setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getName() {
        return name;
    }

    public Activity setName(String name) {
        this.name = name;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public Activity setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public Activity setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getCost() {
        return cost;
    }

    public Activity setCost(String cost) {
        this.cost = cost;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Activity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public Activity setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getCreateBy() {
        return createBy;
    }

    public Activity setCreateBy(String createBy) {
        this.createBy = createBy;
        return this;
    }

    public String getEditTime() {
        return editTime;
    }

    public Activity setEditTime(String editTime) {
        this.editTime = editTime;
        return this;
    }

    public String getEditBy() {
        return editBy;
    }

    public Activity setEditBy(String editBy) {
        this.editBy = editBy;
        return this;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", cost='" + cost + '\'' +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", editTime='" + editTime + '\'' +
                ", editBy='" + editBy + '\'' +
                '}';
    }
}
