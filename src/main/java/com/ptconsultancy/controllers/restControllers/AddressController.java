package com.ptconsultancy.controllers.restControllers;

import com.ptconsultancy.domain.Address;
import com.ptconsultancy.entities.AddressEntity;
import com.ptconsultancy.repositories.AddressEntityRepository;
import com.ptconsultancy.utilities.PropertiesHandler;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddressController {

    private AddressEntityRepository addressEntityRepository;

    private PropertiesHandler propertiesHandler;

    @Autowired
    public AddressController(AddressEntityRepository addressEntityRepository, PropertiesHandler propertiesHandler) {
        this.addressEntityRepository = addressEntityRepository;
        this.propertiesHandler = propertiesHandler;
    }

    @RequestMapping(path="/getPrimaryAddress/{addressId}", method=RequestMethod.GET)
    public AddressEntity getAddress(@PathVariable String addressId) {

        AddressEntity output = null;
        for (AddressEntity entity : addressEntityRepository.findByUserId(Long.parseLong(addressId))) {
            if (entity.isPrimaryAddress()) {
                output = entity;
                break;
            }
        }

        return output;
    }

    @RequestMapping(path="/getAllUserAddresses/{addressId}", method=RequestMethod.GET)
    public List<AddressEntity> getAllAddressesForUser(@PathVariable String addressId) {

        return addressEntityRepository.findByUserId(Long.parseLong(addressId));
    }


    @RequestMapping(path="/savePrimaryAddress/{userId}/{password}", method=RequestMethod.POST)
    public void setPrimaryAddress(@RequestBody() Address address, @PathVariable String userId, @PathVariable String password) {

        boolean primaryAddress = false;
        for (AddressEntity entity : addressEntityRepository.findByUserId(address.getUserId())) {
            primaryAddress = entity.isPrimaryAddress();
            if (primaryAddress) {
                break;
            }
        }

        if (!primaryAddress) {
            AddressEntity addressEntity = new AddressEntity(address.getUserId(), address.getHouseNameNumber(), address.getAddressLine1(), address.getAddressLine2(),
                address.getAddressLine3(), address.getAddressLine4(), address.getCounty(), address.getCountry(), address.getPostCode(), true);

            if (userId.equals(propertiesHandler.getProperty("auth.addressapi.userid")) && password.equals(propertiesHandler.getProperty("auth.addressapi.password"))) {
                addressEntityRepository.save(addressEntity);
            }
        }
    }

    @RequestMapping(path="/saveSecondaryAddress/{userId}/{password}", method=RequestMethod.POST)
    public void setSecondaryAddress(@RequestBody() Address address, @PathVariable String userId, @PathVariable String password) {

        AddressEntity addressEntity = new AddressEntity(address.getUserId(), address.getHouseNameNumber(), address.getAddressLine1(), address.getAddressLine2(),
            address.getAddressLine3(), address.getAddressLine4(), address.getCounty(), address.getCountry(), address.getPostCode(), false);

        if (userId.equals(propertiesHandler.getProperty("auth.addressapi.userid")) && password.equals(propertiesHandler.getProperty("auth.addressapi.password"))) {
            addressEntityRepository.save(addressEntity);
        }
    }

    @RequestMapping(path="/deleteAddress/{userId}/{password}/{addressId}", method=RequestMethod.DELETE)
    public void deleteAddress(@PathVariable String userId, @PathVariable String password, @PathVariable String addressId) {

        if (userId.equals(propertiesHandler.getProperty("auth.addressapi.userid")) && password.equals(propertiesHandler.getProperty("auth.addressapi.password"))) {
            List<AddressEntity> entities = addressEntityRepository.findByUserId(Long.parseLong(addressId));
            if (entities.size() == 1) {
                addressEntityRepository.delete(entities.get(0));
            }
        }
    }
}
