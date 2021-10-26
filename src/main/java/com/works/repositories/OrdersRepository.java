package com.works.repositories;

import com.works.entities.projection.LikeJoin;
import com.works.entities.projection.OrdersJoin;
import com.works.entities.restentity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {

    @Query(value = "SELECT * FROM `orders` WHERE customerid = :cuid AND orderstatus = false",nativeQuery = true)
    List<Orders> userActiveOrders(Integer cuid);

    @Query(value = "SELECT * FROM `orders` WHERE customerid = :cuid AND orderstatus = true ",nativeQuery = true)
    List<Orders> userPassiveOrders(Integer cuid);

    @Query(value = "SELECT * FROM `orders` AS o where o.orderstatus = false GROUP BY o.customerid; ",nativeQuery = true)
    List<Orders> orderList();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `orders`  where orderstatus = false AND customerid = :cuid",nativeQuery = true)
    Integer deleteOrder(Integer cuid);

    @Query(value = "SELECT c.cid as customerid , o.adress AS adress ,c.mobile_phone as phone,c.email as email,COUNT(productid) as quantity ,SUM(p.pprice) AS price ,p.pname as name, c.cname as cname, c.csurname as surname FROM `orders` AS o INNER JOIN customer AS c ON o.customerid = c.cid INNER JOIN product as p ON o.productid = p.pid where o.orderstatus = false AND o.customerid = :id GROUP BY productid",nativeQuery = true)
    List<OrdersJoin> orderDetail(Integer id);


    @Modifying
    @Transactional
    @Query(value = "UPDATE `orders` SET orderstatus = true WHERE customerid = :id",nativeQuery = true)
    void orderComplete(Integer id);
}
