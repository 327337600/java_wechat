package org.yangjie.dao;

import org.springframework.stereotype.Repository;
import org.yangjie.entity.TicketBean;


@Repository
public class JsApiDao {

	/** 缓存ticket信息 */
	private TicketBean ticketBean;
	

	/**
	 * 获取Ticket信息
	 * @author YangJie [2016年5月4日 下午2:43:53]
	 * @param appid
	 * @return
	 */
	public TicketBean getTicket(){
		return ticketBean;
	}
	
	/**
	 * 缓存新获得的Ticket
	 * @author YangJie [2016年5月4日 下午2:45:34]
	 * @param tokenBean
	 * @return
	 */
	public void setTicket(TicketBean ticketBean){
		this.ticketBean = ticketBean;
	}
	
}
