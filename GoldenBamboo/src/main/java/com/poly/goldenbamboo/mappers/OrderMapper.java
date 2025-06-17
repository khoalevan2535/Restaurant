package com.poly.goldenbamboo.mappers;

import org.springframework.stereotype.Component;

import com.poly.goldenbamboo.dtos.OrderDTO;
import com.poly.goldenbamboo.entities.AccountEntity;
import com.poly.goldenbamboo.entities.BranchEntity;
import com.poly.goldenbamboo.entities.OrderEntity;
import com.poly.goldenbamboo.entities.TableEntity;

@Component
public class OrderMapper {

    public OrderDTO toDTO(OrderEntity entity) {
        if (entity == null)
            return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setOrderDate(entity.getOrderDate());
        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setPrepay(entity.getPrepay());
        dto.setStatus(entity.getStatus());
        dto.setTotalAmount(entity.getTotalAmount());

        if (entity.getAccount() != null)
            dto.setAccountId(entity.getAccount().getId());

        if (entity.getBranch() != null)
            dto.setBranchId(entity.getBranch().getId());

        if (entity.getTable() != null)
            dto.setTableId(entity.getTable().getId());

        return dto;
    }

    public OrderEntity toEntity(OrderDTO dto) {
        if (dto == null)
            return null;

        OrderEntity entity = new OrderEntity();
        entity.setId(dto.getId());
        entity.setOrderDate(dto.getOrderDate());
        entity.setPaymentMethod(dto.getPaymentMethod());
        entity.setPrepay(dto.getPrepay());
        entity.setStatus(dto.getStatus());
        entity.setTotalAmount(dto.getTotalAmount());

        AccountEntity account = new AccountEntity();
        account.setId(dto.getAccountId());
        entity.setAccount(account);

        BranchEntity branch = new BranchEntity();
        branch.setId(dto.getBranchId());
        entity.setBranch(branch);

        TableEntity table = new TableEntity();
        table.setId(dto.getTableId());
        entity.setTable(table);

        return entity;
    }
}
