package com.tang.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Flux<Account> account = getAccount();
        account.subscribe(c->{
            System.out.println(c);
            c.setName("jack");
        });
        Mono<Account> account1 = getAccountById(1);
        account.map(f->{
            System.out.println(f);
            f.setCode(f.getCode()*2);
            return f;
        }).subscribe(c->{
            System.out.println(c);
        });

    }

    private static Flux<Account>getAccount(){
        List<Account> accountList = new ArrayList<>();
        Account account = new Account();
        account.setCode(1);
        account.setName("DemoCode");
        accountList.add(account);
        return Flux.fromIterable(accountList);
    }

    private static Mono<Account> getAccountById(int id) {
        List<Account> accountList = new ArrayList<>();
        Account account = new Account();
        account.setCode(id);
        account.setName("DemoCode");
        accountList.add(account);
        return Mono.just(account);
    }
}

class Account{
    private int code;
    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Account{" +
                "code=" + code +
                ", name='" + name + '\'' +
                '}';
    }
}