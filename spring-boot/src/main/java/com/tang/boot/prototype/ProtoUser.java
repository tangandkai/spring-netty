package com.tang.boot.prototype;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("prototype")
@Component
public class ProtoUser extends ProtoSuperUser{
}
