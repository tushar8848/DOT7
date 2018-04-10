package a.getter_setter;

/**
 * Created by TUSHAR on 10-04-18.
 */

public class RestaurantOrders {
    String RName;
    String TotalPrice;
    String OrderId;
    String OrderDate;
    public void setRName(String RName)
    {
        this.RName = RName;
    }
    public void setTotalPrice(String totalPrice)
    {
        this.TotalPrice = totalPrice;
    }
    public void setOrderId(String orderId)
    {
        this.OrderId = orderId;
    }
    public void setOrderDate(String orderDate)
    {
        this.OrderDate = orderDate;
    }
    public String getRName()
    {
        return RName;
    }
    public String getTotalPrice()
    {
        return TotalPrice;
    }
    public String getOrderId()
    {
        return OrderId;
    }
    public String getOrderDate()
    {
        return OrderDate;
    }
}
