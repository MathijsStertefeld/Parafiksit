package com.marbl.parafiksitwebi.beans;

import com.marbl.client.ClientOrderRequest;
import com.marbl.messaging.JMSSettings;
import com.marbl.parafiksitwebi.messaging.ClientTest;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class OrderBean {

    private ClientOrderRequest request;
    private ClientTest client;

    @PostConstruct
    public void postConstruct() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        JMSSettings queueNames = new JMSSettings(externalContext.getRealPath("/../../src/main/resources/MESSAGING_CHANNELS.ini"));
        final String factoryName = queueNames.get(JMSSettings.CONNECTION);
        final String clientOrderRequestQueue = queueNames.get(JMSSettings.CLIENT_ORDER_REQUEST);
        final String clientOrder2ReplyQueue = queueNames.get(JMSSettings.CLIENT_ORDER_REPLY_2);

        client = new ClientTest("FontysApp", factoryName, clientOrderRequestQueue, clientOrder2ReplyQueue);
        client.start();
        request = new ClientOrderRequest();
        System.out.println("Init done.");
    }

    public ClientOrderRequest getRequest() {
        return request;
    }

    public void setRequest(ClientOrderRequest request) {
        this.request = request;
    }

    public void order() {
        client.sendOrderRequest(request);
    }
}