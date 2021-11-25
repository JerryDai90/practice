package com.practice.pattern.factory;

public class UserLoginDemo {

    public static void main(String[] args) {
        UserLoginFactory factory = LDAPFactory.getFactory();
        factory.getUserLogin().login("admin", "", null);


        factory = LocalhostFactory.getFactory();
        factory.getUserLogin().login("admin", "", null);

    }

}


interface UserLogin {

    boolean login(String name, String password, Object otherParams);
}


interface UserLoginFactory {
    UserLogin getUserLogin();
}


class LDAP implements UserLogin {

    @Override
    public boolean login(String name, String password, Object otherParams) {
        System.out.println("LDAP登陆成功，" + name);
        return false;
    }
}

class LDAPFactory implements UserLoginFactory {

    @Override
    public UserLogin getUserLogin() {
        return new LDAP();
    }

    public static UserLoginFactory getFactory() {
        return new LDAPFactory();
    }
}


class Localhost implements UserLogin {

    @Override
    public boolean login(String name, String password, Object otherParams) {
        System.out.println("本地登陆成功，" + name);
        return false;
    }
}

class LocalhostFactory implements UserLoginFactory {

    @Override
    public UserLogin getUserLogin() {
        return new Localhost();
    }

    public static UserLoginFactory getFactory() {
        return new LocalhostFactory();
    }
}