/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.parafiksit;

import com.marbl.client.domain.WorkPerformedInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bas
 */
public class ParafiksitOrderRequest
{

    private List<WorkPerformedInfo> workPerformed;

    public ParafiksitOrderRequest(ArrayList<WorkPerformedInfo> work)
    {
        this.workPerformed = work;
    }

    public List<WorkPerformedInfo> getWork()
    {
        return workPerformed;
    }

    public void setWork(List<WorkPerformedInfo> work)
    {
        this.workPerformed = work;
    }
    
}
