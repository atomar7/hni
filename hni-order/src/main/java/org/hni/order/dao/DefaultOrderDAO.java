package org.hni.order.dao;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hni.common.DateUtils;
import org.hni.common.dao.AbstractDAO;
import org.hni.order.om.Order;
import org.hni.user.om.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultOrderDAO extends AbstractDAO<Order> implements OrderDAO {
	private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);
	
	public DefaultOrderDAO() {
		super(Order.class);
	}

	@Override
	public Collection<Order> get(User user, LocalDate startDate, LocalDate endDate) {
		try {
			Query q = em.createQuery("SELECT x FROM Order x WHERE x.clientId = :userId AND x.orderDate BETWEEN :startDate AND :endDate")
				.setParameter("userId", user.getId())
				.setParameter("startDate", DateUtils.asDate(startDate))
				.setParameter("endDate", DateUtils.asDate(endDate));
			return q.getResultList();
		} catch(NoResultException e) {
			return Collections.emptyList();
		}
	}


}
