package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        orderMap.put(order.getId(),order);
//        for(var e : orderMap.entrySet()){
//            System.out.println(e);
//        }
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner DP = new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,DP);
//        for(var e : partnerMap.entrySet()){
//            System.out.println(e);
//        }
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        System.out.println("The OrderId is :"+orderId);
        System.out.println("The partnerId is :"+partnerId);
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order

//            orderToPartnerMap.put(orderId,partnerId);
//            DeliveryPartner partner = partnerMap.get(partnerId);
//            Order order = orderMap.get(orderId);
//            partner.getOrderList().add(order);
//            partner.setNumberOfOrders(partner.getNumberOfOrders()+1);
//            order.setDeliveryPartner(partner);
//            orderMap.put(orderId,order);
//            partnerMap.put(partnerId,partner);


//            System.out.println("Helloo haiiii");
//            int count=0;
            HashSet<String> currentorders = new HashSet<>();
            if(partnerToOrderMap.containsKey(partnerId))
            {
                currentorders = partnerToOrderMap.get(partnerId);
            }
//            count++;
            currentorders.add(orderId);
            partnerToOrderMap.put(partnerId, currentorders);
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(currentorders.size());
            orderToPartnerMap.put(orderId,partnerId);

//
//                System.out.println("These patner-to-order map");
//                for(var e : partnerToOrderMap.entrySet()){
//                    System.out.println(e);
//                }
//
//                System.out.println("these is order-to-patner map");
//                for(var v : orderToPartnerMap.entrySet()){
//                    System.out.println(v);
//                }

        }else{
            System.out.println("Nothing found with given IDs");
        }
    }

    public Order findOrderById(String orderId){
        // your code here
        Order order = null;
        if(orderMap.containsKey(orderId)){
            order=orderMap.get(orderId);
//            System.out.println("The order is: "+order);
            return order;
        }else{
//            System.out.println("The order in else block: "+order);
            return order;
        }
    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        DeliveryPartner deliveryPartner = null;
        if(partnerMap.containsKey(partnerId)){
            deliveryPartner=partnerMap.get(partnerId);
//            System.out.println("The partner is: "+deliveryPartner);
            return deliveryPartner;
        }else{
//            System.out.println("The partner in else is: "+deliveryPartner);
            return deliveryPartner;
        }
//        return partnerMap.getOrDefault(partnerId,null);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        if(partnerMap.containsKey(partnerId)){
            DeliveryPartner partner = partnerMap.get(partnerId);
            return partner.getNumberOfOrders();
        }else{
            return 0;
        }
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        List<String> orderList = new ArrayList<>();
        if(partnerToOrderMap.containsKey(partnerId)){
            for(String str : partnerToOrderMap.get(partnerId)){
                orderList.add(str);
            }
            return orderList;
        }
        return orderList;
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        List<String> allOrderList = new ArrayList<>();
        for(var e : orderMap.entrySet()){
            allOrderList.add(e.getKey());
        }
        return allOrderList;
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        List<String> pat_orderList = new ArrayList<>();
        if(partnerMap.containsKey(partnerId)){
            partnerMap.remove(partnerId);
        }
        if(partnerToOrderMap.containsKey(partnerId)){
            pat_orderList.addAll(partnerToOrderMap.get(partnerId));
            partnerToOrderMap.remove(partnerId);
        }

        //unassigning orders.
        for(String str : pat_orderList){
            orderToPartnerMap.remove(str);
        }
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        String partnerId = null;
        HashSet<String> orderSet = new HashSet<>();
        if(orderMap.containsKey(orderId)){
            orderMap.remove(orderId);
        }
        if(orderToPartnerMap.containsKey(orderId)){
            partnerId = orderToPartnerMap.get(orderId);
            orderToPartnerMap.remove(orderId);
        }
        if(partnerToOrderMap.containsKey(partnerId)){
            orderSet=partnerToOrderMap.get(partnerId);
            orderSet.remove(orderId);
            DeliveryPartner partner = partnerMap.get(partnerId);
            partner.setNumberOfOrders(orderSet.size());
            partnerToOrderMap.put(partnerId,orderSet);
        }
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        Integer countOfUnassignedOrders=0;
        return (orderMap.size() - orderToPartnerMap.size() );
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        Integer count=0;

        String[] split = timeString.split(":");
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        int givenTime = (hour*60)+minute;

        List<String> orderList = new ArrayList<>();
        orderList.addAll(partnerToOrderMap.get(partnerId));
        int listSize = orderList.size();
        for(String str : orderList){
            Order order = orderMap.get(str);
            int time = order.getDeliveryTime();
            if(time <= givenTime){
                count++;
            }
        }
        return listSize - count;

    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        int lastDelivery = 0;
        if(partnerToOrderMap.containsKey(partnerId)){
            for(String orderId : partnerToOrderMap.get(partnerId)){
                Order order = orderMap.get(orderId);
                if(order.getDeliveryTime()>lastDelivery){
                    lastDelivery=order.getDeliveryTime();
                }
            }
        }
        return convertMinutesToHours(lastDelivery);
    }

    public String convertMinutesToHours(int time){
        int hour = time/60;
        int minute = time%60;
        return String.format("%02d:%02d",hour,minute);
    }
}
