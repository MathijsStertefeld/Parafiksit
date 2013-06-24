/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.warehouse;

import com.marbl.client.domain.PartInfo;
import com.marbl.warehouse.domain.Database;
import com.marbl.warehouse.domain.Factuur;
import com.marbl.warehouse.domain.FactuurRegel;
import com.marbl.warehouse.domain.Klant;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bas
 */
public class WarehouseMessaging
{

    private ComputerBrokerGateway gateway;
    private Database db;

    public WarehouseMessaging(String factoryName, String requestQueue, String replyQueue)
    {
       
        gateway = new ComputerBrokerGateway(factoryName, requestQueue, replyQueue)
        {
            @Override
            void receivedWarehouseRequest(WarehouseOrderRequest request)
            {
                try
                {
                     db = new Database();
                    //Klant en factuur hier maken
                    String address = request.getShipping().getStreet() + " " + request.getShipping().getNumber() + ", " + request.getShipping().getPlace();
                    Klant klant = new Klant(-1, request.getContact().getContactName(), address);

                    int nextId = 0;

                    for (Factuur f : db.selectFacturen())
                    {
                        if (f.getCode() > nextId)
                        {
                            nextId = f.getCode();
                        }
                    }
                    ArrayList<FactuurRegel> regels = new ArrayList<FactuurRegel>();
                    for (PartInfo i : request.getParts())
                    {
                        //TODO EDIT THIS
                        regels.add(new FactuurRegel(nextId, 1, 1));
                    }
                    Factuur f = new Factuur(nextId, klant.getCode(), new Date().toString(), regels);
                    f.setRegels(regels);
                    //db.insert(klant);
                    //db.insert(f);
                    gateway.sendReply(request, new WarehouseOrderReply(f));
                    
                    System.out.println("I sent away a reply from the warehouse. I needed " +  request.getParts().get(0));
                } catch (SQLException ex)
                {
                    Logger.getLogger(WarehouseMessaging.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    }

    public void start()
    {
        gateway.start();
    }
}
