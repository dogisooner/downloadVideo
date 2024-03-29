package com.download.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.SessionAware;  
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.download.dao.CustomerDao;
import com.download.model.Customer;
import com.opensymphony.xwork2.ActionSupport;

@Controller @Scope("prototype")
public class CustomerAction extends ActionSupport implements SessionAware{
	
	private static final long serialVersionUID = 1L;

	@Resource CustomerDao customerDao;

	
	private Customer customer;
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public List<Customer> getCustomerList() {
		return customerList;
	}
	
	
	private Map<String,Object> session;
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	
	
	private String errMessage;
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
	
	public String reg() throws Exception{
		customerDao.addCustomer(customer);
		session.put("customer", customer);
		return "show_view";

	}
	
	
	private List<Customer> customerList;
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomerList(List<Customer> customerList) {
		this.customerList = customerList;
	}
	public String addCustomer() throws Exception{
		customerDao.addCustomer(customer);
		return "message";
	}
	public String showCustomer(){
		customerList=customerDao.QueryAllCustomer();
		return "show_view";
	}
	
	
	public String login(){
		ArrayList<Customer> listCustomers = customerDao.QueryCustomer(customer.getCustomerName());
		if(listCustomers.size()==0){
			this.errMessage="账号不存在";
			System.out.println(this.errMessage);
			return "input";
		}else{
			Customer db_customerCustomer = listCustomers.get(0);
			if(!db_customerCustomer.getPassword().equals(customer.getPassword())){
				this.errMessage="密码不正确";
				System.out.println(this.errMessage);
				return "input";
			}else{
				session.put("customer", db_customerCustomer);
				return "success";
			}
		}
	
	}
	
}