package com.marbl.messaging.requestreply;

/**
 *
 * @author Maja Pesic
 */
public interface IRequestListener<REQUEST> {
   public void receivedRequest(REQUEST request);
}
