package com.marbl.parafiksit;

import com.thoughtworks.xstream.XStream;
import com.marbl.messaging.requestreply.IRequestReplySerializer;

/**
 *
 * This class serializes BankRequest and Bankreply to/from XML.
 */
public class ParafiksitSerializer implements IRequestReplySerializer<ParafiksitRequest, ParafiksitReply> {

    private static final String ALIAS_REQUEST = "ParafiksitRequest"; // tag name for BankRequest
    private static final String ALIAS_REPLY = "ParafiksitReply"; // tag name for BankReply
    private XStream xstream; // class for serialization

    public ParafiksitSerializer() {
        super();
        xstream = new XStream();
         // register aliases (i.e., tag names)
        xstream.alias(ALIAS_REQUEST, ParafiksitRequest.class);
        xstream.alias(ALIAS_REPLY, ParafiksitReply.class);
    }

    /**
     * This method parses a bankRequest from an XML string.
     * @param str is the string containing the XML
     * @return the BankRequest containng the same information like the given XML (str)
     */
    public ParafiksitRequest requestFromString(String str) {
        return (ParafiksitRequest) xstream.fromXML(str);
    }
    /**
     * This method parses a BankReply from an XML string.
     * @param str is the string containing the XML
     * @return the BankReply containng the same information like the given XML (str)
     */
    public ParafiksitReply replyFromString(String str) {
        return (ParafiksitReply) xstream.fromXML(str);
    }
    
    /**
     * Serializes a BankRequest into an XML string.
     * @param request is the BankRequest to be serialized into XML
     * @return the string containing XML with information about the request
     */
    public String requestToString(ParafiksitRequest request) {
        return xstream.toXML(request);
    }
    /**
     * Serializes a BankReply into XML string.
     * @param request is the BankReply to be serialized into XML
     * @return the string containing XML with information about the reply
     */
    public String replyToString(ParafiksitReply reply) {
        return xstream.toXML(reply);
    }
}
