package com.epam.esm.service;

import com.epam.esm.dto.OrdersDto;
import com.epam.esm.dto.TagsDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UsersDto;

public interface IUserService extends IBaseService<UserDto, UsersDto> {

    OrdersDto findUserOrders(long userId);

    TagsDto findUserSuperTag(long userId);
}
