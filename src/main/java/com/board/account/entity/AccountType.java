package com.jari.jari.account.entity;

import com.jari.jari.exception.exceptions.NoSuchAccountTypeException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public enum AccountType {

    Lessor("임대인"),
    Lessee("임차인"),
    Realtor("공인중개사"),
    Guest("비회원");

    private static final Map<String, AccountType> typeMap = new HashMap<>();

    static {
        typeMap.put(Lessor.name(), Lessor);
        typeMap.put(Lessee.name(), Lessee);
        typeMap.put(Realtor.name(), Realtor);
        typeMap.put(Guest.name(), Guest);
    }

    private String description;

    public static AccountType castFromString(String accountTypeString) {
        if (typeMap.containsKey(accountTypeString)) {
            return typeMap.get(accountTypeString);
        }
        throw new NoSuchAccountTypeException();
    }

}
