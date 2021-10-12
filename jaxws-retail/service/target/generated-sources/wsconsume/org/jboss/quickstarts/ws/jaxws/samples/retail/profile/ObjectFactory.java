
package org.jboss.quickstarts.ws.jaxws.samples.retail.profile;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.jboss.quickstarts.ws.jaxws.samples.retail.profile package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetCustomerDiscount_QNAME = new QName("http://org.jboss.ws/samples/retail/profile", "getCustomerDiscount");
    private final static QName _GetCustomerDiscountResponse_QNAME = new QName("http://org.jboss.ws/samples/retail/profile", "getCustomerDiscountResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.jboss.quickstarts.ws.jaxws.samples.retail.profile
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Customer }
     * 
     */
    public Customer createCustomer() {
        return new Customer();
    }

    /**
     * Create an instance of {@link DiscountRequest }
     * 
     */
    public DiscountRequest createDiscountRequest() {
        return new DiscountRequest();
    }

    /**
     * Create an instance of {@link DiscountResponse }
     * 
     */
    public DiscountResponse createDiscountResponse() {
        return new DiscountResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscountRequest }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DiscountRequest }{@code >}
     */
    @XmlElementDecl(namespace = "http://org.jboss.ws/samples/retail/profile", name = "getCustomerDiscount")
    public JAXBElement<DiscountRequest> createGetCustomerDiscount(DiscountRequest value) {
        return new JAXBElement<DiscountRequest>(_GetCustomerDiscount_QNAME, DiscountRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DiscountResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link DiscountResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://org.jboss.ws/samples/retail/profile", name = "getCustomerDiscountResponse")
    public JAXBElement<DiscountResponse> createGetCustomerDiscountResponse(DiscountResponse value) {
        return new JAXBElement<DiscountResponse>(_GetCustomerDiscountResponse_QNAME, DiscountResponse.class, null, value);
    }

}
