package com.tourbooking.mapper;

import com.tourbooking.dto.response.CreateNewAccountResponse;
import com.tourbooking.model.Account;
import com.tourbooking.model.Tour;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(source = "accountName", target = "username")
    CreateNewAccountResponse toAccountResponse(Account account);
    Account toAccount(CreateNewAccountResponse accountResponse);
}
