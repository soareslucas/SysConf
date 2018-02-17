package ufg.inf.pw.beans;

import javax.faces.application.FacesMessage;
import javax.faces.context.*;

public class AbstractBean
{

    public AbstractBean()
    {
    }

    protected void displayErrorMessageToUser(String message)
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }

    protected void displayInfoMessageToUser(String message)
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
