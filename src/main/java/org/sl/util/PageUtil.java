package org.sl.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/10/1.
 * 分页工具类
 */
public class PageUtil implements Serializable,Cloneable {
    private Integer pageIndex=1;//当前页码
    private Integer pageSize=2;//页面大小
    private Integer pageCount=1;//总页数
    private Integer sqlCount=0;//数据库总记录数
    private Integer num=2;//当前页码前后个多显示的按钮
    private List items = new ArrayList();

    public PageUtil() {
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public void setSqlCount(Integer sqlCount) {
        if (sqlCount<=0){
            return;
        }
        this.sqlCount = sqlCount;
        this.pageCount= this.sqlCount%pageSize==0 ?
                this.sqlCount/pageSize : this.sqlCount/pageSize+1;
    }

    public Integer getSqlCount() {
        return sqlCount;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }




    /**
     * 获取前一页
     * @return
     */
    public Integer getPrev(){
        return this.pageIndex-1;
    }

    /**
     * 获取后一页
     * @return
     */
    public Integer getNext(){
        return this.pageIndex+1;
    }

    /**
     * 判断是否有前一页
     * @return
     */
    public boolean isPrev(){
        return pageIndex > 1 ? true : false;
    }

    /**
     * 判断是否有下一页
     * @return
     */
    public boolean isNext(){
        if (pageCount==null || pageCount<1){//判断总页码是否正确
            return false;
        }
        return pageIndex<pageCount?true:false;
    }

    /**
     * 获取当前页码之前显示的页数 当前页为6 则返回345
     * @return
     */
    public List<Integer> getPrevPages(){
        List<Integer> list=new ArrayList<Integer>();
        Integer start=1;
        if (pageIndex>num)
            start=pageIndex-num;
        for (Integer i=start;i<pageIndex;i++){
            list.add(i);
        }
        return list;
    }

    /**
     * 获取当前页码之后的显示页数 如当前页码：6  返回789
     * @return
     */
    public List<Integer> getNextPages(){
        List<Integer> list=new ArrayList<Integer>();
        Integer end=num;
        if (pageCount==null || pageCount<1){
            return null;
        }
        if (num<pageCount && (num+pageIndex)<pageCount){
            end+=pageIndex;
        }else {
            end=pageCount;
        }
        for (Integer i=pageIndex+1;i<=end;i++){
            list.add(i);
        }
        return list;
    }

}
