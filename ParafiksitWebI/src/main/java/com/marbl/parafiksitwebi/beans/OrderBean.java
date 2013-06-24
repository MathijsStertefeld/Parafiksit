package com.marbl.parafiksitwebi.beans;

import com.marbl.client.ClientOrderRequest;
import com.marbl.client.domain.PartInfo;
import com.marbl.client.domain.WorkPerformedInfo;
import com.marbl.messaging.JMSSettings;
import com.marbl.parafiksitwebi.messaging.ClientTest;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class OrderBean {

    private ClientOrderRequest request;
    private ClientTest cTest;

    @PostConstruct
    public void postConstruct() {
        JMSSettings queueNames = new JMSSettings("src/main/resources/MESSAGING_CHANNELS.ini");
        final String factoryName = queueNames.get(JMSSettings.CONNECTION);
        final String clientOrderRequestQueue = queueNames.get(JMSSettings.CLIENT_ORDER_REQUEST);
        final String clientOrder2ReplyQueue = queueNames.get(JMSSettings.CLIENT_ORDER_REPLY_2);

        cTest = new ClientTest("FontysApp", factoryName, clientOrderRequestQueue, clientOrder2ReplyQueue);
        cTest.start();
        request = new ClientOrderRequest("", "", "", "", "", "", "", "", new ArrayList<WorkPerformedInfo>(), new ArrayList<PartInfo>());
        System.out.println("Init done.");
    }

    public void order() {
        cTest.sendOrderRequest(request);
    }

    public ClientOrderRequest getRequest() {
        return request;
    }

    public void setRequest(ClientOrderRequest request) {
        this.request = request;
    }
}