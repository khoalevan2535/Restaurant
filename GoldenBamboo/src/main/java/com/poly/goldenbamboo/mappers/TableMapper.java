package com.poly.goldenbamboo.mappers;
import com.poly.goldenbamboo.dtos.TableDTO;
import com.poly.goldenbamboo.entities.TableEntity;

public class TableMapper {
   // Chuyển từ TableEntity sang TableBeans
    public static TableDTO mapToTableBeans(TableEntity tableEntity) {
        if (tableEntity == null) {
            return null;
        }
        TableDTO tableBeans = new TableDTO();
        tableBeans.setId(tableEntity.getId());
        tableBeans.setBranchId(
            tableEntity.getBranch() != null ? tableEntity.getBranch().getId() : null
        );
        tableBeans.setNumber(tableEntity.getNumber());
        return tableBeans;
    }

    // Chuyển từ TableBeans sang TableEntity
    public static TableEntity mapToTableEntity(TableDTO tableBeans) {
        if (tableBeans == null) {
            return null;
        }
        TableEntity tableEntity = new TableEntity();
        tableEntity.setId(tableBeans.getId());
        tableEntity.setBranch(BranchMapper.mapToBranchEntityById(tableBeans.getBranchId()));
        tableEntity.setNumber(tableBeans.getNumber());
        return tableEntity;
    }
}
