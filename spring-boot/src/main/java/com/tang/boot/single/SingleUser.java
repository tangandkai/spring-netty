package com.tang.boot.single;

import com.tang.boot.prototype.ProtoUser;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component("singleUser")
public class SingleUser {

    @Lookup
    public ProtoUser getProtoUser(){
        return null;
    }
}
