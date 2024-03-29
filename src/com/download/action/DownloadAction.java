package com.download.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.download.dao.CustomerDao;
import com.download.dao.DownloadDao;
import com.download.dao.VideoDao;
import com.download.model.Customer;
import com.download.model.Download;
import com.download.model.Video;
import com.opensymphony.xwork2.ActionSupport;

@Controller @Scope("prototype")
public class DownloadAction extends ActionSupport{
	
	 /*业务层对象*/
    @Resource VideoDao videoDao;
    @Resource CustomerDao customerDao;
    @Resource DownloadDao downloadDao;
    
    private Download download;
    private List<Download> downloadList;
    private Customer customer;
    private Video video;
    
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	
	
	public Download getDownload() {
		return download;
	}

	public void setDownload(Download download) {
		this.download = download;
	}

	public List<Download> getDownloadList() {
		return downloadList;
	}

	public void setDownloadList(List<Download> downloadList) {
		this.downloadList = downloadList;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	/*添加Order*/
	public String addDownload() throws Exception{
		
		customer = customerDao.QueryCustomer(customer.getCustomerName()).get(0);
		Download dld =new Download();
		dld.setCustomer(customer);
		dld.setVideo(video);
		//因为是“来一份”，所以置为1
		downloadDao.addDownload(dld);
		return "download_message";
		
	}
	
	/*显示所有dld*/
    public String showDownload() {
    	
        //将系统设定为用户名不重复，因此在系统中查询到第一个该名称用户即可
    	System.out.println(customer.getCustomerName());
        Customer cus= customerDao.QueryCustomer(customer.getCustomerName()).get(0);
        //注意不需要food的查询条件时，下面语句的写法，直接将food的条件置为null
        downloadList = downloadDao.QueryDownloadInfo(cus,null);

        return "show_view";
    }

    /*显示某一Download的详细信息*/
    public String showDetail() {
    	System.out.print(download.getDownloadId());
    	download = downloadDao.GetDownloadById(download.getDownloadId());
        return "detail_view";
    }
    
    /*显示Order的修改项*/
    /*public String showEdit() throws Exception {
    	order = orderDao.GetOrderById(order.getOrderid());
        return "edit_view";
    }*/

    /*编辑Order*/
    /*public String editOrder() throws Exception {
    	orderDao.UpdateOrder(order);
        return "edit_message";
    }*/
    
    /*删除Order*/
    /*public String deleteOrder() throws Exception {
    	orderDao.DeleteOrder(food.getFoodid());
        return "delete_message";
    }*/
    
    /*查询Order*/
    public String queryDownload() throws Exception {
    	downloadList = downloadDao.QueryDownloadInfo(customer,video);
        return "show_view";
    }


}
