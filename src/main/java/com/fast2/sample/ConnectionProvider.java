package com.fast2.sample;

import com.arondor.common.management.mbean.annotation.Description;
import com.arondor.common.management.mbean.annotation.Example;
import com.arondor.common.management.mbean.annotation.LongDescription;
import com.arondor.common.management.mbean.annotation.Mandatory;
import com.arondor.common.management.mbean.annotation.Password;
import com.fast2.model.task.annotation.TaskType;

@TaskType(TaskType.Type.Credentials)
public class ConnectionProvider
{

    @Mandatory
    @Description("Connexion URL")
    @LongDescription("http://hostname:port")
    @Example("http://localhost:8080")
    private String endPoint;

    @Mandatory
    @Description("UserName")
    private String login;

    @Mandatory
    @Password
    @Description("Password")
    private String password;

    public String getEndPoint()
    {
        return endPoint;
    }

    public void setEndPoint(String endPoint)
    {
        this.endPoint = endPoint;
    }

    public String getLogin()
    {
        return login;
    }

    public void setLogin(String login)
    {
        this.login = login;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
