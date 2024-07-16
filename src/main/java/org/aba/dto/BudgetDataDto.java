package org.aba.dto;

import org.aba.BaseUtils;

import java.util.Date;

public class BudgetDataDto
{
    String description = "";
    Date date = null;
    Double amount = null;

    public BudgetDataDto()
    {

    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public Double getAmount()
    {
        return amount;
    }

    public void setAmount(Double amount)
    {
        this.amount = amount;
    }

    public String getInfos()
    {
        String s = "";
        if (getDate() != null)
        {
            s += BaseUtils.getDateFormatted(getDate()) + ", ";
        }

        s += getDescription();

        if (getAmount() != null)
        {
            s += " :" + BaseUtils.getDoubleFormatted(getAmount());
        }

        return s;
    }
}
