package pl.agh.kis.soa.catering.server.api;

import pl.agh.kis.soa.catering.server.model.Client;
import pl.agh.kis.soa.catering.server.model.Order;
import pl.agh.kis.soa.catering.server.model.Subscription;
import pl.agh.kis.soa.catering.server.model.UserRole;

import java.util.Date;
import java.util.List;

public interface IClientRepository {
    Client getClientById(Long clientId);
    List<Client> getAllClients();
    List<Subscription> getAllClientSubscriptions(Long clientId);
    void addClient(Client client);
    UserRole getUserRole(String role);
}
