package by.clevertec.mapper;

import by.clevertec.dto.ClientDto;
import by.clevertec.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toClientDto(Client client);

    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Client toClient(ClientDto clientDto);

    List<ClientDto> toClientDtoList(List<Client> clients);

}
