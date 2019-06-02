
package pl.agh.kis.soa.catering.client.soap.generated;

import java.math.BigDecimal;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.0
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CateringService", targetNamespace = "http://interfaces.soap.server.soa.kis.agh.pl/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CateringService {


    /**
     * 
     * @param menuItemPrice
     * @param menuItemName
     * @param menuItemCategoryId
     * @param menuItemServingSize
     */
    @WebMethod
    @RequestWrapper(localName = "addMenuItemToCategory", targetNamespace = "http://interfaces.soap.server.soa.kis.agh.pl/", className = "pl.agh.kis.soa.catering.client.soap.generated.AddMenuItemToCategory")
    @ResponseWrapper(localName = "addMenuItemToCategoryResponse", targetNamespace = "http://interfaces.soap.server.soa.kis.agh.pl/", className = "pl.agh.kis.soa.catering.client.soap.generated.AddMenuItemToCategoryResponse")
    public void addMenuItemToCategory(
        @WebParam(name = "menuItemName", targetNamespace = "")
        String menuItemName,
        @WebParam(name = "menuItemServingSize", targetNamespace = "")
        int menuItemServingSize,
        @WebParam(name = "menuItemPrice", targetNamespace = "")
        BigDecimal menuItemPrice,
        @WebParam(name = "menuItemCategoryId", targetNamespace = "")
        Long menuItemCategoryId);

}
