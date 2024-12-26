package by.clevertec.mapper;

import by.clevertec.dto.ClientDto;
import by.clevertec.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toClientDto(Client client);

//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "cars", ignore = true)
//    @Mapping(target = "reviews", ignore = true)
    Client toClient(ClientDto clientDto);

}
