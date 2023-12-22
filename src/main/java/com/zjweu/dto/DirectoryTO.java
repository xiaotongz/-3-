package com.zjweu.dto;


import java.util.List;


public class DirectoryTO {
    /*
    private Integer id;   //文件或目录id
    不应该有主键，因为凡是用到query类的都不是精准查询，而是可能返回多个结果的不定条件查询或者模糊查询
    */
    private int userId;   //文件或目录的用户id
    private Integer did;  //查询文件或目录的所属目录id
    private String name;  //查询目录名
    private List<Integer> dids;  //根据多个id进行删除
    public DirectoryTO() {
        super();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getDids() {
        return dids;
    }

    public void setDids(List<Integer> dids) {
        this.dids = dids;
    }
}
