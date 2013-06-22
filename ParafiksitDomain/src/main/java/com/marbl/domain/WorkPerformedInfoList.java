/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.marbl.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Leslie Aerts
 */
public class WorkPerformedInfoList
{
    private List<WorkPerformedInfo> list;

    public WorkPerformedInfoList()
    {
        list = new ArrayList<WorkPerformedInfo>();
    }
    
    public void add(WorkPerformedInfo info)
    {
        list.add(info);
    }
    
    
}
