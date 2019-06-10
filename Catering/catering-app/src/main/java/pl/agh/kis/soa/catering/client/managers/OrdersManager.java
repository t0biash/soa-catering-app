package pl.agh.kis.soa.catering.client.managers;

import pl.agh.kis.soa.catering.client.services.ClientService;
import pl.agh.kis.soa.catering.client.services.MenuItemService;
import pl.agh.kis.soa.catering.client.services.OrderService;
import pl.agh.kis.soa.catering.client.services.SubscriptionService;
import pl.agh.kis.soa.catering.server.model.Client;
import pl.agh.kis.soa.catering.server.model.MenuItem;
import pl.agh.kis.soa.catering.server.model.Order;
import pl.agh.kis.soa.catering.server.model.Subscription;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.util.*;

@ManagedBean(name = "OrdersManager")
@SessionScoped
public class OrdersManager {
    private Map<Long, Boolean> orderCheckboxes = new HashMap<Long, Boolean>();
    private List<MenuItem> orderedMenuItems;
    private String[] subscriptionDaysOfTheWeek;
    private Date fromDate;
    private Date toDate;

    @ManagedProperty(value="#{menuItemService}")
    private MenuItemService menuItemService;
    @ManagedProperty(value="#{orderService}")
    private OrderService orderService;
    @ManagedProperty(value="#{subscriptionService}")
    private SubscriptionService subscriptionService;
    @ManagedProperty(value="#{clientService}")
    private ClientService clientService;

    public Map<Long, Boolean> getOrderCheckboxes() {
        return orderCheckboxes;
    }

    public List<MenuItem> getOrderedMenuItems() {
        return orderedMenuItems;
    }

    public String[] getSubscriptionDaysOfTheWeek() { return subscriptionDaysOfTheWeek; }

    public Date getFromDate() { return fromDate; }

    public Date getToDate() { return toDate; }

    public BigDecimal getOrderPrice() {
        BigDecimal orderPrice = new BigDecimal(0);

        for(MenuItem menuItem : orderedMenuItems)
            orderPrice = orderPrice.add(menuItem.getPrice());

        return orderPrice;
    }

    public BigDecimal getTotalPriceOfAllClientOrders() {
        Client client = clientService.getClientByUsername(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        BigDecimal totalPrice = new BigDecimal(0);
        List<Order> allClientOrders = orderService.getAllClientOrders(client.getId());

        for(Order order : allClientOrders)
            for(MenuItem menuItem : order.getMenuItems())
                totalPrice = totalPrice.add(menuItem.getPrice());

        return totalPrice;
    }

    public List<Order> getAllClientOrders() {
        Client client = clientService.getClientByUsername(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

        return orderService.getAllClientOrders(client.getId());
    }

    public List<Order> getAllUnconfirmedOrders() {
        return orderService.getAllUnconfirmedOrders();
    }

    public List<Order> getAllConfirmedAndNotShipPendingOrders() {
        return orderService.getAllConfirmedAndNotShipPendingOrders();
    }

    public List<Order> getClientOrdersBetweenDates() {
        Client client = clientService.getClientByUsername(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

        if(fromDate != null && toDate != null)
            return orderService.getClientOrdersBetweenDates(client.getId(), fromDate, toDate);
        return new ArrayList<>();
    }

    public int getCurrentMonthNumberOfMeals() {
        Client client = clientService.getClientByUsername(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        Date now = new Date();
        Date fromDate = new Date(now.getYear(), now.getMonth(), 1);
        Date toDate = new Date(now.getYear(), now.getMonth() , Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

        return orderService.getClientOrdersBetweenDates(client.getId(), fromDate, toDate).size();
    }

    public BigDecimal getCurrentMonthClientOrdersTotalPrice() {
        Client client = clientService.getClientByUsername(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        Date now = new Date();
        Date fromDate = new Date(now.getYear(), now.getMonth(), 1);
        Date toDate = new Date(now.getYear(), now.getMonth() , Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));

        List<Order> clientOrders = orderService.getClientOrdersBetweenDates(client.getId(), fromDate, toDate);
        BigDecimal totalPrice = new BigDecimal(0);

        for(Order order :  clientOrders)
            totalPrice = totalPrice.add(order.getPrice());

        return totalPrice;
    }

    public List<Subscription> getAllClientSubscriptions() {
        Client client = clientService.getClientByUsername(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

        return subscriptionService.getAllClientSubscriptions(client.getId());
    }

    public String getSubscriptionDaysOfTheWeekExplication(String subscribedDaysOfTheWeek) {
        String daysOfTheWeek = subscribedDaysOfTheWeek;

        daysOfTheWeek = daysOfTheWeek
                .replace("MON", "Monday")
                .replace("TUE", "Tuesday")
                .replace("WED", "Wednesday")
                .replace("THU", "Thursday")
                .replace("FRI", "Friday")
                .replace("SAT", "Saturday")
                .replace("SUN", "Sunday")
                .replace("#", ", ");

        return daysOfTheWeek;
    }

    public void setMenuItemService(MenuItemService menuItemService) { this.menuItemService = menuItemService; }

    public void setOrderService(OrderService orderService) { this.orderService = orderService; }

    public void setSubscriptionService(SubscriptionService subscriptionService) { this.subscriptionService = subscriptionService; }

    public void setClientService(ClientService clientService) { this.clientService = clientService; }

    public void setSubscriptionDaysOfTheWeek(String[] subscriptionDaysOfTheWeek) { this.subscriptionDaysOfTheWeek = subscriptionDaysOfTheWeek; }

    public void setFromDate(Date fromDate) { this.fromDate = fromDate; }

    public void setToDate(Date toDate) { this.toDate = toDate; }

    public String order() {
        orderedMenuItems = new ArrayList<MenuItem>();

        for(Long id : orderCheckboxes.keySet()) {
            boolean checked = orderCheckboxes.get(id);

            if(checked)
                orderedMenuItems.add(menuItemService.getMenuItemById(id));
        }

        return "order-summary";
    }

    public String subscribe() {
        order();

        return "subscription-details";
    }

    public String confirmOrder() {
        Client client = clientService.getClientByUsername(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());

        orderService.addOrder(client.getId(), orderedMenuItems, new Date(), getOrderPrice());
        orderedMenuItems = new ArrayList<MenuItem>();

        return "client-panel";
    }

    public String cancelOrder() {
        orderedMenuItems = new ArrayList<MenuItem>();

        return "client-panel";
    }

    public String confirmSubscription() {
        Client client = clientService.getClientByUsername(FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName());
        String subscribedDaysOfTheWeek = String.join("#", subscriptionDaysOfTheWeek);

        if(!subscribedDaysOfTheWeek.equals(""))
            subscriptionService.addSubscription(orderedMenuItems, client.getId(), subscribedDaysOfTheWeek);

        return "client-panel";
    }

    public String cancelSubscription() {
        orderedMenuItems = new ArrayList<MenuItem>();

        return "client-panel";
    }

    public String deleteSubscription(Subscription subscription) {
        subscriptionService.deleteSubscription(subscription.getId());

        return "subscription-panel";
    }
}
