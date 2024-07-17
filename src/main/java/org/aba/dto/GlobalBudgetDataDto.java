package org.aba.dto;

import java.util.Collection;
import java.util.Date;

public class GlobalBudgetDataDto
{
    private Collection<BudgetDataDto> budgetDataDtos;
    private Double totalOutput = 0.0;
    private Double totalInput = 0.0;
    private Date from = new Date();
    private Date to = new Date();

    public GlobalBudgetDataDto()
    {

    }

    public Collection<BudgetDataDto> getBudgetDataDtos()
    {
        return budgetDataDtos;
    }

    public void setBudgetDataDtos(Collection<BudgetDataDto> budgetDataDtos)
    {
        this.budgetDataDtos = budgetDataDtos;
    }

    public Double getTotalOutput()
    {
        return totalOutput;
    }

    public void setTotalOutput(Double totalOutput)
    {
        this.totalOutput = totalOutput;
    }

    public Double getTotalInput()
    {
        return totalInput;
    }

    public void setTotalInput(Double totalInput)
    {
        this.totalInput = totalInput;
    }

    public Date getFrom()
    {
        return from;
    }

    public void setFrom(Date from)
    {
        this.from = from;
    }

    public Date getTo()
    {
        return to;
    }

    public void setTo(Date to)
    {
        this.to = to;
    }
}
