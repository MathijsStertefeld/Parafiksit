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
public class PartInfoList
{
    private List<PartInfo> list;
    
    public PartInfoList()
    {
        list = new ArrayList<PartInfo>();
    }
    
    public void add(PartInfo info)
    {
        list.add(info);
    }
}
